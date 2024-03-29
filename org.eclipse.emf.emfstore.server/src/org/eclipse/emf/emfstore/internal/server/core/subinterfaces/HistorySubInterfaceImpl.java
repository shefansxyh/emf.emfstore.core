/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.core.subinterfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.core.AbstractEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.AbstractSubEmfstoreInterface;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod;
import org.eclipse.emf.emfstore.internal.server.core.helper.EmfStoreMethod.MethodId;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidInputException;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.exceptions.StorageException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Version;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * This subinterfaces implements all history related functionality for the
 * EmfStoreImpl interface.
 * 
 * @author wesendon
 */
public class HistorySubInterfaceImpl extends AbstractSubEmfstoreInterface {

	/**
	 * Default constructor.
	 * 
	 * @param parentInterface
	 *            parent interface
	 * @throws FatalESException
	 *             in case of failure
	 */
	public HistorySubInterfaceImpl(AbstractEmfstoreInterface parentInterface) throws FatalESException {
		super(parentInterface);
	}

	@Override
	protected void initSubInterface() throws FatalESException {
		super.initSubInterface();
	}

	/**
	 * {@inheritDoc}
	 */
	@EmfStoreMethod(MethodId.ADDTAG)
	public void addTag(ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws ESException {
		sanityCheckObjects(projectId, versionSpec, tag);
		synchronized (getMonitor()) {
			Version version = getSubInterface(VersionSubInterfaceImpl.class).getVersion(projectId, versionSpec);

			// stamp branch instead of throwing an exception
			tag.setBranch(versionSpec.getBranch());

			version.getTagSpecs().add(tag);
			try {
				save(version);
			} catch (FatalESException e) {
				throw new StorageException(StorageException.NOSAVE);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@EmfStoreMethod(MethodId.REMOVETAG)
	public void removeTag(ProjectId projectId, PrimaryVersionSpec versionSpec, TagVersionSpec tag)
		throws ESException {
		sanityCheckObjects(projectId, versionSpec, tag);
		synchronized (getMonitor()) {
			Version version = getSubInterface(VersionSubInterfaceImpl.class).getVersion(projectId, versionSpec);
			Iterator<TagVersionSpec> iterator = version.getTagSpecs().iterator();
			while (iterator.hasNext()) {
				if (iterator.next().getName().equals(tag.getName())) {
					iterator.remove();
				}
			}
			try {
				save(version);
			} catch (FatalESException e) {
				throw new StorageException(StorageException.NOSAVE);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@EmfStoreMethod(MethodId.GETHISTORYINFO)
	public List<HistoryInfo> getHistoryInfo(ProjectId projectId, HistoryQuery historyQuery) throws ESException {
		sanityCheckObjects(projectId, historyQuery);
		synchronized (getMonitor()) {

			if (historyQuery instanceof ModelElementQuery) {

				return handleMEQuery(projectId, (ModelElementQuery) historyQuery);

			} else if (historyQuery instanceof RangeQuery) {

				return versionToHistoryInfo(projectId, handleRangeQuery(projectId, (RangeQuery<?>) historyQuery),
					historyQuery.isIncludeChangePackages());

			} else if (historyQuery instanceof PathQuery) {

				return versionToHistoryInfo(projectId, handlePathQuery(projectId, (PathQuery) historyQuery),
					historyQuery.isIncludeChangePackages());

			}
			return Collections.emptyList();
		}
	}

	private List<Version> handleRangeQuery(ProjectId projectId, RangeQuery<?> query) throws ESException {
		ProjectHistory project = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);
		if (query.isIncludeAllVersions()) {
			return getAllVersions(project, sourceNumber(query) - query.getLowerLimit(),
				sourceNumber(query) + query.getUpperLimit(), true);
		}
		Version version = getSubInterface(VersionSubInterfaceImpl.class).getVersion(projectId, query.getSource());
		TreeSet<Version> result = new TreeSet<Version>(new VersionComparator(false));
		result.addAll(addForwardVersions(project, version, query.getUpperLimit(), query.isIncludeIncoming(),
			query.isIncludeOutgoing()));
		result.add(version);
		result.addAll(addBackwardVersions(project, version, query.getLowerLimit(), query.isIncludeIncoming(),
			query.isIncludeOutgoing()));
		return new ArrayList<Version>(result);
	}

	private List<Version> handlePathQuery(ProjectId projectId, PathQuery query) throws ESException {
		ProjectHistory project = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);
		if (query.isIncludeAllVersions()) {
			List<Version> result = getAllVersions(project, sourceNumber(query), targetNumber(query), false);
			if ((targetNumber(query) > sourceNumber(query))) {
				Collections.reverse(result);
			}
			return result;
		}
		if (query.getSource() == null || query.getTarget() == null) {
			throw new InvalidInputException();
		}
		List<Version> result = getSubInterface(VersionSubInterfaceImpl.class).getVersions(projectId, query.getSource(),
			query.getTarget());
		if (query.getTarget().compareTo(query.getSource()) < 0) {
			Collections.reverse(result);
		}
		return result;
	}

	private List<HistoryInfo> handleMEQuery(ProjectId projectId, ModelElementQuery query) throws ESException {
		List<Version> inRange = handleRangeQuery(projectId, query);
		// SortedSet<Version> relevantVersions = new TreeSet<Version>(new VersionComparator(false));
		// for (ModelElementId id : query.getModelElements()) {
		// relevantVersions.addAll(historyCache.getChangesForModelElement(projectId, id));
		// }
		// relevantVersions.retainAll(inRange);
		List<Version> relevantVersions = filterVersions(inRange, query.getModelElements());
		List<HistoryInfo> result = versionToHistoryInfo(projectId, relevantVersions, query.isIncludeChangePackages());
		// filter ops
		for (HistoryInfo historyInfo : result) {
			filterOperationsForSelectedME(query.getModelElements(), historyInfo);
		}
		return result;
	}

	// TODO combine with op filtering
	private List<Version> filterVersions(List<Version> inRange, List<ModelElementId> modelElements) {
		ArrayList<Version> result = new ArrayList<Version>();
		for (Version version : inRange) {
			// special case for initial version
			if (version.getPrimarySpec() != null && version.getPrimarySpec().getIdentifier() == 0) {
				if (version.getProjectState() != null) {
					for (ModelElementId id : modelElements) {
						if (version.getProjectState().contains(id)) {
							result.add(version);
							break;
						}
					}
				}
			}
			if (version.getChanges() == null) {
				continue;
			}
			Set<ModelElementId> involvedModelElements = version.getChanges().getAllInvolvedModelElements();
			for (ModelElementId id : modelElements) {
				if (involvedModelElements.contains(id)) {
					result.add(version);
					break;
				}
			}
		}
		return result;
	}

	private int sourceNumber(HistoryQuery query) throws ESException {
		if (query.getSource() == null) {
			throw new InvalidInputException();
		}
		return query.getSource().getIdentifier();
	}

	private int targetNumber(PathQuery query) throws ESException {
		if (query.getTarget() == null) {
			throw new InvalidInputException();
		}
		return query.getTarget().getIdentifier();
	}

	/**
	 * @return higher versions first
	 * @throws ESException
	 */
	private List<Version> getAllVersions(ProjectHistory project, int from, int to, boolean tollerant)
		throws ESException {
		if (to < from) {
			return getAllVersions(project, to, from, tollerant);
		}
		EList<Version> versions = project.getVersions();
		int globalhead = versions.size() - 1;

		int start = to;
		int end = from;
		if (!tollerant && (from < 0 || to < 0 || from > globalhead || to > globalhead)) {
			throw new InvalidVersionSpecException();
		} else {
			start = Math.min(globalhead, to);
			end = Math.max(0, from);
		}

		if (start < 0 || start > globalhead || end < 0 || end > globalhead) {
			throw new InvalidVersionSpecException();
		}

		if (start == end) {
			return Arrays.asList(versions.get(start));
		}

		// saftey check
		if (Math.abs(start - end) > Math.abs(to - from)) {
			throw new InvalidVersionSpecException();
		}
		ArrayList<Version> result = new ArrayList<Version>();
		for (int i = start; i >= end; i--) {
			result.add(versions.get(i));
		}
		return result;
	}

	private Collection<Version> addForwardVersions(ProjectHistory project, Version version, int limit,
		boolean includeIncoming, boolean includeOutgoing) {
		if (limit == 0) {
			return Collections.emptyList();
		}
		SortedSet<Version> result = new TreeSet<Version>(new VersionComparator(false));
		Version currentVersion = version;
		while (currentVersion != null && result.size() < limit) {
			if (includeOutgoing && currentVersion.getBranchedVersions().size() > 0) {
				result.addAll(currentVersion.getBranchedVersions());
			}
			if (includeIncoming && currentVersion.getMergedFromVersion().size() > 0) {
				result.addAll(currentVersion.getMergedFromVersion());
			}

			currentVersion = currentVersion.getNextVersion();
			if (currentVersion != null) {
				result.add(currentVersion);
			}
		}

		if (limit > 0 && result.size() > limit) {
			return new ArrayList<Version>(result).subList(0, limit);
		}
		return result;
	}

	private Collection<Version> addBackwardVersions(ProjectHistory project, Version version, int limit,
		boolean includeIncoming, boolean includeOutgoing) {
		if (limit == 0) {
			return Collections.emptyList();
		}
		SortedSet<Version> result = new TreeSet<Version>(new VersionComparator(false));
		Version currentVersion = version;
		while (currentVersion != null && result.size() < limit) {
			if (includeOutgoing && currentVersion.getBranchedVersions().size() > 0) {
				result.addAll(currentVersion.getBranchedVersions());
			}
			if (includeIncoming && currentVersion.getMergedFromVersion().size() > 0) {
				result.addAll(currentVersion.getMergedFromVersion());
			}
			// move in tree
			if (currentVersion.getPreviousVersion() != null) {
				currentVersion = currentVersion.getPreviousVersion();
			} else if (currentVersion.getAncestorVersion() != null) {
				currentVersion = currentVersion.getAncestorVersion();
			} else {
				currentVersion = null;
			}
			// add versions
			if (currentVersion != null) {
				result.add(currentVersion);
			}
		}
		if (limit > 0 && result.size() > limit) {
			return new ArrayList<Version>(result).subList(0, limit);
		}
		return result;
	}

	private void filterOperationsForSelectedME(List<ModelElementId> ids, HistoryInfo historyInfo) {
		if (historyInfo.getChangePackage() == null || historyInfo.getChangePackage().getOperations() == null) {
			return;
		}
		Set<AbstractOperation> operationsToRemove = new LinkedHashSet<AbstractOperation>();
		EList<AbstractOperation> operations = historyInfo.getChangePackage().getOperations();
		for (AbstractOperation operation : operations) {
			for (ModelElementId id : ids) {
				if (!operation.getAllInvolvedModelElements().contains(id)) {
					operationsToRemove.add(operation);
				}
			}
		}
		operations.removeAll(operationsToRemove);
	}

	private List<HistoryInfo> versionToHistoryInfo(ProjectId projectId, Collection<Version> versions, boolean includeCP)
		throws ESException {
		ArrayList<HistoryInfo> result = new ArrayList<HistoryInfo>();
		for (Version version : versions) {
			result.add(createHistoryInfo(projectId, version, includeCP));
		}
		return result;
	}

	/**
	 * Generates a history info from a version. If needed also adds the HEAD
	 * tag, which isn't persistent.
	 * 
	 * @param projectId
	 *            project
	 * @param version
	 *            version
	 * @param includeChangePackage
	 * @return history info
	 * @throws ESException
	 */
	private HistoryInfo createHistoryInfo(ProjectId projectId, Version version, boolean includeChangePackage)
		throws ESException {
		HistoryInfo history = VersioningFactory.eINSTANCE.createHistoryInfo();
		if (includeChangePackage && version.getChanges() != null) {
			history.setChangePackage(ModelUtil.clone(version.getChanges()));
		}
		history.setLogMessage(ModelUtil.clone(version.getLogMessage()));
		// Set Version References
		history.setPrimerySpec(ModelUtil.clone(version.getPrimarySpec()));
		if (version.getAncestorVersion() != null) {
			history.setPreviousSpec(ModelUtil.clone(version.getAncestorVersion().getPrimarySpec()));
		} else if (version.getPreviousVersion() != null) {
			history.setPreviousSpec(ModelUtil.clone(version.getPreviousVersion().getPrimarySpec()));
		}
		if (version.getNextVersion() != null) {
			history.getNextSpec().add(ModelUtil.clone(version.getNextVersion().getPrimarySpec()));
		}
		history.getNextSpec().addAll(addSpecs(version.getBranchedVersions()));
		history.getMergedFrom().addAll(addSpecs(version.getMergedFromVersion()));
		history.getMergedTo().addAll(addSpecs(version.getMergedToVersion()));

		setTags(projectId, version, history);
		return history;
	}

	private void setTags(ProjectId projectId, Version version, HistoryInfo history) throws ESException {
		ProjectHistory project = getSubInterface(ProjectSubInterfaceImpl.class).getProject(projectId);

		if (version.getPrimarySpec().equals(project.getLastVersion().getPrimarySpec())) {
			history.getTagSpecs().add(Versions.createTAG("HEAD", VersionSpec.GLOBAL));
		}
		for (BranchInfo branch : project.getBranches()) {
			if (version.getPrimarySpec().equals(branch.getHead())) {
				history.getTagSpecs().add(Versions.createTAG("HEAD: " + branch.getName(), branch.getName()));
			}
		}

		history.getTagSpecs().addAll(ModelUtil.clone(version.getTagSpecs()));
	}

	private List<PrimaryVersionSpec> addSpecs(List<Version> versions) {
		ArrayList<PrimaryVersionSpec> result = new ArrayList<PrimaryVersionSpec>();
		for (Version version : versions) {
			result.add(ModelUtil.clone(version.getPrimarySpec()));
		}
		return result;
	}

	/**
	 * Sorts versions based on the primary version spec.
	 * 
	 * @author wesendon
	 * 
	 */
	private final class VersionComparator implements Comparator<Version> {
		private final boolean asc;

		public VersionComparator(boolean asc) {
			this.asc = asc;
		}

		public int compare(Version o1, Version o2) {
			PrimaryVersionSpec v1 = o1.getPrimarySpec();
			PrimaryVersionSpec v2 = o2.getPrimarySpec();
			if (v1 == null || v2 == null) {
				throw new IllegalStateException();
			}
			if (asc) {
				return v1.compareTo(v2);
			} else {
				return v1.compareTo(v2) * -1;
			}
		}
	}
}
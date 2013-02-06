package org.eclipse.emf.emfstore.client.model.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.emfstore.client.api.ILocalProject;
import org.eclipse.emf.emfstore.client.api.IRemoteProject;
import org.eclipse.emf.emfstore.client.api.IUsersession;
import org.eclipse.emf.emfstore.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.client.model.ModelFactory;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.Usersession;
import org.eclipse.emf.emfstore.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.client.model.connectionmanager.ServerCall;
import org.eclipse.emf.emfstore.client.model.observers.CheckoutObserver;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.server.model.ProjectId;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryInfo;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.model.versioning.Versions;

public class RemoteProject implements IRemoteProject {

	/**
	 * The current connection manager used to connect to the server(s).
	 */
	private ConnectionManager connectionManager;

	private final ProjectInfo projectInfo;

	private final Usersession usersession;

	// TODO: OTS
	public RemoteProject(ProjectInfo projectInfo) {
		this.usersession = null;
		this.projectInfo = projectInfo;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.Workspace#setConnectionManager(org.eclipse.emf.emfstore.client.model.connectionmanager.ConnectionManager)
	 */
	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public IProjectId getProjectId() {
		return projectInfo.getProjectId();
	}

	public String getProjectName() {
		return projectInfo.getName();
	}

	public String getProjectDescription() {
		return projectInfo.getDescription();
	}

	public List<BranchInfo> getBranches() throws EmfStoreException {
		return new ServerCall<List<BranchInfo>>(usersession) {
			@Override
			protected List<BranchInfo> run() throws EmfStoreException {
				final ConnectionManager cm = WorkspaceProvider.getInstance().getConnectionManager();
				return cm.getBranches(getSessionId(), (ProjectId) getProjectId());
			};
		}.execute();
	}

	public PrimaryVersionSpec resolveVersionSpec(final IVersionSpec versionSpec) throws EmfStoreException {
		return new ServerCall<PrimaryVersionSpec>(usersession) {
			@Override
			protected PrimaryVersionSpec run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return connectionManager.resolveVersionSpec(getSessionId(), (ProjectId) getProjectId(),
					(VersionSpec) versionSpec);
			}
		}.execute();
	}

	public List<HistoryInfo> getHistoryInfos(final IHistoryQuery query) throws EmfStoreException {
		return new ServerCall<List<HistoryInfo>>(usersession) {
			@Override
			protected List<HistoryInfo> run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return connectionManager.getHistoryInfo(getSessionId(), (ProjectId) getProjectId(),
					(HistoryQuery) query);
			}
		}.execute();
	}

	public void addTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EmfStoreException {
		final ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
		connectionManager.addTag(usersession.getSessionId(), (ProjectId) getProjectId(),
			(PrimaryVersionSpec) versionSpec, (TagVersionSpec) tag);
	}

	public void removeTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EmfStoreException {
		final ConnectionManager cm = WorkspaceProvider.getInstance().getConnectionManager();
		cm.removeTag(usersession.getSessionId(), (ProjectId) getProjectId(), (PrimaryVersionSpec) versionSpec,
			(TagVersionSpec) tag);
	}

	public ILocalProject checkout() throws EmfStoreException {
		return checkout(usersession);
	}

	public ILocalProject checkout(IUsersession usersession) throws EmfStoreException {
		return checkout(usersession, new NullProgressMonitor());

	}

	public ILocalProject checkout(IUsersession usersession, IProgressMonitor progressMonitor) throws EmfStoreException {
		PrimaryVersionSpec targetSpec = this.connectionManager.resolveVersionSpec(
			((Usersession) usersession).getSessionId(), ((ProjectInfo) projectInfo).getProjectId(),
			Versions.createHEAD());
		return checkout(usersession, targetSpec, progressMonitor);
	}

	public ILocalProject checkout(IUsersession usersession, IVersionSpec versionSpec, IProgressMonitor progressMonitor)
		throws EmfStoreException {

		SubMonitor parent = SubMonitor.convert(progressMonitor, "Checkout", 100);
		final Usersession session = (Usersession) usersession;
		final ProjectInfo info = (ProjectInfo) projectInfo;

		// FIXME: MK: hack: set head version manually because esbrowser does not
		// update
		// revisions properly
		final ProjectInfo projectInfoCopy = ModelUtil.clone(info);
		projectInfoCopy.setVersion((PrimaryVersionSpec) versionSpec);

		// get project from server
		Project project = null;

		SubMonitor newChild = parent.newChild(40);
		parent.subTask("Fetching project from server...");
		project = new UnknownEMFStoreWorkloadCommand<Project>(newChild) {
			@Override
			public Project run(IProgressMonitor monitor) throws EmfStoreException {
				return connectionManager.getProject(session.getSessionId(), info.getProjectId(),
					projectInfoCopy.getVersion());
			}
		}.execute();

		if (project == null) {
			throw new EmfStoreException("Server returned a null project!");
		}

		final PrimaryVersionSpec primaryVersionSpec = projectInfoCopy.getVersion();
		ProjectSpace projectSpace = ModelFactory.eINSTANCE.createProjectSpace();

		// initialize project space
		parent.subTask("Initializing Projectspace...");
		projectSpace.setProjectId(projectInfoCopy.getProjectId());
		projectSpace.setProjectName(projectInfoCopy.getName());
		projectSpace.setProjectDescription(projectInfoCopy.getDescription());
		projectSpace.setBaseVersion(primaryVersionSpec);
		projectSpace.setLastUpdated(new Date());
		projectSpace.setUsersession(session);
		WorkspaceProvider.getObserverBus().register((ProjectSpaceBase) projectSpace);
		projectSpace.setProject(project);
		projectSpace.setResourceCount(0);
		projectSpace.setLocalOperations(ModelFactory.eINSTANCE.createOperationComposite());
		parent.worked(20);

		// progressMonitor.subTask("Initializing resources...");
		// TODO: OTS cast
		projectSpace.initResources(((WorkspaceBase) WorkspaceProvider.INSTANCE.getWorkspace()).getResourceSet());
		parent.worked(10);

		// retrieve recent changes
		// TODO why are we doing this?? And why HERE?
		parent.subTask("Retrieving recent changes...");
		try {
			DateVersionSpec dateVersionSpec = VersioningFactory.eINSTANCE.createDateVersionSpec();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -10);
			dateVersionSpec.setDate(calendar.getTime());
			PrimaryVersionSpec sourceSpec;
			try {
				sourceSpec = this.connectionManager.resolveVersionSpec(session.getSessionId(),
					projectSpace.getProjectId(), dateVersionSpec);
			} catch (InvalidVersionSpecException e) {
				sourceSpec = VersioningFactory.eINSTANCE.createPrimaryVersionSpec();
				sourceSpec.setIdentifier(0);
			}
			ModelUtil.saveResource(projectSpace.eResource(), WorkspaceUtil.getResourceLogger());

		} catch (EmfStoreException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			WorkspaceUtil.logException(e.getMessage(), e);
		} catch (IOException e) {
			WorkspaceUtil.logException(e.getMessage(), e);
		}
		parent.worked(10);

		parent.subTask("Finishing checkout...");
		// TODO: OTS cast
		((WorkspaceBase) WorkspaceProvider.INSTANCE.getWorkspace()).addProjectSpace(projectSpace);
		// TODO: OTS save
		((WorkspaceBase) WorkspaceProvider.INSTANCE.getWorkspace()).save();
		WorkspaceProvider.getObserverBus().notify(CheckoutObserver.class).checkoutDone(projectSpace);
		parent.worked(10);
		parent.done();

		return projectSpace;
	}

	public List<? extends IHistoryInfo> getHistoryInfos(IUsersession usersession, final IHistoryQuery query)
		throws EmfStoreException {
		return new ServerCall<List<IHistoryInfo>>((Usersession) usersession) {
			@Override
			protected List<IHistoryInfo> run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				return (List<IHistoryInfo>) (List<?>) connectionManager.getHistoryInfo(getSessionId(),
					(ProjectId) getProjectId(), (HistoryQuery) query);
			}
		}.execute();
	}

	public IPrimaryVersionSpec resolveVersionSpec(IUsersession usersession, final IVersionSpec versionSpec)
		throws EmfStoreException {
		return new ServerCall<IPrimaryVersionSpec>((Usersession) usersession) {
			@Override
			protected PrimaryVersionSpec run() throws EmfStoreException {
				ConnectionManager connectionManager = WorkspaceProvider.getInstance().getConnectionManager();
				// TODO: OTS cast
				return connectionManager.resolveVersionSpec(getSessionId(), (ProjectId) getProjectId(),
					(VersionSpec) versionSpec);
			}
		}.execute();

	}

	public void delete() throws IOException {
		// TODO Auto-generated method stub

	}

}
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
package org.eclipse.emf.emfstore.client.test.server.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.test.testmodel.TestElement;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.InvalidVersionSpecException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Version;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Versions;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

public class BranchTests extends CoreServerTest {

	@Test
	public void createBranchFromTrunk() {
		final ProjectSpace ps = getProjectSpace();
		createTestElement("Horst");
		share(ps);

		branch(ps, "b1");

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(2, history.getVersions().size());

		Version initialVersion = history.getVersions().get(0);
		Version branchedVersion = history.getVersions().get(1);

		assertEquals("trunk", initialVersion.getPrimarySpec().getBranch());
		assertEquals("b1", branchedVersion.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertTrue(branchedVersion.getNextVersion() == null);
		assertTrue(branchedVersion.getPreviousVersion() == null);
		assertTrue(initialVersion.getNextVersion() == null);
		assertTrue(initialVersion.getPreviousVersion() == null);

	}

	@Test
	public void createBranchFromTrunkWithChanges() {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");
		share(ps);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		branch(ps, "b1");

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(2, history.getVersions().size());

		Version initialVersion = history.getVersions().get(0);
		Version branchedVersion = history.getVersions().get(1);

		assertEquals("trunk", initialVersion.getPrimarySpec().getBranch());
		assertEquals("b1", branchedVersion.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertTrue(branchedVersion.getNextVersion() == null);

	}

	@Test
	public void createBranchFromBranch() {
		final ProjectSpace ps = getProjectSpace();
		createTestElement("Horst");
		share(ps);

		branch(ps, "b1");
		branch(ps, "b2");

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(3, history.getVersions().size());

		Version initialVersion = history.getVersions().get(0);
		Version branchedVersion = history.getVersions().get(1);
		Version branchOfBranch = history.getVersions().get(2);

		assertEquals("trunk", initialVersion.getPrimarySpec().getBranch());
		assertEquals("b1", branchedVersion.getPrimarySpec().getBranch());
		assertEquals("b2", branchOfBranch.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertTrue(branchedVersion.getNextVersion() == null);
		assertTrue(branchOfBranch.getAncestorVersion() == branchedVersion);
		assertTrue(branchOfBranch.getNextVersion() == null);
	}

	@Test
	public void commitToBranch() {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");
		share(ps);

		branch(ps, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		commit(ps);

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(3, history.getVersions().size());

		Version initialVersion = history.getVersions().get(0);
		Version branchedVersion = history.getVersions().get(1);
		Version commit = history.getVersions().get(2);

		assertEquals("trunk", initialVersion.getPrimarySpec().getBranch());
		assertEquals("b1", branchedVersion.getPrimarySpec().getBranch());
		assertEquals("b1", commit.getPrimarySpec().getBranch());
		assertTrue(branchedVersion.getAncestorVersion() == initialVersion);
		assertTrue(branchedVersion.getNextVersion() == commit);
		assertTrue(commit.getAncestorVersion() == null);
		assertTrue(commit.getPreviousVersion() == branchedVersion);
	}

	@Test
	public void updateFromBranch() throws InvalidVersionSpecException, ESException {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");
		share(ps);

		branch(ps, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		commit(ps);

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(3, history.getVersions().size());
		Version commit = history.getVersions().get(2);

		// create version
		PrimaryVersionSpec newVersion = ModelUtil.clone(getEmfStore().createVersion(
			ModelUtil.clone(ps.getUsersession().getSessionId()), ModelUtil.clone(ps.getProjectId()),
			ModelUtil.clone(commit.getPrimarySpec()), ModelUtil.clone(commit.getChanges()), null, null,
			ModelUtil.clone(commit.getLogMessage())));

		assertEquals(4, history.getVersions().size());

		assertFalse(newVersion.equals(ps.getBaseVersion()));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					ps.update(new NullProgressMonitor());
				} catch (ESException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		assertEquals(newVersion, ps.getBaseVersion());
	}

	@Test
	public void tagBranch() throws ESException {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");
		share(ps);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		PrimaryVersionSpec branch = branch(ps, "b1");

		ps.addTag(branch, Versions.createTAG("mytag", branch.getBranch()));

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(2, history.getVersions().size());

		Version initialVersion = history.getVersions().get(0);
		Version branchedVersion = history.getVersions().get(1);

		assertEquals("trunk", initialVersion.getPrimarySpec().getBranch());
		assertEquals("b1", branchedVersion.getPrimarySpec().getBranch());
		assertEquals(1, branchedVersion.getTagSpecs().size());
		assertEquals("mytag", branchedVersion.getTagSpecs().get(0).getName());
	}

	@Test
	public void untagBranch() throws ESException {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");
		share(ps);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		PrimaryVersionSpec branch = branch(ps, "b1");

		ProjectHistory history = getProjectHistory(ps);
		assertEquals(2, history.getVersions().size());

		Version branchedVersion = history.getVersions().get(1);

		branchedVersion.getTagSpecs().add(Versions.createTAG("mytag", "b1"));

		assertEquals(1, branchedVersion.getTagSpecs().size());
		assertEquals("mytag", branchedVersion.getTagSpecs().get(0).getName());

		ps.removeTag(branch, Versions.createTAG("mytag", "b1"));

		assertEquals(0, branchedVersion.getTagSpecs().size());
	}

	@Test
	public void backmerge() {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");

		share(ps);

		// checkout second trunk project
		final ProjectSpace trunk = reCheckout(ps);

		branch(ps, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		final PrimaryVersionSpec latestOnBranch = commit(ps);

		TestElement test1 = (TestElement) trunk.getProject().getModelElement(
			ps.getProject().getModelElementId(testElement));

		assertTrue(test1 != null);
		assertEquals("Horst", test1.getName());

		// merge
		mergeWithBranch(trunk, latestOnBranch, 1);

		TestElement test2 = (TestElement) trunk.getProject().getModelElement(
			ps.getProject().getModelElementId(testElement));

		assertTrue(test2 != null);
		assertEquals("J?rgen", test2.getName());
	}

	@Test
	public void backmergeMultipleCP() {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");

		share(ps);

		// checkout second trunk project
		final ProjectSpace trunk = reCheckout(ps);

		branch(ps, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		commit(ps);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("Tom");
			}
		}.run(false);

		final PrimaryVersionSpec latestOnBranch = commit(ps);

		TestElement test1 = (TestElement) trunk.getProject().getModelElement(
			ps.getProject().getModelElementId(testElement));

		assertTrue(test1 != null);
		assertEquals("Horst", test1.getName());

		// merge
		mergeWithBranch(trunk, latestOnBranch, 1);

		TestElement test2 = (TestElement) trunk.getProject().getModelElement(
			ps.getProject().getModelElementId(testElement));

		assertTrue(test2 != null);
		assertEquals("Tom", test2.getName());
	}

	@Test
	public void backmergeWithShortCut() {
		final ProjectSpace branch = getProjectSpace();
		final TestElement branchElement = createTestElement("Horst");

		share(branch);

		// checkout second trunk project
		final ProjectSpace trunk = reCheckout(branch);

		final TestElement trunkElement = (TestElement) trunk.getProject().getModelElement(
			branch.getProject().getModelElementId(branchElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				branchElement.setName("J?rgen");
			}
		}.run(false);

		PrimaryVersionSpec latestOnBranch = branch(branch, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				trunkElement.setName("G?nter");
			}
		}.run(false);

		commit(trunk);

		mergeWithBranch(trunk, latestOnBranch, 1);

		assertEquals(latestOnBranch, trunk.getMergedVersion());
		assertEquals("J?rgen", trunkElement.getName());

		// commit merged changes
		PrimaryVersionSpec mergedToVersion = commit(trunk);

		ProjectHistory projectHistory = getProjectHistory(trunk);
		Version version = projectHistory.getVersions().get(mergedToVersion.getIdentifier());
		assertEquals(1, version.getMergedFromVersion().size());

		assertTrue(trunk.getMergedVersion() == null);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				branchElement.setName("Tom");
			}
		}.run(false);

		latestOnBranch = commit(branch);

		// because of the shortcut there should be no conflict. Due to missing canonization however, there is a
		// conflict.
		mergeWithBranch(trunk, latestOnBranch, 1);

		assertEquals("Tom", trunkElement.getName());
	}

	// disabled, Otto please fix: @Test
	public void backmergeWithShortCutMultipleCp() {
		final ProjectSpace branch = getProjectSpace();
		final TestElement branchElement = createTestElement("Horst");

		share(branch);

		// checkout second trunk project
		final ProjectSpace trunk = reCheckout(branch);

		final TestElement trunkElement = (TestElement) trunk.getProject().getModelElement(
			branch.getProject().getModelElementId(branchElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				branchElement.setName("J?rgen");
			}
		}.run(false);

		PrimaryVersionSpec latestOnBranch = branch(branch, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				trunkElement.setName("G?nter");
			}
		}.run(false);

		commit(trunk);

		mergeWithBranch(trunk, latestOnBranch, 1);

		// commit merge
		commit(trunk);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				branchElement.setName("Jerry");
			}
		}.run(false);

		latestOnBranch = commit(branch);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				branchElement.setName("Tom");
			}
		}.run(false);

		latestOnBranch = commit(branch);

		// because of the shortcut there should be no conflict. Due to missing canonization however, there is a
		// conflict.
		mergeWithBranch(trunk, latestOnBranch, 1);

		assertEquals("Tom", trunkElement.getName());
	}

	@Test
	public void backmergeBranchToBranch() {
		final ProjectSpace outterBranch = getProjectSpace();
		final TestElement outterBranchElement = createTestElement("Horst");

		share(outterBranch);

		branch(outterBranch, "b1");

		// checkout second trunk project
		final ProjectSpace innerBranch = reCheckout(outterBranch);

		final TestElement innerBranchElement = (TestElement) innerBranch.getProject().getModelElement(
			outterBranch.getProject().getModelElementId(outterBranchElement));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				outterBranchElement.setName("J?rgen");
			}
		}.run(false);

		PrimaryVersionSpec latestOnBranch = branch(outterBranch, "b2");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				innerBranchElement.setName("Jerry");
			}
		}.run(false);

		commit(innerBranch);

		// merge
		mergeWithBranch(innerBranch, latestOnBranch, 1);

		assertEquals("J?rgen", innerBranchElement.getName());
	}

	@Test
	public void checkoutBranch() {
		final ProjectSpace ps = getProjectSpace();
		final TestElement testElement = createTestElement("Horst");
		share(ps);

		branch(ps, "b1");

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testElement.setName("J?rgen");
			}
		}.run(false);

		PrimaryVersionSpec lastFromBranch = commit(ps);

		ProjectSpace secondProjectSpace = reCheckout(ps);

		TestElement test = (TestElement) secondProjectSpace.getProject().getModelElement(
			ps.getProject().getModelElementId(testElement));

		assertTrue(test != null);
		assertEquals("J?rgen", test.getName());
		assertTrue(secondProjectSpace.getBaseVersion().equals(lastFromBranch));
	}
}
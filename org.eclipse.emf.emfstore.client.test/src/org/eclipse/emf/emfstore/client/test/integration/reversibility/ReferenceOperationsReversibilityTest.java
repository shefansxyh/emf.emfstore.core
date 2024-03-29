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
package org.eclipse.emf.emfstore.client.test.integration.reversibility;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.emfstore.client.test.integration.forward.IntegrationTestHelper;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Test;

/**
 * @author Hodaie
 */
public class ReferenceOperationsReversibilityTest extends OperationsReversibilityTest {

	private long randomSeed = 1;

	/**
	 * Takes a random ME (meA). Takes randomly one of its containment references. Creates a new ME matching containment
	 * reference type (meB). Adds created meB to meA's containment reference.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void containmentReferenceAddNewReversibilityTest() throws SerializationException, ESException {
		System.out.println("ContainmentReferenceAddNewReversibilityTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doContainemntReferenceAddNew();
			}

		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getTestProjectSpace().revert();

			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));

	}

	/**
	 * This takes a random model element (meA). Takes one of its containments (meToMove). Takes containing reference of
	 * meToMove. Finds another ME of type meA (meB). Moves meToMove to meB. Finds yet another ME of type meA (meC) .
	 * Moves meToMove to meC.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void containmentRefTransitiveChangeReversibilityTest() throws SerializationException, ESException {
		System.out.println("ContainmentRefTransitiveChangeReversibilityTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testHelper.doContainmentRefTransitiveChange();
			}
		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getTestProjectSpace().revert();
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * This move an element in a many reference list to another position.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void multiReferenceMoveReversibilityTest() throws SerializationException, ESException {
		System.out.println("MultiReferenceMoveReversibilityTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				testHelper.doMultiReferenceMove();
				getTestProjectSpace().revert();
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Select a random ME (meA). Select one of its non-containment references. Find an ME matching reference type (meB).
	 * Add meB to meA.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void nonContainmentReferenceAddReversibilityTest() throws SerializationException, ESException {
		System.out.println("NonContainmentReferenceAddReversibilityTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doNonContainmentReferenceAdd();
			}
		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getTestProjectSpace().revert();
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Removes a referenced model element form a non-containment reference of a randomly selected ME.
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void nonContainmentReferenceRemoveReversibilityTest() throws SerializationException, ESException {
		System.out.println("NonContainmentReferenceRemoveReversibilityTest");

		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doNonContainmentReferenceRemove();
				getTestProjectSpace().revert();
			}
		}.run(false);

		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				getTestProjectSpace().revert();
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

	/**
	 * Takes a random ME (meA). Takes randomly one of its containment references. Finds an existing ME in project
	 * matching the reference type (meB). Adds meB to this reference of meA (moves meB from its old parent to meA).
	 * 
	 * @throws ESException ESException
	 * @throws SerializationException SerializationException
	 */
	@Test
	public void containmentReferenceMoveReversibilityTest() throws SerializationException, ESException {
		System.out.println("ContainmentReferenceMoveReversibilityTest");
		final IntegrationTestHelper testHelper = new IntegrationTestHelper(randomSeed, getTestProject());
		new EMFStoreCommand() {

			@Override
			protected void doRun() {
				testHelper.doContainmentReferenceMove();
			}
		}.run(false);

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getTestProjectSpace().revert();
			}
		}.run(false);

		assertTrue(ModelUtil.areEqual(getTestProject(), getCompareProject()));
	}

}
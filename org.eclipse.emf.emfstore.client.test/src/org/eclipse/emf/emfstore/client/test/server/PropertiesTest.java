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
package org.eclipse.emf.emfstore.client.test.server;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.eclipse.emf.emfstore.client.test.testmodel.TestmodelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.ProjectSpaceBase;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.internal.client.properties.EMFStorePropertiesOutdatedException;
import org.eclipse.emf.emfstore.internal.client.properties.PropertyManager;
import org.eclipse.emf.emfstore.internal.common.model.PropertyStringValue;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesTest extends TransmissionTests {

	private static PropertyManager propertyManager1;
	private static PropertyManager propertyManager2;

	@Test
	public void testSharedProperties() throws ESException {

		propertyManager1 = getProjectSpace1().getPropertyManager();
		propertyManager2 = getProjectSpace2().getPropertyManager();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				propertyManager1.setSharedStringProperty("FirstPropKey", "test1");
				propertyManager2.setSharedStringProperty("SecondTest", "test2");

				try {
					propertyManager1.synchronizeSharedProperties();
					propertyManager2.synchronizeSharedProperties();
					propertyManager1.synchronizeSharedProperties();
				} catch (ESException e) {
					throw new RuntimeException(e);
				} catch (EMFStorePropertiesOutdatedException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// 1. Test, ob transmit funktioniert
		Assert.assertEquals("test1", propertyManager1.getSharedStringProperty("FirstPropKey"));
		Assert.assertEquals("test1", propertyManager2.getSharedStringProperty("FirstPropKey"));

		Assert.assertEquals("test2", propertyManager1.getSharedStringProperty("SecondTest"));
		Assert.assertEquals("test2", propertyManager2.getSharedStringProperty("SecondTest"));

		Assert.assertEquals(propertyManager1.getSharedStringProperty("FirstPropKey"),
			propertyManager2.getSharedStringProperty("FirstPropKey"));

		Assert.assertEquals(propertyManager2.getSharedStringProperty("SecondTest"),
			propertyManager1.getSharedStringProperty("SecondTest"));

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				propertyManager1.setSharedStringProperty("SecondTest", "test4");
				propertyManager2.setSharedStringProperty("SecondTest", "test5");

				try {
					propertyManager1.synchronizeSharedProperties();
					propertyManager2.synchronizeSharedProperties();
					propertyManager1.synchronizeSharedProperties();
				} catch (ESException e) {
					throw new RuntimeException(e);
				} catch (EMFStorePropertiesOutdatedException e) {
					throw new RuntimeException(e);
				}
			}
		}.run(false);

		// 2. Funktioniert update
		Assert.assertEquals("test5", propertyManager1.getSharedStringProperty("SecondTest"));
		Assert.assertEquals("test5", propertyManager2.getSharedStringProperty("SecondTest"));
	}

	@Test
	public void testVersionedProperty() {

		propertyManager1 = getProjectSpace1().getPropertyManager();
		propertyManager2 = getProjectSpace2().getPropertyManager();

		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				propertyManager1.setSharedVersionedStringProperty("SecondTest", "test1");
				propertyManager2.setSharedVersionedStringProperty("SecondTest", "test2");

				try {
					propertyManager1.synchronizeSharedProperties();
				} catch (Exception e) {
					junit.framework.Assert.fail();
				}

				try {
					propertyManager2.synchronizeSharedProperties();
					junit.framework.Assert.fail();
				} catch (ESException e) {
					junit.framework.Assert.fail();
				} catch (EMFStorePropertiesOutdatedException e) {
					junit.framework.Assert.assertEquals(1, e.getOutdatedProperties().size());
					Assert.assertEquals(propertyManager1.getSharedStringProperty("SecondTest"),
						((PropertyStringValue) e.getOutdatedProperties().get(0).getValue()).getValue());

				}
			}
		}.run(false);

		// check if rollback succeeded
		Assert.assertEquals("test1", propertyManager1.getSharedStringProperty("SecondTest"));
		Assert.assertEquals("test1", propertyManager2.getSharedStringProperty("SecondTest"));
	}

	@Test
	public void testLocalProperties() throws IOException {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				getProjectSpace1().getPropertyManager().setLocalProperty("foo",
					TestmodelFactory.eINSTANCE.createTestElement());
			}
		}.run(false);

		((ProjectSpaceBase) getProjectSpace1()).save();
		ProjectSpace loadedProjectSpace = ModelUtil.loadEObjectFromResource(ModelPackage.eINSTANCE.getProjectSpace(),
			getProjectSpace1().eResource().getURI(), false);

		assertNotNull(loadedProjectSpace.getPropertyManager().getLocalProperty("foo"));
	}
}
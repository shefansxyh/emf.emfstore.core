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

import org.eclipse.emf.emfstore.client.test.server.api.AllServerAPITests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * .
 * 
 * @author wesendon
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ServerInterfaceTest.class, InvalidArgumentsTest.class, // InvalidAuthenticationTest.class,
	PropertiesTest.class, FileManagerTest.class, AllServerAPITests.class, ChecksumTest.class })
public class AllServerTests {

}

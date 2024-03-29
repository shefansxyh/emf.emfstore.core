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

/**
 * <copyright> Copyright (c) 2008-2009 Jonas Helming, Maximilian Koegel. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html </copyright>
 */
package org.eclipse.emf.emfstore.client.test.integration;

import org.eclipse.emf.emfstore.client.test.integration.forward.AllForwardIntegrationTests;
import org.eclipse.emf.emfstore.client.test.integration.reversibility.AllReversibilityIntegrationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Hodaie
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AllForwardIntegrationTests.class, AllReversibilityIntegrationTests.class })
public class AllIntegrationTests {

}
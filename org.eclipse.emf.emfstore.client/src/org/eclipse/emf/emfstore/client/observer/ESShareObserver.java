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
package org.eclipse.emf.emfstore.client.observer;

import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;

/**
 * Share listeners are added to a project space and informed whenever a share is
 * executed.
 * 
 * @author pfeifferc
 */
public interface ESShareObserver extends ESObserver {

	/**
	 * Share is executed.
	 * 
	 * @param projectSpace the {@link ProjectSpace} that got shared
	 */
	void shareDone(ProjectSpace projectSpace);
}
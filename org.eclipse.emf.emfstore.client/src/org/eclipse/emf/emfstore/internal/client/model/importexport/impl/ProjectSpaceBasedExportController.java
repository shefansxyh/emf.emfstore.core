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
package org.eclipse.emf.emfstore.internal.client.model.importexport.impl;

import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.importexport.IExportImportController;

/**
 * Exports a entity which is part of a project space.
 * 
 * @author emueller
 * 
 */
public abstract class ProjectSpaceBasedExportController implements IExportImportController {

	private final ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space that contains the data to be exported
	 */
	public ProjectSpaceBasedExportController(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	/**
	 * Returns the project space.
	 * 
	 * @return the project space.
	 */
	ProjectSpace getProjectSpace() {
		return projectSpace;
	}
}
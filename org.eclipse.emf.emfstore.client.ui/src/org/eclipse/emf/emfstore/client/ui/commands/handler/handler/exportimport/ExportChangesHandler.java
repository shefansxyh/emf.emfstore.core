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
package org.eclipse.emf.emfstore.client.ui.commands.handler.handler.exportimport;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.importexport.impl.ExportChangesController;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.RequiredSelectionException;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIGenericExportImportController;

/**
 * CheckoutHandler for exporting local changes upon a selected {@link ProjectSpace}.
 * 
 * @author emueller
 */
public class ExportChangesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIGenericExportImportController(getShell(), new ExportChangesController(
				requireSelection(ProjectSpace.class))).execute();
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		}
	}

}
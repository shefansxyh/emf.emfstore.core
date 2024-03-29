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
package org.eclipse.emf.emfstore.internal.client.ui.views.emfstorebrowser.views;

import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * Wizard for adding a new repository.
 * 
 * @author shterev
 */
public class NewRepositoryWizard extends Wizard implements INewWizard {

	private ServerInfo serverInfo;

	private NewRepositoryWizardPageOne mainPage;

	/**
	 * Default constructor.
	 */
	public NewRepositoryWizard() {
		super();
	}

	/**
	 * Adds all pages in the wizard.
	 */
	@Override
	public void addPages() {
		mainPage = new NewRepositoryWizardPageOne();
		setWindowTitle("Server Details");
		addPage(mainPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canFinish() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {
		if (this.getContainer().getCurrentPage().canFlipToNextPage()) {
			ESWorkspaceProviderImpl.getInstance().getWorkspace().getInternalAPIImpl().addServerInfo(serverInfo);
			dispose();
		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
				"Field(s) were left blank!");
			return false;
		}
		return true;
	}

	/**
	 * Getter for the ServerInfo.
	 * 
	 * @return the {@link ServerInfo}
	 */
	public ServerInfo getServerInfo() {
		if (serverInfo == null) {
			serverInfo = ModelFactory.eINSTANCE.createServerInfo();
		}
		return serverInfo;
	}

	/**
	 * Sets the ServerInfo.
	 * 
	 * @param serverInfo
	 *            {@link ServerInfo}
	 */
	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// nothing to do
	}

}
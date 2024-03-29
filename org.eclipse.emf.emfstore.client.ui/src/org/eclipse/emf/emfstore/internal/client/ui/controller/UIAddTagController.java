/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * UI controller for creating a tag for a project.
 * 
 * @author emueller
 * 
 */
public class UIAddTagController extends AbstractEMFStoreUIController<Void> {

	private final HistoryInfo historyInfo;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the shell that will be used to create the tag
	 * @param historyInfo
	 *            the {@link HistoryInfo} object that is needed to determine the version for which to create a tag
	 */
	public UIAddTagController(Shell shell, HistoryInfo historyInfo) {
		super(shell);
		this.historyInfo = historyInfo;
	}

	private HistoryBrowserView getHistoryBrowserViewFromActivePart() {
		// TODO: controller currently does not work if the active workbench window is not
		// the history view
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();

		if (activePage == null || !(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return null;
		}

		return (HistoryBrowserView) activePage.getActivePart();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {

		HistoryBrowserView historyBrowserView = getHistoryBrowserViewFromActivePart();

		if (historyBrowserView == null) {
			return null;
		}

		final PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimarySpec());

		InputDialog inputDialog = new InputDialog(getShell(), "Add tag", "Please enter the tag's name.", "Tag@"
			+ new Date(), null);

		if (inputDialog.open() != Window.OK) {
			return null;
		}

		final String tagName = inputDialog.getValue().trim();
		final ProjectSpace projectSpace = historyBrowserView.getProjectSpace();

		if (tagName != null && tagName.length() > 0) {

			TagVersionSpec tag = VersioningFactory.eINSTANCE.createTagVersionSpec();
			tag.setName(tagName);

			try {
				// TODO: monitor
				projectSpace.addTag(versionSpec, tag);
			} catch (ESException e) {
				WorkspaceUtil.logException(e.getMessage(), e);
				MessageDialog.openError(getShell(), "Error", "Could not create tag. Reason: " + e.getMessage());
				return null;
			}
			// also add tag to the selected history info
			historyInfo.getTagSpecs().add(tag);
			historyBrowserView.refresh();
		}

		return null;
	}
}
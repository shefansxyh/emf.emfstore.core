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
package org.eclipse.emf.emfstore.internal.client.ui.controller;

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.ui.common.RunInUI;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.internal.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * UI controller responsible for opening up the history view.
 * 
 * @author emueller
 * 
 */
public class UIShowHistoryController extends AbstractEMFStoreUIController<Void> {

	private final EObject modelElement;

	/**
	 * Constructor.
	 * 
	 * @param shell
	 *            the parent {@link Shell}
	 * @param modelElement
	 *            the model element whose history should be queried
	 */
	public UIShowHistoryController(Shell shell, EObject modelElement) {
		super(shell, true, true);
		this.modelElement = modelElement;
	}

	public UIShowHistoryController(Shell shell, ESLocalProject localProject) {
		super(shell, true, true);
		this.modelElement = ((ESLocalProjectImpl) localProject).getInternalAPIImpl();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.common.MonitoredEMFStoreAction#doRun(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Void doRun(IProgressMonitor monitor) throws ESException {

		RunInUI.run(new Callable<Void>() {
			public Void call() throws Exception {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				HistoryBrowserView historyBrowserView = null;
				// TODO: remove hard-coded reference
				String viewId = "org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView";

				try {
					historyBrowserView = (HistoryBrowserView) page.showView(viewId);
				} catch (PartInitException e) {
					EMFStoreMessageDialog.showExceptionDialog(getShell(), e);
				}

				if (historyBrowserView != null) {
					historyBrowserView.setInput(modelElement);
				}
				return null;
			}
		});

		return null;
	}
}
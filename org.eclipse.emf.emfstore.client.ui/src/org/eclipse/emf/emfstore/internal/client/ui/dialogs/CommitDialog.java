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
package org.eclipse.emf.emfstore.internal.client.ui.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionElement;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;
import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPointException;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.ui.Activator;
import org.eclipse.emf.emfstore.internal.client.ui.views.changes.TabbedChangesComposite;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This class shows a ChangesTreeComposite and a Text control to enter commit
 * message.
 * 
 * @author Hodaie
 * @author Shterev
 */
public class CommitDialog extends EMFStoreTitleAreaDialog implements
	KeyListener {

	private Text txtLogMsg;
	private String logMsg = "";
	private ChangePackage changes;
	private EList<String> oldLogMessages;
	private ProjectSpace activeProjectSpace;
	private Map<String, CommitDialogTray> trays;
	private Image commitImage;
	private int numberOfChanges;
	private final ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            shell
	 * @param changes
	 *            the {@link ChangePackage} to be displayed
	 * @param activeProjectSpace
	 *            ProjectSpace that will be committed
	 * @param idToEObjectMapping
	 *            a mapping between ModelElementIds and EObjects. This is needed
	 *            correctly infer information about deleted model elements
	 */
	public CommitDialog(Shell parentShell, ChangePackage changes, ProjectSpace activeProjectSpace,
		ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
		super(parentShell);
		this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
		this.idToEObjectMapping = idToEObjectMapping;
		this.changes = changes;
		this.activeProjectSpace = activeProjectSpace;
		this.numberOfChanges = changes.getSize();
		this.trays = new LinkedHashMap<String, CommitDialogTray>();

		for (ESExtensionElement element : new ESExtensionPoint(
			"org.eclipse.emf.emfstore.client.ui.commitdialog.tray", true)
			.getExtensionElements()) {
			try {
				CommitDialogTray tray = element.getClass("class",
					CommitDialogTray.class);
				String name = element.getAttribute("name");
				tray.init(CommitDialog.this);
				trays.put(name, tray);
			} catch (ESExtensionPointException e) {
				// fail silently
			}
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.ui.dialogs.EMFStoreTitleAreaDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Commit");
		commitImage = Activator.getImageDescriptor("icons/arrow_right.png")
			.createImage();
		newShell.setImage(commitImage);
	}

	/**
	 * Returns the change package displayed by the commit dialog.
	 * 
	 * @return the change package
	 */
	public ChangePackage getChangePackage() {
		return changes;
	}

	/**
	 * Returns the active project space.
	 * 
	 * @return the active project space
	 */
	public ProjectSpace getActiveProjectSpace() {
		return activeProjectSpace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		oldLogMessages = activeProjectSpace.getOldLogMessages();

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		contents.setLayout(new GridLayout(2, false));
		String projectName = "";
		if (activeProjectSpace.getProjectName() != null
			&& activeProjectSpace.getProjectName().length() > 0) {
			projectName = "of project \"" + activeProjectSpace.getProjectName()
				+ "\" ";
		}
		setTitle("Commit your local changes " + projectName + "to the server");
		setMessage("Number of composite changes: "
			+ changes.getOperations().size()
			+ ", Number of overall changes: " + numberOfChanges);

		// Log message
		Label lblLogMsg = new Label(contents, SWT.NONE);
		lblLogMsg.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
			false, 2, 1));
		lblLogMsg.setText("Log message:");

		txtLogMsg = new Text(contents, SWT.MULTI | SWT.LEAD | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
			.align(SWT.FILL, SWT.TOP).hint(1, 45).applyTo(txtLogMsg);
		String logMsg = "";
		LogMessage logMessage = changes.getLogMessage();

		if (logMessage != null && logMessage.getMessage() != null
			&& !logMessage.getMessage().equals("")) {
			// if change package has log message attached to it, use it
			logMsg = logMessage.getMessage();
		} else if (oldLogMessages != null && oldLogMessages.size() > 0) {
			// otherwise use the most recent log message
			logMsg = oldLogMessages.get(oldLogMessages.size() - 1);
		}
		txtLogMsg.setText(logMsg);
		txtLogMsg.selectAll();
		// to implement a shortcut for submitting the commit
		txtLogMsg.addKeyListener(this);

		// previous log messages
		Label oldLabel = new Label(contents, SWT.NONE);
		oldLabel.setText("Previous messages:");
		final Combo oldMsg = new Combo(contents, SWT.READ_ONLY);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP)
			.grab(true, false).applyTo(oldMsg);

		ArrayList<String> oldLogMessagesCopy = new ArrayList<String>();
		oldLogMessagesCopy.addAll(oldLogMessages);
		Collections.reverse(oldLogMessagesCopy);
		oldMsg.setItems(oldLogMessagesCopy.toArray(new String[0]));
		oldMsg.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// nothing to do here
			}

			public void widgetSelected(SelectionEvent e) {
				txtLogMsg.setText(oldMsg.getItem(oldMsg.getSelectionIndex()));
			}

		});

		if (oldLogMessages.size() > 0) {
			oldMsg.select(0);
		}

		// ChangesTree
		ArrayList<ChangePackage> changePackages = new ArrayList<ChangePackage>();
		changePackages.add(changes);
		TabbedChangesComposite changesComposite = new TabbedChangesComposite(
			contents, SWT.BORDER, changePackages, getActiveProjectSpace()
				.getProject(), idToEObjectMapping, true);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
			.grab(true, true).span(2, 1).applyTo(changesComposite);

		return contents;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void okPressed() {

		logMsg = txtLogMsg.getText();

		// suppress duplicates
		if (!oldLogMessages.contains(logMsg)) {
			oldLogMessages.add(logMsg);
		}

		// remove older messages
		if (oldLogMessages.size() > 10) {
			// the list can only grow one element at a time,
			// so only one element should be deleted
			oldLogMessages.remove(0);
		}

		for (CommitDialogTray t : trays.values()) {
			t.okPressed();
		}

		super.okPressed();
	}

	@Override
	public boolean close() {
		commitImage.dispose();
		return super.close();
	}

	/**
	 * @return the log message that has been set by the user.
	 */
	public String getLogText() {
		return logMsg.equals("") ? "<Empty log message>" : logMsg;
	}

	/**
	 * handles the pressing of Ctrl+ENTER: OKpressed() is called. {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (e.keyCode == SWT.CR && (e.stateMask & SWT.MOD1) != 0) {
			this.okPressed();
		}
	}

	/**
	 * does nothing. {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// final String notifyUsers = "Notify users";
		for (ESExtensionElement c : new ESExtensionPoint(
			"org.eclipse.emf.emfstore.client.ui.commitdialog.tray")
			.getExtensionElements()) {
			final String name = c.getAttribute("name");
			final CommitDialogTray tray = trays.get(name);
			if (tray != null) {
				final Button notificationsButton = createButton(parent, 2138,
					name + " >>", false);
				notificationsButton
					.addSelectionListener(new SelectionAdapter() {
						private boolean isOpen;

						@Override
						public void widgetSelected(SelectionEvent e) {
							if (!isOpen) {
								openTray(tray);
								notificationsButton.setText(name + " <<");
								Rectangle bounds = getShell().getBounds();
								bounds.x -= 100;
								getShell().setBounds(bounds);
							} else {
								closeTray();
								notificationsButton.setText(name + " >>");
								Rectangle bounds = getShell().getBounds();
								bounds.x += 100;
								getShell().setBounds(bounds);
							}
							isOpen = !isOpen;
						}
					});
			}
		}
		super.createButtonsForButtonBar(parent);
	}

	/**
	 * @return the operations.
	 */
	public List<AbstractOperation> getOperations() {
		return changes.getOperations();
	}

}
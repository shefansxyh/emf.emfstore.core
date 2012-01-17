package org.eclipse.emf.emfstore.client.ui.commands.handlers;

import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.ui.commands.controller.UIServerLoginController;

public class ServerLoginHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIServerLoginController(getShell(), requireSelection(ServerInfo.class)).openLoginDialog();
	}

}
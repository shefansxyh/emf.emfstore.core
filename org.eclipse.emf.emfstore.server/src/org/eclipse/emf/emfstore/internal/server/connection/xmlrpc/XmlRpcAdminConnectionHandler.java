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
package org.eclipse.emf.emfstore.internal.server.connection.xmlrpc;

import org.eclipse.emf.emfstore.internal.server.AdminEmfStore;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.AuthenticationControl;
import org.eclipse.emf.emfstore.internal.server.connection.ConnectionHandler;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * Connection Handler for XML RPC AdminEmfstore interface.
 * 
 * @author wesendon
 */
public class XmlRpcAdminConnectionHandler implements ConnectionHandler<AdminEmfStore> {

	/**
	 * String interface identifier.
	 */
	public static final String ADMINEMFSTORE = "AdminEmfStore";

	private static final String NAME = "XML RPC Admin Connection Handler";

	private static AdminEmfStore adminEmfStore;

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("static-access")
	public synchronized void init(AdminEmfStore adminEmfStore, AuthenticationControl accessControl)
		throws FatalESException {
		this.adminEmfStore = adminEmfStore;
		XmlRpcWebserverManager webServer = XmlRpcWebserverManager.getInstance();
		webServer.initServer();
		webServer.addHandler(ADMINEMFSTORE, XmlRpcAdminEmfStoreImpl.class);
	}

	/**
	 * Returns AdminEmfstore.
	 * 
	 * @return admin emfstore
	 */
	public static AdminEmfStore getAdminEmfStore() {
		return adminEmfStore;
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop(boolean force) {
		XmlRpcWebserverManager webserverManager = XmlRpcWebserverManager.getInstance();
		if (!webserverManager.removeHandler(ADMINEMFSTORE)) {
			webserverManager.stopServer();
		}
	}

}
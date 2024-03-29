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
package org.eclipse.emf.emfstore.internal.client.model.util;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.client.model.impl.WorkspaceBase;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Utility class for EMFStore clients to ease connecting to the server.
 * 
 * @author koegel
 */
public final class EMFStoreClientUtil {

	private static final String LOCALHOST_GENERATED_ENTRY_NAME = "EMFStore (generated entry)";

	/**
	 * Private constructor for utility class.
	 */
	private EMFStoreClientUtil() {
		// do nothing
	}

	/**
	 * Gives a server info for a given port and URL. Searches first for already existing ones. If the search fails, it
	 * creates a new one and registers it for later lookup.
	 * 
	 * @param url the server URL (e.g. IP address or DNS name)
	 * @param port the server port
	 * @return a server info
	 */
	public static ServerInfo giveServerInfo(String url, int port) {

		ESWorkspaceImpl workspace = ESWorkspaceProviderImpl.getInstance().getWorkspace();

		for (ServerInfo existingServerInfo : workspace.getInternalAPIImpl().getServerInfos()) {
			if (existingServerInfo.getName().equals(LOCALHOST_GENERATED_ENTRY_NAME)) {
				if (url.equals(existingServerInfo.getUrl()) && port == existingServerInfo.getPort()) {
					return existingServerInfo;
				}
			}
		}
		ServerInfo serverInfo = createServerInfo(url, port, null);
		workspace.getInternalAPIImpl().getServerInfos().add(serverInfo);
		// TODO: OTS
		((WorkspaceBase) workspace.getInternalAPIImpl()).save();
		return serverInfo;
	}

	/**
	 * Create a server info for a given port and URL.
	 * 
	 * @param url the server URL (e.g. IP address or DNS name)
	 * @param port the server port
	 * @param certificateAlias the certificateAlias (defaults to {@link KeyStoreManager.DEFAULT_CERTIFICATE})
	 * @return a server info
	 */
	public static ServerInfo createServerInfo(String url, int port, String certificateAlias) {
		ServerInfo serverInfo = ModelFactory.eINSTANCE.createServerInfo();
		serverInfo.setName(LOCALHOST_GENERATED_ENTRY_NAME);
		serverInfo.setUrl(url);
		serverInfo.setPort(port);
		if (certificateAlias == null) {
			serverInfo.setCertificateAlias(KeyStoreManager.DEFAULT_CERTIFICATE);
		} else {
			serverInfo.setCertificateAlias(certificateAlias);
		}
		return serverInfo;
	}

	/**
	 * Create a default user session with the default super user and password and a server on localhost on the default
	 * port.
	 * 
	 * @return a user session
	 */
	public static Usersession createUsersession() {
		return createUsersession("super", "super", "localhost", 8080);
	}

	/**
	 * Create a {@link Usersession} for the given credentials and server info.
	 * 
	 * @param username the user name
	 * @param password the password
	 * @param serverUrl server URL
	 * @param serverPort server port
	 * @return a user session
	 */
	public static Usersession createUsersession(String username, String password, String serverUrl, int serverPort) {
		ESWorkspaceImpl workspace = ESWorkspaceProviderImpl.getInstance().getWorkspace();
		for (Usersession usersession : workspace.getInternalAPIImpl().getUsersessions()) {
			ServerInfo existingServerInfo = usersession.getServerInfo();
			if (existingServerInfo != null && existingServerInfo.getName().equals(LOCALHOST_GENERATED_ENTRY_NAME)
				&& existingServerInfo.getUrl().equals(serverUrl) && existingServerInfo.getPort() == serverPort) {
				String encPassword = KeyStoreManager.getInstance().encrypt(password, existingServerInfo);
				if (username.equals(usersession.getUsername()) && encPassword.equals(usersession.getPassword())) {
					return usersession;
				}
			}
		}
		Usersession usersession = ModelFactory.eINSTANCE.createUsersession();
		usersession.setServerInfo(giveServerInfo(serverUrl, serverPort));
		usersession.setUsername(username);
		usersession.setPassword(password);
		workspace.getInternalAPIImpl().getUsersessions().add(usersession);
		// TODO: OTS
		((WorkspaceBase) workspace.getInternalAPIImpl()).save();
		return usersession;
	}

	/**
	 * Checks, if the given credentials can be authenticated at the given server.
	 * 
	 * @param username the user name
	 * @param password the password
	 * @param serverUrl server url
	 * @param serverPort server port
	 * @param certificateAlias the certificateAlias (defaults to {@link KeyStoreManager.DEFAULT_CERTIFICATE})
	 * @return true, if user name & password are right
	 * @throws ESException Problem with the EMFStore Server
	 */
	public static boolean dryLogin(String username, String password, String serverUrl, int serverPort,
		String certificateAlias) throws ESException {
		Usersession usersession = ModelFactory.eINSTANCE.createUsersession();
		usersession.setServerInfo(createServerInfo(serverUrl, serverPort, certificateAlias));
		usersession.setUsername(username);
		usersession.setPassword(password);
		try {
			usersession.logIn();
		} catch (AccessControlException e) {
			return false;
		}
		return true;
	}

	public static boolean areEqual(ESLocalProject projectA, ESLocalProject projectB) {
		ProjectSpace projectSpaceA = ((ESLocalProjectImpl) projectA).getInternalAPIImpl();
		ProjectSpace projectSpaceB = ((ESLocalProjectImpl) projectB).getInternalAPIImpl();

		return ModelUtil.areEqual(projectSpaceA.getProject(), projectSpaceB.getProject());
	}
}
/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client;

import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.ISessionId;

/**
 * User session for a given {@link IServer}. Can be used with multiple projects.
 * An user session is gained by calling {@link IServer#login(String, String)}
 * 
 * @author emueller
 * @author wesendon
 */
public interface IUsersession {

	/**
	 * Returns the usersession's server.
	 * 
	 * @return the server this user session is associated with
	 */
	IServer getServer();

	/**
	 * Returns the name of the user this user session is associated with.
	 * 
	 * @return username
	 *         the name of the user this user session is associated with
	 */
	String getUsername();

	/**
	 * Returns the password. The password is encrypted with the server's public key, so there's no access to the
	 * cleartext password.
	 * 
	 * @return the encrypted password
	 */
	String getPassword();

	/**
	 * Checks whether the user session has a {@link ISessionId}.
	 * 
	 * @return true, if session is logged in, false otherwise
	 */
	// TODO: use isLoggedIn of connection layer? or even check with server?
	boolean isLoggedIn();

	/**
	 * Relogins into the server using the same credentials in order to update the {@link ISessionId}.
	 * 
	 * @throws EMFStoreException in case renewal of the session failed
	 */
	// TODO: mention how long an user session valid
	void renew() throws EMFStoreException;

	/**
	 * Logs out the user session.
	 * 
	 * @throws EMFStoreException in case an error occurred during logout
	 */
	void logout() throws EMFStoreException;

	/**
	 * Returns the session id of this session.
	 * 
	 * @return the current session id
	 */
	ISessionId getSessionId();
}

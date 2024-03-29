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
package org.eclipse.emf.emfstore.internal.server.exceptions;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Represents a condition where a session is not known.
 * 
 * @author koegel
 */
@SuppressWarnings("serial")
public class UnknownSessionException extends ESException {

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 * @param cause underlying exception
	 */
	public UnknownSessionException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * Default constructor.
	 * 
	 * @param message the message
	 */
	public UnknownSessionException(String message) {
		super(message);
	}

}
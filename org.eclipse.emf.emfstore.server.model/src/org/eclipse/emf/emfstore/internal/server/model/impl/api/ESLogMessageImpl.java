/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import java.util.Date;

import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;

/**
 * Mapping between {@link ESLogMessage} and {@link LogMessage}.
 * 
 * @author emueller
 * 
 */
public class ESLogMessageImpl extends AbstractAPIImpl<ESLogMessageImpl, LogMessage> implements ESLogMessage {

	/**
	 * Constructor.
	 * 
	 * @param logMessage
	 *            the delegate
	 */
	public ESLogMessageImpl(LogMessage logMessage) {
		super(logMessage);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESLogMessage#getAuthor()
	 */
	public String getAuthor() {
		return getInternalAPIImpl().getAuthor();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESLogMessage#getClientDate()
	 */
	public Date getClientDate() {
		return getInternalAPIImpl().getClientDate();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.server.model.ESLogMessage#getMessage()
	 */
	public String getMessage() {
		return getInternalAPIImpl().getMessage();
	}

}

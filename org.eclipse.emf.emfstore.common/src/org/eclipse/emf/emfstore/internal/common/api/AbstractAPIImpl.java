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
package org.eclipse.emf.emfstore.internal.common.api;

/**
 * <p>
 * Common base class for all API implementation classes.
 * </p>
 * <p>
 * Given an internal type that defines an {@link APIDelegate} to a API implementation type 
 * and a concrete API implementation class that delegates to the internal type by means of the
 * {@link InternalAPIDelegator} interface, this class is responsible for setting up the mapping 
 * from the API implementation type to the internal one. 
 * </p>
 * <p>
 * It is necessary to specify the API implementation class because the internal types must be  
 * able to instantiate the type they are mapping to.
 * </p> 
 * 
 *   
 * @author emueller
 *
 * @param <INTERNAL_TYPE> the internal API type to be mapped onto an API implementation class
 * @param <API_IMPL_TYPE> the type that implements an API interface
 * 
 * @see InternalAPIDelegator
 */
public abstract class AbstractAPIImpl<API_IMPL_TYPE, INTERNAL_TYPE extends APIDelegate<API_IMPL_TYPE>> 
	implements InternalAPIDelegator<API_IMPL_TYPE, INTERNAL_TYPE> {

	private INTERNAL_TYPE internal;

	protected AbstractAPIImpl(INTERNAL_TYPE internalType) {
		this.internal = internalType;
	}

	public INTERNAL_TYPE getInternalAPIImpl() {
		return internal;
	}

}

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
package org.eclipse.emf.emfstore.internal.server.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.model.impl.UniqueIdentifierImpl;
import org.eclipse.emf.emfstore.internal.server.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESSessionIdImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Session Id</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class SessionIdImpl extends UniqueIdentifierImpl implements SessionId, APIDelegate<ESSessionIdImpl> {

	/**
	 * @generated NOT
	 */
	private ESSessionIdImpl apiImpl;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SessionIdImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SESSION_ID;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#getAPIImpl()
	 * 
	 * @generated NOT
	 */
	public ESSessionIdImpl getAPIImpl() {
		if (apiImpl == null) {
			apiImpl = createAPIImpl();
		}
		return apiImpl;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.api.APIDelegate#createAPIImpl()
	 * 
	 * @generated NOT
	 */
	public ESSessionIdImpl createAPIImpl() {
		return new ESSessionIdImpl(this);
	}

} // SessionIdImpl
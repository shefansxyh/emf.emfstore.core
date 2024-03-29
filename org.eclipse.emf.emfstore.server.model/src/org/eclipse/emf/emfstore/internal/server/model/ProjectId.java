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
package org.eclipse.emf.emfstore.internal.server.model;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.model.UniqueIdentifier;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESGlobalProjectIdImpl;
import org.eclipse.emf.emfstore.server.model.ESGlobalProjectId;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Project Id</b></em>'.
 * 
 * @extends {@link ESGlobalProjectId} <!-- end-user-doc -->
 * 
 * 
 * @see org.eclipse.emf.emfstore.internal.common.model.server.model.ModelPackage#getProjectId()
 * @model
 * @generated
 */
public interface ProjectId extends UniqueIdentifier, APIDelegate<ESGlobalProjectIdImpl> {
} // ProjectId
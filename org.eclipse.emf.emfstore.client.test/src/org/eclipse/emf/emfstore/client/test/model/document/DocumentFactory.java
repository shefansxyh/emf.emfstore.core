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
package org.eclipse.emf.emfstore.client.test.model.document;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.internal.client.test.model.document.DocumentPackage
 * @generated
 */
public interface DocumentFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	DocumentFactory eINSTANCE = org.eclipse.emf.emfstore.client.test.model.document.impl.DocumentFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Leaf Section</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Leaf Section</em>'.
	 * @generated
	 */
	LeafSection createLeafSection();

	/**
	 * Returns a new object of class '<em>Composite Section</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Composite Section</em>'.
	 * @generated
	 */
	CompositeSection createCompositeSection();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	DocumentPackage getDocumentPackage();

} // DocumentFactory
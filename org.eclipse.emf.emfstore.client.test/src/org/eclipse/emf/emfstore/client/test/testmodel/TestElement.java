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
package org.eclipse.emf.emfstore.client.test.testmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Test Element</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getStrings <em>Strings</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getReferences <em>References</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getContainedElements <em>Contained
 * Elements</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getReference <em>Reference</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getContainedElement <em>Contained
 * Element</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getOtherReference <em>Other Reference
 * </em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getContainedElements2 <em>Contained
 * Elements2</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getContainer <em>Container</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getElementMap <em>Element Map</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getStringToStringMap <em>String To
 * String Map</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getElementToStringMap <em>Element To
 * String Map</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getStringToElementMap <em>String To
 * Element Map</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement()
 * @model
 * @generated
 */
public interface TestElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getName
	 * <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Strings</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strings</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Strings</em>' attribute list.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_Strings()
	 * @model
	 * @generated
	 */
	EList<String> getStrings();

	/**
	 * Returns the value of the '<em><b>References</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>References</em>' reference list.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_References()
	 * @model
	 * @generated
	 */
	EList<TestElement> getReferences();

	/**
	 * Returns the value of the '<em><b>Contained Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Elements</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contained Elements</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_ContainedElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestElement> getContainedElements();

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(TestElement)
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_Reference()
	 * @model
	 * @generated
	 */
	TestElement getReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getReference
	 * <em>Reference</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(TestElement value);

	/**
	 * Returns the value of the '<em><b>Contained Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Element</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contained Element</em>' containment reference.
	 * @see #setContainedElement(TestElement)
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_ContainedElement()
	 * @model containment="true"
	 * @generated
	 */
	TestElement getContainedElement();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getContainedElement
	 * <em>Contained Element</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Contained Element</em>' containment reference.
	 * @see #getContainedElement()
	 * @generated
	 */
	void setContainedElement(TestElement value);

	/**
	 * Returns the value of the '<em><b>Other Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Other Reference</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Other Reference</em>' reference.
	 * @see #setOtherReference(TestElement)
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_OtherReference()
	 * @model
	 * @generated
	 */
	TestElement getOtherReference();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getOtherReference
	 * <em>Other Reference</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Other Reference</em>' reference.
	 * @see #getOtherReference()
	 * @generated
	 */
	void setOtherReference(TestElement value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getDescription
	 * <em>Description</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Contained Elements2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Elements2</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contained Elements2</em>' containment reference list.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_ContainedElements2()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestElement> getContainedElements2();

	/**
	 * Returns the value of the '<em><b>Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '
	 * {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElementContainer#getElements
	 * <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container</em>' container reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Container</em>' container reference.
	 * @see #setContainer(TestElementContainer)
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_Container()
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElementContainer#getElements
	 * @model opposite="elements" transient="false"
	 * @generated
	 */
	TestElementContainer getContainer();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement#getContainer
	 * <em>Container</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Container</em>' container reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(TestElementContainer value);

	/**
	 * Returns the value of the '<em><b>Element Map</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement},
	 * and the value is of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Map</em>' map isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Element Map</em>' map.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_ElementMap()
	 * @model mapType=
	 *        "org.eclipse.emf.emfstore.client.test.testmodel.TestElementToTestElementMap<org.eclipse.emf.emfstore.client.test.testmodel.TestElement, org.eclipse.emf.emfstore.client.test.testmodel.TestElement>"
	 * @generated
	 */
	EMap<TestElement, TestElement> getElementMap();

	/**
	 * Returns the value of the '<em><b>String To String Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String To String Map</em>' map isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>String To String Map</em>' map.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_StringToStringMap()
	 * @model mapType=
	 *        "org.eclipse.emf.emfstore.client.test.testmodel.StringToStringMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getStringToStringMap();

	/**
	 * Returns the value of the '<em><b>Element To String Map</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element To String Map</em>' map isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Element To String Map</em>' map.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_ElementToStringMap()
	 * @model mapType=
	 *        "org.eclipse.emf.emfstore.client.test.testmodel.TestElementToStringMap<org.eclipse.emf.emfstore.client.test.testmodel.TestElement, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<TestElement, String> getElementToStringMap();

	/**
	 * Returns the value of the '<em><b>String To Element Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.emf.emfstore.internal.client.test.testmodel.TestElement},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String To Element Map</em>' map isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>String To Element Map</em>' map.
	 * @see org.eclipse.emf.emfstore.internal.client.test.testmodel.TestmodelPackage#getTestElement_StringToElementMap()
	 * @model mapType=
	 *        "org.eclipse.emf.emfstore.client.test.testmodel.StringToTestElementMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.emfstore.client.test.testmodel.TestElement>"
	 * @generated
	 */
	EMap<String, TestElement> getStringToElementMap();

} // TestElement
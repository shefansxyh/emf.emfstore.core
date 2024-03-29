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
package org.eclipse.emf.emfstore.internal.client.ui.views.changes;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.emfstore.common.ESDisposable;
import org.eclipse.emf.emfstore.common.model.ESModelElementId;
import org.eclipse.emf.emfstore.common.model.ESModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.client.ui.Activator;
import org.eclipse.emf.emfstore.internal.common.model.ModelElementId;
import org.eclipse.emf.emfstore.internal.common.model.ModelFactory;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.provider.AbstractOperationCustomLabelProvider;
import org.eclipse.emf.emfstore.internal.server.model.provider.CustomOperationLabelProviderManager;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AttributeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CreateDeleteOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceMoveOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.MultiReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.ReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.SingleReferenceOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.UnkownFeatureException;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.provider.AbstractOperationItemProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A helper class for the visualization of change packages.
 * 
 * @author koegel
 * @author shterev
 * @author emueller
 */
public class ChangePackageVisualizationHelper implements ESDisposable {

	private DefaultOperationLabelProvider defaultOperationLabelProvider;
	private ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping;

	/**
	 * Constructor.
	 * 
	 * @param idToEObjectMapping
	 *            the ID to EObject mapping that is holding the EObjects that are going to be visualized
	 *            as part of the change packages
	 */
	public ChangePackageVisualizationHelper(ESModelElementIdToEObjectMapping<ESModelElementId> idToEObjectMapping) {
		defaultOperationLabelProvider = new DefaultOperationLabelProvider();
		this.idToEObjectMapping = idToEObjectMapping;
	}

	/**
	 * Get the overlay image for an operation.
	 * 
	 * @param operation
	 *            the operation
	 * @return the ImageDescriptor
	 */
	public ImageDescriptor getOverlayImage(AbstractOperation operation) {
		String overlay = null;
		if (operation instanceof CreateDeleteOperation) {
			CreateDeleteOperation op = (CreateDeleteOperation) operation;
			if (op.isDelete()) {
				overlay = "icons/delete_overlay.png";
			} else {
				overlay = "icons/add_overlay.png";
			}
		} else if (operation instanceof AttributeOperation) {
			AttributeOperation op = (AttributeOperation) operation;
			if (op.getNewValue() == null) {
				overlay = "icons/delete_overlay.png";
			} else if (op.getOldValue() == null) {
				overlay = "icons/add_overlay.png";
			} else {
				overlay = "icons/modify_overlay.png";
			}
		} else if (operation instanceof SingleReferenceOperation) {
			SingleReferenceOperation op = (SingleReferenceOperation) operation;
			if (op.getNewValue() == null) {
				overlay = "icons/delete_overlay.png";
			} else {
				overlay = "icons/link_overlay.png";
			}
		} else if (operation instanceof MultiReferenceOperation) {
			MultiReferenceOperation op = (MultiReferenceOperation) operation;
			if (op.getReferencedModelElements().size() > 0) {
				overlay = "icons/link_overlay.png";
			}
		} else if (operation instanceof MultiReferenceMoveOperation) {
			overlay = "icons/link_overlay.png";
		} else {
			overlay = "icons/modify_overlay.png";
		}

		ImageDescriptor overlayDescriptor = Activator.getImageDescriptor(overlay);
		return overlayDescriptor;
	}

	/**
	 * Get an image for the operation.
	 * 
	 * @param emfProvider
	 *            the label provider
	 * @param operation
	 *            the operation
	 * @return an image
	 */
	public Image getImage(ILabelProvider emfProvider, AbstractOperation operation) {

		// check if a custom label provider can provide an image
		Image image = getCustomOperationProviderLabel(operation);
		if (image != null) {
			return image;
		}

		return emfProvider.getImage(operation);
	}

	private Image getCustomOperationProviderLabel(AbstractOperation operation) {
		AbstractOperationCustomLabelProvider customLabelProvider = CustomOperationLabelProviderManager.getInstance()
			.getCustomLabelProvider(operation);
		if (customLabelProvider != null) {
			try {
				return (Image) customLabelProvider.getImage(operation);
				// BEGIN SUPRESS CATCH EXCEPTION
			} catch (RuntimeException e) {
				// END SUPRESS CATCH EXCEPTION
				ModelUtil.logWarning("Image load from custom operation item provider failed!", e);
			}
		}
		return null;
	}

	/**
	 * Returns a description for the given operation.
	 * 
	 * @param op
	 *            the operation to generate a description for
	 * @return the description for the given operation
	 */
	public String getDescription(AbstractOperation op) {

		// check of a custom operation label provider can provide a label
		AbstractOperationCustomLabelProvider customLabelProvider = CustomOperationLabelProviderManager.getInstance()
			.getCustomLabelProvider(op);

		if (customLabelProvider != null) {
			return decorate(customLabelProvider, op);
		}

		if (op instanceof CompositeOperation) {
			CompositeOperation compositeOperation = (CompositeOperation) op;
			// artificial composite because of opposite ref, take description of
			// main operation
			if (compositeOperation.getMainOperation() != null) {
				return getDescription(compositeOperation.getMainOperation());
			}
		}
		return decorate(defaultOperationLabelProvider, op);
	}

	private String decorate(AbstractOperationCustomLabelProvider labelProvider, AbstractOperation op) {
		String namesResolved = resolveIds(labelProvider, labelProvider.getDescription(op),
			AbstractOperationItemProvider.NAME_TAG__SEPARATOR, op);
		String allResolved = resolveIds(labelProvider, namesResolved,
			AbstractOperationItemProvider.NAME_CLASS_TAG_SEPARATOR, op);
		if (op instanceof ReferenceOperation) {
			return resolveTypes(allResolved, (ReferenceOperation) op);
		}
		if (op instanceof CompositeOperation && ((CompositeOperation) op).getMainOperation() != null
			&& ((CompositeOperation) op).getMainOperation() instanceof ReferenceOperation) {
			return resolveTypes(allResolved, (ReferenceOperation) ((CompositeOperation) op).getMainOperation());
		}

		return allResolved;
	}

	private String resolveTypes(String unresolvedString, ReferenceOperation op) {
		EObject modelElement = getModelElement(op.getModelElementId());
		String type;
		if (modelElement == null) {
			type = "ModelElement";
		} else {
			try {
				EStructuralFeature feature = op.getFeature(modelElement);
				type = feature.getEType().getName();
			} catch (UnkownFeatureException e) {
				type = "ModelElement";
			}
		}

		return unresolvedString.replace(AbstractOperationItemProvider.REFERENCE_TYPE_TAG_SEPARATOR, type);
	}

	private String resolveIds(AbstractOperationCustomLabelProvider labelProvider, String unresolvedString,
		String devider, AbstractOperation op) {
		String[] strings = unresolvedString.split(devider);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			if (i % 2 == 1) {
				ModelElementId modelElementId = ModelFactory.eINSTANCE.createModelElementId();
				modelElementId.setId(strings[i]);
				EObject modelElement = getModelElement(modelElementId);
				if (modelElement != null) {
					stringBuilder.append(labelProvider.getModelElementName(modelElement));
				} else if (modelElement == null && op instanceof CreateDeleteOperation) {
					CreateDeleteOperation createDeleteOp = (CreateDeleteOperation) op;
					for (Map.Entry<EObject, ModelElementId> entry : createDeleteOp.getEObjectToIdMap()) {
						if (entry.getValue().equals(modelElementId)) {
							stringBuilder.append(labelProvider.getModelElementName(entry.getKey()));
							break;
						}
					}
				}
			} else {
				stringBuilder.append(strings[i]);
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * Get a model element instance from the project for the given id.
	 * 
	 * @param modelElementId
	 *            the id
	 * @return the model element instance
	 */
	public EObject getModelElement(ModelElementId modelElementId) {
		return idToEObjectMapping.get(modelElementId.getAPIImpl());
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.ESDisposable#dispose()
	 */
	public void dispose() {
		defaultOperationLabelProvider.dispose();
		idToEObjectMapping = null;
	}
}
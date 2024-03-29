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
package org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec;

import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.versionspec.ESBranchVersionSpec;

/**
 * Mapping between {@link ESBranchVersionSpecImpl} and {@link BranchVersionSpec}.
 * 
 * @author emueller
 * 
 */
public class ESBranchVersionSpecImpl extends ESVersionSpecImpl<ESBranchVersionSpecImpl, BranchVersionSpec> implements
	ESBranchVersionSpec {

	/**
	 * Constructor.
	 * 
	 * @param branchVersionSpec
	 *            the delegate
	 */
	public ESBranchVersionSpecImpl(BranchVersionSpec branchVersionSpec) {
		super(branchVersionSpec);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof ESBranchVersionSpecImpl) {
			ESBranchVersionSpecImpl branchVersionSpecImpl = (ESBranchVersionSpecImpl) object;
			return getInternalAPIImpl().equals(branchVersionSpecImpl.getInternalAPIImpl());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getInternalAPIImpl().hashCode() + super.hashCode();
	}
}

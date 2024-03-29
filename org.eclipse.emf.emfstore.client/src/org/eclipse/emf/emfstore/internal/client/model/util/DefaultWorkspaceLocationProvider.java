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
package org.eclipse.emf.emfstore.internal.client.model.util;

import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.common.CommonUtil;
import org.eclipse.emf.emfstore.internal.server.DefaultServerWorkspaceLocationProvider;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.server.ESLocationProvider;

/**
 * This is the default workspace location provider. If no other location
 * provider is registered, this provider is used. The default location provider
 * offers profiles, which allows to have multiple workspaces within one root
 * folder. Allowing this isn't mandatory. It is encouraged to subclass this
 * class when implementing an own location provider, since it offers convenience
 * methods. By convention, every path should end with an folder separator char.
 * 
 * @author wesendon
 */
public class DefaultWorkspaceLocationProvider extends DefaultServerWorkspaceLocationProvider implements
	ESLocationProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.DefaultServerWorkspaceLocationProvider#getRootDirectory()
	 */
	@Override
	protected String getRootDirectory() {
		String parameter = getStartParameter(ServerConfiguration.EMFSTORE_HOME);
		if (parameter == null) {
			return addFolders(getUserHome(), ".emfstore", "client");
		} else {
			return parameter;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.server.DefaultServerWorkspaceLocationProvider#getSelectedProfile()
	 */
	@Override
	protected String getSelectedProfile() {
		String parameter = getStartParameter("-profile");
		if (parameter == null) {
			parameter = "default";
			if (CommonUtil.isTesting()) {
				parameter += "_test";
			} else if (!Configuration.VERSIONING.isReleaseVersion()) {
				if (Configuration.VERSIONING.isInternalReleaseVersion()) {
					parameter += "_internal";
				} else {
					parameter += "_dev";
				}
			}
		}
		return parameter;
	}
}
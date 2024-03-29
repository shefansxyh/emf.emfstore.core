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
package org.eclipse.emf.emfstore.internal.client.model.connectionmanager.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcSun15HttpTransportFactory;
import org.eclipse.emf.emfstore.client.exceptions.ESCertificateStoreException;
import org.eclipse.emf.emfstore.internal.client.model.Configuration;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.ConnectionManager;
import org.eclipse.emf.emfstore.internal.client.model.connectionmanager.KeyStoreManager;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;
import org.eclipse.emf.emfstore.internal.server.connection.xmlrpc.util.EObjectTypeFactory;
import org.eclipse.emf.emfstore.internal.server.exceptions.ConnectionException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.xml.sax.SAXException;

/**
 * Manager for XML RPC server calls.
 * 
 * @author wesendon
 */
public class XmlRpcClientManager {

	/**
	 * Default constructor.
	 * 
	 * @param serverInterface name of interface
	 */
	public XmlRpcClientManager(String serverInterface) {
		this.serverInterface = serverInterface;
	}

	private String serverInterface;
	private XmlRpcClient client;

	/**
	 * Initializes the connection.
	 * 
	 * @param serverInfo server info
	 * @throws ConnectionException in case of failure
	 */
	public void initConnection(ServerInfo serverInfo) throws ConnectionException {
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(createURL(serverInfo));
			config.setEnabledForExceptions(true);
			config.setEnabledForExtensions(true);
			config.setConnectionTimeout(Configuration.XML_RPC.getXMLRPCConnectionTimeout());
			config.setReplyTimeout(Configuration.XML_RPC.getXMLRPCReplyTimeout());
			config.setContentLengthOptional(true);

			client = new XmlRpcClient();
			client.setTypeFactory(new EObjectTypeFactory(client));

			XmlRpcSun15HttpTransportFactory factory = new XmlRpcSun15HttpTransportFactory(client);

			try {
				factory.setSSLSocketFactory(KeyStoreManager.getInstance().getSSLContext().getSocketFactory());
			} catch (ESCertificateStoreException e) {
				throw new ConnectionException("Couldn't load certificate", e);
			}
			client.setTransportFactory(factory);

			client.setConfig(config);

			// } catch (XmlRpcException e) {
			// throw new ConnectionException("", e);
		} catch (MalformedURLException e) {
			throw new ConnectionException("Malformed Url or Port", e);
		}
	}

	private URL createURL(ServerInfo serverInfo) throws MalformedURLException {
		checkUrl(serverInfo.getUrl());
		return new URL("https", serverInfo.getUrl(), serverInfo.getPort(), "xmlrpc");
	}

	private void checkUrl(String url) throws MalformedURLException {
		if (url != null && !url.equals("")) {
			if (!(url.contains(":") || url.contains("/"))) {
				return;
			}
		}
		throw new MalformedURLException();
	}

	/**
	 * Executes a server call with return value.
	 * 
	 * @param <T> return type
	 * @param methodName method name
	 * @param returnType return type
	 * @param parameters parameters
	 * @return returned object from server
	 * @throws ESException in case of failure
	 */
	public <T> T callWithResult(String methodName, Class<T> returnType, Object... parameters) throws ESException {
		return executeCall(methodName, returnType, parameters);
	}

	/**
	 * Executes a server call with list return value.
	 * 
	 * @param <T> return type
	 * @param methodName method name
	 * @param returnType list return type
	 * @param parameters parameters
	 * @return list return type
	 * @throws ESException in case of failure
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> callWithListResult(String methodName, Class<T> returnType, Object... parameters)
		throws ESException {
		List<T> result = new ArrayList<T>();
		Object[] callResult = executeCall(methodName, Object[].class, parameters);
		if (callResult == null) {
			return result;
		}
		for (Object obj : callResult) {
			result.add((T) obj);
		}
		return result;
	}

	/**
	 * Executes a server call without return value.
	 * 
	 * @param methodName method name
	 * @param parameters parameters
	 * @throws ESException in case of failure
	 */
	public void call(String methodName, Object... parameters) throws ESException {
		executeCall(methodName, null, parameters);
	}

	@SuppressWarnings("unchecked")
	private <T> T executeCall(String methodName, Class<T> returnType, Object[] params) throws ESException {
		if (client == null) {
			throw new ConnectionException(ConnectionManager.REMOTE);
		}
		try {
			return (T) client.execute(serverInterface + "." + methodName, params);
		} catch (XmlRpcException e) {
			if (e.getCause() instanceof ESException) {
				throw ((ESException) e.getCause());
			} else if (e.linkedException instanceof SAXException
				&& ((SAXException) e.linkedException).getException() instanceof SerializationException) {
				SerializationException serialE = (SerializationException) ((SAXException) e.linkedException)
					.getException();
				throw new org.eclipse.emf.emfstore.internal.server.exceptions.SerializationException(serialE);
			} else {
				throw new ConnectionException(ConnectionManager.REMOTE + e.getMessage(), e);
			}
		}
	}
}
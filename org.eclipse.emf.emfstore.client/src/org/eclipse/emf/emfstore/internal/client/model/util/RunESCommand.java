/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.util;

import java.util.concurrent.Callable;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * Utility class for executing {@link Callable}s within the UI Thread.
 * 
 * @author emueller
 * 
 */
public final class RunESCommand {

	private RunESCommand() {

	}

	/**
	 * The {@link Callable} to be executed may throw an exception.
	 */
	public static class WithException {

		/**
		 * Executes the given callable and returns the result.
		 * 
		 * @param callable
		 *            the callable to be execued
		 * @return the return value of the {@link Callable}
		 * @throws ESException in case an error occurs during execution of the callable
		 * 
		 * @param <T> the return type of the callable
		 */
		public static <T, E extends Exception> T runWithResult(final Class<E> exceptionType, final Callable<T> callable)
			throws E {
			EMFStoreCommandWithResultAndException<T, E> cmd = new EMFStoreCommandWithResultAndException<T, E>() {
				@Override
				protected T doRun() {
					try {
						return callable.call();
					} catch (Exception e) {
						if (exceptionType.isInstance(e)) {
							setExcpetion(exceptionType.cast(e));
						} else if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						} else {
							throw new RuntimeException(e);
						}
					}

					return null;
				}
			};

			T result = cmd.run(false);

			if (cmd.hasException()) {
				throw cmd.getExcpetion();
			}

			return result;
		}

		/**
		 * Executes the given callable and returns the result.
		 * 
		 * @param callable
		 *            the callable to be execued
		 * @throws T in case an error occurs during execution of the callable
		 */
		public static <T extends Exception> void run(final Class<T> exceptionType, final Callable<Void> callable)
			throws T {

			EMFStoreCommandWithException<T> cmd = new EMFStoreCommandWithException<T>() {
				@Override
				protected void doRun() {
					try {
						callable.call();
					} catch (Exception e) {
						if (exceptionType.isInstance(e)) {
							setException(exceptionType.cast(e));
						} else if (e instanceof RuntimeException) {
							throw (RuntimeException) e;
						} else {
							throw new RuntimeException(e);
						}
					}
				}
			};

			cmd.run(false);

			if (cmd.hasException()) {
				throw cmd.getException();
			}
		}
	}

	/**
	 * Executes the given {@link Callable} and returns the result.
	 * 
	 * @param callable
	 *            the {@link Callable} to be executed
	 */
	public static void run(final Callable<Void> callable) {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				try {
					callable.call();
				} catch (Exception e) {
					// ignore
				}
			}
		}.run(false);
	}

	/**
	 * Executes the given callable and returns the result.
	 * 
	 * @param callable
	 *            the callable to be execued
	 * @return the return value of the {@link Callable}
	 * 
	 * @param <T> the return type of the callable
	 */
	public static <T> T runWithResult(final Callable<T> callable) {
		return new EMFStoreCommandWithResult<T>() {
			@Override
			protected T doRun() {
				try {
					return callable.call();
				} catch (Exception e) {
					// ignore
				}
				return null;
			}
		}.run(false);
	}

}
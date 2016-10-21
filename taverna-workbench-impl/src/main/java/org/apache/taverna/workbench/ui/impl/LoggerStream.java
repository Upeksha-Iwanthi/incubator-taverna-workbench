/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.taverna.workbench.ui.impl;

import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * A subclass of PrintStream that redirects its output to a log4j Logger.
 *
 * <p>This class is used to map PrintStream/PrintWriter oriented logging onto
 *    the log4j Categories. Examples include capturing System.out/System.err
 *
 * @version <tt>$Revision: 1.1.1.1 $</tt>
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>.
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 */
//FIXME Replace this class entirely
class LoggerStream extends PrintStream {
	/**
	 * Default flag to enable/disable tracing println calls. from the system
	 * property <tt>net.sf.taverna.t2.workbench.ui.impl.LoggerStream.trace</tt>
	 * or if not set defaults to <tt>false</tt>.
	 */
   public static final boolean TRACE =
		   getBoolean(LoggerStream.class.getName() + ".trace", false);

	/**
	 * Helper to get boolean value from system property or use default if not
	 * set.
	 */
	private static boolean getBoolean(String name, boolean defaultValue) {
		String value = System.getProperty(name, null);
		if (value == null)
			return defaultValue;
		return new Boolean(value).booleanValue();
	}

	private Logger logger;
	private Level level;
	private boolean issuedWarning;

	/**
	 * Redirect logging to the indicated logger using Level.INFO
	 */
	public LoggerStream(Logger logger) {
		this(logger, Level.INFO, System.out);
	}

	/**
	 * Redirect logging to the indicated logger using the given level. The ps is
	 * simply passed to super but is not used.
	 */
	public LoggerStream(Logger logger, Level level, PrintStream ps) {
		super(ps);
		this.logger = logger;
		this.level = level;
	}

	@Override
	public void println(String msg) {
		if (msg == null)
			msg = "null";
		byte[] bytes = msg.getBytes();
		write(bytes, 0, bytes.length);
	}

	@Override
	public void println(Object msg) {
		if (msg == null)
			msg = "null";
		byte[] bytes = msg.toString().getBytes();
		write(bytes, 0, bytes.length);
	}

	public void write(byte b) {
		byte[] bytes = { b };
		write(bytes, 0, 1);
	}

	private ThreadLocal<Boolean> recursiveCheck = new ThreadLocal<>();

	@Override
	public void write(byte[] b, int off, int len) {
		Boolean recursed = recursiveCheck.get();
		if (recursed != null && recursed) {
			/*
			 * There is a configuration error that is causing looping. Most
			 * likely there are two console appenders so just return to prevent
			 * spinning.
			 */
			if (issuedWarning == false) {
				String msg = "ERROR: invalid log settings detected, console capturing is looping";
				// out.write(msg.getBytes());
				new Exception(msg).printStackTrace((PrintStream) out);
				issuedWarning = true;
			}
			try {
				out.write(b, off, len);
			} catch (IOException e) {
			}
			return;
		}

		// Remove the end of line chars
		while (len > 0 && (b[len - 1] == '\n' || b[len - 1] == '\r')
				&& len > off)
			len--;

		/*
		 * HACK, something is logging exceptions line by line (including
		 * blanks), but I can't seem to find it, so for now just ignore empty
		 * lines... they aren't very useful.
		 */
		if (len != 0) {
			String msg = new String(b, off, len);
			recursiveCheck.set(true);
			if (TRACE)
				logger.log(level, msg, new Throwable());
			else
				logger.log(level, msg);
			recursiveCheck.set(false);
		}
	}
}
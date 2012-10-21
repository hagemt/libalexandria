/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
package lib.alexandria.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import lib.alexandria.LabelledEntity;

import static lib.alexandria.ModelConstants.DEFAULT_LOG_FORMAT;

/**
 * A facility for logging messages via lib.alexandria
 * TODO don't ignore free advice; subclassing notes specify:
 * Note that a LogManager class may provide its own implementation of named Loggers for any point in the namespace.
 * Therefore, any subclasses of Logger (unless they are implemented in conjunction with a new LogManager class)
 * should take care to obtain a Logger instance from the LogManager class and should delegate operations such as
 * "isLoggable" and "log(LogRecord)" to that instance. Note that in order to intercept all logging output,
 * subclasses need only override the log(LogRecord) method.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @see lib.alexandria.Log
 */
public class MessageLogger extends Logger implements Log {
	private static final ConsoleHandler DEFAULT_CONSOLE;
	private static final Formatter XML_FORMAT;
	
	static {
		DEFAULT_CONSOLE = new ConsoleHandler();
		DEFAULT_CONSOLE.setFormatter(DEFAULT_LOG_FORMAT);
		XML_FORMAT = new XMLFormatter();
	}
	
	private Level initial_level;
	
	public MessageLogger(String name, Handler... handlers) {
		this(name, Level.ALL, handlers);
	}

	public MessageLogger(String name, Level level, Handler... handlers) {
		super(name, null);
		boolean console_missing = true;
		for (Handler h : handlers) {
			super.addHandler(h);
			if (h instanceof ConsoleHandler) {
				console_missing = false;
			}
		}
		if (console_missing) {
			super.addHandler(DEFAULT_CONSOLE);
		}
		super.setLevel(level);
		this.initial_level = level;
	}

	@Override
	public void i(LabelledEntity le, String msg) {
		super.info("[" + le.getLabel() + "] (" + msg + ")");
	}

	@Override
	public void w(LabelledEntity le, String msg) {
		super.warning("[" + le.getLabel() + "] (" + msg + ")");
	}

	@Override
	public boolean toConsole(Level level) {
		boolean error = false;
		try {
			for (Handler h : getHandlers()) {
				if (h instanceof ConsoleHandler) {
					h.setLevel(level);
				}
			}
		} catch (Exception e) {
			error = true;
			e.printStackTrace();
		}
		return error;
	}

	@Override
	public boolean toFilename(String path) {
		return toFilename(path, DEFAULT_LOG_FORMAT);
	}

	@Override
	public boolean toFilename(String path, FormatType type) {
		Formatter f = (type == FormatType.XML) ? XML_FORMAT : DEFAULT_LOG_FORMAT;
		return toFilename(path, f);
	}

	@Override
	public boolean toFilename(String path, Formatter formatter) {
		boolean error = false;
		try {
			FileHandler fh = new FileHandler(path);
			fh.setFormatter(formatter);
			fh.setLevel(initial_level);
			super.addHandler(fh);
		} catch (Exception e) {
			error = true;
			e.printStackTrace();
		}
		return error;
	}
}

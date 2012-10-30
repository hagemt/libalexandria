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

import java.util.Date;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import lib.alexandria.naming.Labelled;

import static lib.alexandria.ModelConstants.DEFAULT_LOG_FORMAT;
import static lib.alexandria.ModelConstants.DEFAULT_LOG_LEVEL;

/**
 * A facility for logging messages via lib.alexandria
 * TODO don't ignore free advice; subclassing notes specify:
 * Note that a LogManager class may provide its own implementation of named Loggers for any point in the namespace.
 * Therefore, any subclasses of Logger (unless they are implemented in conjunction with a new LogManager class)
 * should take care to obtain a Logger instance from the LogManager class and should delegate operations such as
 * "isLoggable" and "log(LogRecord)" to that instance. Note that in order to intercept all logging output,
 * subclasses need only override the log(LogRecord) method.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @see lib.alexandria.logging.Log
 */
public class MessageLogger extends Logger implements Log {
	private static final ConsoleHandler DEFAULT_CONSOLE;
	private static final Formatter SIMPLE_FORMAT, XML_FORMAT;
	
	static {
		DEFAULT_CONSOLE = new ConsoleHandler();
		DEFAULT_CONSOLE.setFormatter(DEFAULT_LOG_FORMAT);
		DEFAULT_CONSOLE.setLevel(DEFAULT_LOG_LEVEL);
		SIMPLE_FORMAT = new SimpleFormatter();
		XML_FORMAT = new XMLFormatter();
	}
	
	private Level initial_level;
	
	public MessageLogger(String name, Handler... handlers) {
		this(name, DEFAULT_LOG_LEVEL, handlers);
	}

	public MessageLogger(String name, Level level, Handler... handlers) {
		super(name, null);
		super.setLevel(level);
		this.initial_level = level;
		/* Ensure there is at least one handler (console) */
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
	}
	
	private String str(Labelled l, String msg) {
		return "[" + getName() + ":" + l.getLabel() + "] (" + msg + ")";
	}

	public void vv(Labelled l, String msg) {
		super.finest(str(l, msg));
	}

	@Override
	public void v(Labelled l, String msg) {
		super.finer(str(l, msg));
	}

	@Override
	public void d(Labelled l, String msg) {
		super.fine(str(l, msg));
	}

	@Override
	public void i(Labelled l, String msg) {
		super.config(str(l, msg));
	}

	@Override
	public void w(Labelled l, String msg) {
		super.info(str(l, msg));
	}

	@Override
	public void e(Labelled l, String msg) {
		super.warning(str(l, msg));
	}

	@Override
	public void f(Labelled l, String msg) {
		super.severe(str(l, msg));
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
		Formatter f;
		switch (type) {
		case SIMPLE: f = SIMPLE_FORMAT; break;
		case XML: f = XML_FORMAT; break;
		default: f = DEFAULT_LOG_FORMAT; break;
		}
		return toFilename(path, f);
	}

	@Override
	public boolean toFilename(String path, Formatter formatter) {
		boolean error = false;
		try {
			FileHandler fh = new FileHandler(path, true);
			fh.setFormatter(formatter);
			addHandler(fh);
		} catch (Exception e) {
			error = true;
			e.printStackTrace();
		}
		return error;
	}

	@Override
	public void addHandler(Handler handler) {
		// Store the level so that we can restore it later
		Level level = handler.getLevel();
		handler.setLevel(initial_level);
		// Publish a mandatory timestamp
		LogRecord timestamp = new LogRecord(initial_level, new Date().toString());
		timestamp.setLoggerName(getName());
		handler.publish(timestamp);
		// Reset the handler's level and add it
		handler.setLevel(level);
		super.addHandler(handler);
	}
}

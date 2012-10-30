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

import java.util.logging.Formatter;
import java.util.logging.Level;

import lib.alexandria.naming.Labelled;

/**
 * Designed as a basic log interface for lib.alexandria
 * In general:
 *  v is for verbose mode (Level.FINER)
 *  d is for debug messages (Level.FINE)
 *  i is for minor information (Level.CONFIG)
 *  w is for important information (Level.INFO)
 *  e is for recoverable errors (Level.WARNING)
 *  f is for fatal errors (Level.SEVERE)
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public interface Log {
	void v(Labelled l, String msg);
	void d(Labelled l, String msg);
	void i(Labelled l, String msg);
	void w(Labelled l, String msg);
	void e(Labelled l, String msg);
	void f(Labelled l, String msg);
	
	/**
	 * Try to set all console logging to the desired level.
	 * @param level the level at which to set console logging
	 * @return if everything completed successfully, false otherwise
	 */
	boolean toConsole(Level level);

	boolean toFilename(String path);
	boolean toFilename(String path, FormatType type);
	boolean toFilename(String path, Formatter formatter);
}

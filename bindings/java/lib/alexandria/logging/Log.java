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

import lib.alexandria.LabelledEntity;

/**
 * Designed as a basic log interface for lib.alexandria
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public interface Log {
	void d(LabelledEntity le, String msg);
	void i(LabelledEntity le, String msg);
	void w(LabelledEntity le, String msg);
	boolean toConsole(Level level);
	boolean toFilename(String path);
	boolean toFilename(String path, FormatType type);
	boolean toFilename(String path, Formatter formatter);
}

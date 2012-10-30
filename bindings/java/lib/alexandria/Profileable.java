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
package lib.alexandria;

import java.io.Closeable;

import lib.alexandria.naming.Labelled;

/**
 * Allows certain types to provide mechanisms to produce performance data
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public interface Profileable extends Closeable, Labelled {
	/**
	 * Runs code that tests computationally-intensive functions
	 * Typically, this is done with native code through the JNI
	 */
	void benchmark();

	/**
	 * Provides extra type information for the profiler. This is beyond an object's traditional "type."
	 * Enumerated types are commonly used within classes to specify a "flavor" for an object.
	 * This way, type (or "flavor") information provided by a class is retrievable in a portable way.
	 * @param category Specifies the type of "flavor" information requested
	 * @return a "flavor" by which a profiler can identify this object
	 * @throws ClassCastException if a matching "flavor" cannot be found
	 * @see lib.alexandria.ModelConstants.ModelType
	 */
	<E extends Enum<E>> E getType(Class<E> category) throws ClassCastException;
}

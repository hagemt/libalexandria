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

import java.util.concurrent.TimeUnit;
import java.util.logging.Formatter;
import java.util.logging.Level;

import lib.alexandria.logging.LinearFormatter;

/**
 * Standard or shared constants for Java bindings go here.
 * Consider: http://docs.oracle.com/javase/1.5.0/docs/guide/language/static-import.html
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public final class ModelConstants {
	/**
	 * An HTM constant specifying the minimum cortex dimension.
	 * Its standard value is: 32 (results in 1KB used)
	 */
	public static final int MIN_HTM_DIMENSION;

	/**
	 * An HTM constant specifying the default cortex dimension.
	 * Its standard value is: 64 (results in 4KB used)
	 */
	public static final int DEFAULT_HTM_DIMENSION;

	/**
	 * A testing constant specifying the time to wait for jobs.
	 * Its standard value is: 10000 (milliseconds)
	 * @see DEFAULT_TIME_UNIT
	 */
	public static final int DEFAULT_RUN_TIME;

	/**
	 * A testing constant specifying the time to wait for joins.
	 * Its standard value is: 1000 (milliseconds)
	 * @see DEFAULT_TIME_UNIT
	 */
	public static final int DEFAULT_JOIN_TIME;

	/**
	 * A testing constant specifying the time unit for various other testing constants.
	 * Its standard value is: TimeUnit.MILLISECONDS (from java.util.concurrent)
	 * @see java.util.concurrent.TimeUnit
	 * @see DEFAULT_RUN_TIME
	 * @see DEFAULT_JOIN_TIME
	 */
	public static final TimeUnit DEFAULT_TIME_UNIT;

	/**
	 * A global constant specifying the size of buffers in bytes.
	 * Its standard value is: 1024 (simply 1KB)
	 */
	public static final int DEFAULT_BUFFER_SIZE;

	/**
	 * A global constant specifying the default number of label components.
	 * Its standard value is: 3 (provides almost 16 bits of randomness)
	 */
	public static final int DEFAULT_LABEL_LENGTH;

	/**
	 * A global constant specifying the default seed.
	 * Its standard value is defined by System.nanoTime()
	 */
	public static final long DEFAULT_SEED;

	/**
	 * A global constant specifying the library's prefix.
	 * Its standard value is: "laJ" (as per spec)
	 */
	public static final String LA_PREFIX;

	/**
	 * A global constant specifying the library's fully qualified name.
	 * Its standard value is: "lib.alexandria" (matches package name)
	 */
	public static final String LA_FQN;
	
	/**
	 * A global constant specifying an array of label components.
	 * Its standard values are the NATO phonetic "alphabet."
	 */
	public static final String[] LABEL_POOL;
	
	/**
	 * A logging constant specifying the default log format.
	 * Its standard value is a simple one-line plain-text format.
	 * @see lib.alexandria.logging.LinearFormatter
	 */
	public static final Formatter DEFAULT_LOG_FORMAT;
	
	/**
	 * A logging constant specifying the default log level.
	 * Its standard value indicates information messages.
	 */
	public static final Level DEFAULT_LOG_LEVEL;
	
	/**
	 * Give constants the default values.
	 */
	static {
		/* HTM constants */
		MIN_HTM_DIMENSION = 32;
		DEFAULT_HTM_DIMENSION = 64;
		/* Testing constants */
		DEFAULT_RUN_TIME = 10000;
		DEFAULT_JOIN_TIME = 1000;
		DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
		/* Global constants*/
		DEFAULT_BUFFER_SIZE = 1024;
		DEFAULT_LABEL_LENGTH = 3;
		DEFAULT_SEED = System.nanoTime();
		LA_PREFIX = "laJ";
		LA_FQN = "lib.alexandria";
		DEFAULT_LOG_FORMAT = new LinearFormatter(LA_PREFIX);
		DEFAULT_LOG_LEVEL = Level.ALL;
		LABEL_POOL = new String[] {
				"Alpha",
				"Bravo",
				"Charlie",
				"Delta",
				"Echo",
				"Foxtrot",
				"Golf",
				"Hotel",
				"India",
				"Juliet",
				"Kilo",
				"Lima",
				"Mike",
				"November",
				"Oscar",
				"Papa",
				"Quebec",
				"Romeo",
				"Sierra",
				"Tango",
				"Uniform",
				"Victor",
				"Whiskey",
				"Xray",
				"Yankee",
				"Zulu",
				"1One",
				"2Two",
				"3Three",
				"4Four",
				"5Five",
				"6Six",
				"7Seven",
				"8Eight",
				"9Nine",
				"0Zero"
		};
	}
	
	/**
	 * Specifies the categories of models we know about.
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @since libalexandria v0.1
	 */
	public static enum ModelType {
		SUPERVISED, UNSUPERVISED, REINFORCEMENT;
	}

	/**
	 * TODO remove?
	 * Perhaps the best or the worst idea I have ever had.
	 * Allows models to implement model constants for a type.
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @param <E> an enumerated type
	 */
	/*
	public static interface ModelFor<E extends Enum<E>> {
		E getType();
		Class<E> getTypeClass();
	}
	*/
}

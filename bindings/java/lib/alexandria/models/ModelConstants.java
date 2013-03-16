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
package lib.alexandria.models;

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
public class ModelConstants {

	/**
	 * Protect against direct instances.
	 */
	protected ModelConstants() { }

	/**
	 * A global constant specifying the priority for asynchronous tasks.
	 * Its standard value is: Thread.NORM_PRIORITY
	 */
	public static final int DEFAULT_TASK_PRIORITY;
	
	/**
	 * An HTM constant specifying the minimum cortex dimension.
	 * Its standard value is: 32 (results in 1KB used)
	 */
	public static final int DEFAULT_HTM_DIMENSION_MIN;

	/**
	 * An HTM constant specifying the default cortex dimension.
	 * Its standard value is: 64 (results in 4KB used)
	 */
	public static final int DEFAULT_HTM_DIMENSION;

	/**
	 * A testing constant specifying the time to wait for jobs.
	 * Its standard value is: 10000 (DEFAULT_TIME_UNITs)
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_UNIT
	 */
	public static final int DEFAULT_TIME_RUN;

	/**
	 * A testing constant specifying the time to wait for joins.
	 * Its standard value is: 1000 (DEFAULT_TIME_UNITs)
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_UNIT
	 */
	public static final int DEFAULT_TIME_JOIN;
	
	/**
	 * A testing constant specifying the time to train.
	 * Its standard value is: DEFAULT_TIME_RUN (DEFAULT_TIME_UNITs)
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_RUN
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_UNIT
	 */
	public static final int DEFAULT_TIME_TRAIN;

	/**
	 * A testing constant specifying the time to test.
	 * Its standard value is: DEFAULT_TIME_RUN + DEFAULT_TIME_JOIN (DEFAULT_TIME_UNITs)
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_RUN
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_JOIN
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_UNIT
	 */
	public static final int DEFAULT_TIME_TEST;

	/**
	 * A testing constant specifying the time unit for various other testing constants.
	 * Its standard value is: TimeUnit.MILLISECONDS (from java.util.concurrent)
	 * @see java.util.concurrent.TimeUnit
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_RUN
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_JOIN
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_TRAIN
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_TIME_TEST
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
	public static final String LAJ_PREFIX;

	/**
	 * A global constant specifying the library's fully qualified name.
	 * Its standard value is: "lib.alexandria" (matches package name)
	 */
	public static final String LAJ_FQN;
	
	/**
	 * A global constant specifying an array of label components.
	 * Its standard values are the NATO phonetic "alphabet."
	 */
	public static final String[] LAJ_LABEL_POOL;
	
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
	 * Specifies the categories of models we know about.
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @since libalexandria v0.1
	 */
	public static enum ModelType {
		SUPERVISED, UNSUPERVISED, REINFORCEMENT;
	}
	
	/**
	 * Give constants the default values.
	 */
	static {
		/* Global constants*/
		LAJ_PREFIX = "laJ";
		LAJ_FQN = "lib.alexandria";
		DEFAULT_BUFFER_SIZE = 1024;
		DEFAULT_LABEL_LENGTH = 3;
		DEFAULT_SEED = System.nanoTime();
		DEFAULT_TASK_PRIORITY = Thread.NORM_PRIORITY;
		/* Logging constants */
		DEFAULT_LOG_FORMAT = new LinearFormatter(LAJ_PREFIX);
		DEFAULT_LOG_LEVEL = Level.ALL;
		/* HTM constants */
		DEFAULT_HTM_DIMENSION_MIN = 32;
		DEFAULT_HTM_DIMENSION = 64;
		/* Testing constants */
		DEFAULT_TIME_RUN = 10000;
		DEFAULT_TIME_JOIN = 1000;
		DEFAULT_TIME_TRAIN = DEFAULT_TIME_RUN;
		DEFAULT_TIME_TEST = DEFAULT_TIME_RUN + DEFAULT_TIME_JOIN;
		DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
		/* Internals */
		LAJ_LABEL_POOL = new String[] {
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
}

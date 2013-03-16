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

// TODO consider using java.security.SecureRandom
import java.security.SecureRandom;
import java.util.Random;

import static lib.alexandria.models.ModelConstants.DEFAULT_LABEL_LENGTH;
import static lib.alexandria.models.ModelConstants.DEFAULT_LOG_FORMAT;
import static lib.alexandria.models.ModelConstants.DEFAULT_LOG_LEVEL;
import static lib.alexandria.models.ModelConstants.DEFAULT_SEED;
import static lib.alexandria.models.ModelConstants.LAJ_FQN;
import static lib.alexandria.models.ModelConstants.LAJ_LABEL_POOL;
import static lib.alexandria.models.ModelConstants.LAJ_PREFIX;

import lib.alexandria.logging.FormatType;
import lib.alexandria.logging.Log;
import lib.alexandria.logging.MessageLogger;

import lib.alexandria.naming.LabelledEntity;
import lib.alexandria.pipeline.Spool;

/**
 * Provides static methods to generate some standard entities, like labels.
 * Other utilities are also provided, but only public members are in the API.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Generate {
	/**
	 * The default source of randomness.
	 * @see java.util.Random
	 */
	protected static final Random source;

	/**
	 * The default log manager.
	 * @see lib.alexandria.logging.Log
	 */
	public static final Log LOG;
	
	/**
	 * A spool is a source of threads.
	 * @see lib.alexandria.pipeline.Spool
	 */
	public static final Spool SPOOL;

	static {
		source = new SecureRandom();
		source.setSeed(DEFAULT_SEED);
		LOG = new MessageLogger(LAJ_FQN);
		LOG.toConsole(DEFAULT_LOG_LEVEL);
		LOG.toFilename(LAJ_FQN + ".log", DEFAULT_LOG_FORMAT);
		LOG.toFilename(LAJ_FQN + ".txt", FormatType.SIMPLE);
		LOG.toFilename(LAJ_FQN + ".xml", FormatType.XML);
		SPOOL = new Spool(LAJ_PREFIX);
		Thread.currentThread().setUncaughtExceptionHandler(SPOOL);
	}

	private Generate() { }

	/* Templators */
	
	/**
	 * A templator built mainly for enumerations.
	 * It allows a class to specify that it categorizes
	 * a certain type of entites, which happen to have labels.
	 * This is used to provide a shared interface for many functions.
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @param <L> a type with an intrinsic label
	 * @see lib.alexandria.functions
	 */
	public static interface A<L extends LabelledEntity> {
		/**
		 * Checks if any entity with the given name is flavored as desired.
		 * @param label the common name of a flavored entity
		 * @return true if one exists, false otherwise
		 */
		boolean knows(String label);
		/**
		 * Tries to retrieve a named entity of this flavor.
		 * @param label the common name of a flavored entity
		 * @return an entity, flavored as desired
		 */
		L knownAs(String label);
		/**
		 * May retrieve any entity with this flavor.
		 * @return the default entity for this flavor
		 */
		L getDefault();
		/**
		 * Remember to provide a friendly human name!
		 * @return a textual description of the flavor
		 */
		String toString();
	}

	/**
	 * A templator for implementing "some flavor" in a class.
	 * FIXME this could be considered an abused of inheritance
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @param <Flavor> some enumeration classifier
	 */
	public static interface Some<Flavor extends Enum<Flavor>> {
		/**
		 * Returns the "flavor" associated with this object.
		 * @return whichever constant describes our "flavor"
		 */
		Flavor getFlavor();
	}
	
	/* Generators */

	public static int randomInteger(int top) {
		return source.nextInt(top);
	}
	
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
	
	public static char[] chars(int number) {
		char[] characters = new char[number];
		for (int i = 0; i < number; ++i) {
			characters[i] = CHARS.charAt(randomInteger(CHARS.length()));
		}
		return characters;
	}
	
	/**
	 * Generate a random label of the default length.
	 * @return text that can be used to identify an entity easily
	 * @see lib.alexandria.models.ModelConstants#DEFAULT_LABEL_LENGTH
	 */
	public static String randomString() {
		return randomString(DEFAULT_LABEL_LENGTH);
	}
	
	public static String randomString(int words) {
		StringBuilder sb = new StringBuilder();
		for (int max = LAJ_LABEL_POOL.length - 1; words > 0; --words) {
			sb.append(LAJ_LABEL_POOL[source.nextInt(max)]);
		}
		return sb.toString();
	}
	
	public static <E extends Enum<E>> E randomType(Class<E> type) {
		E[] options = type.getEnumConstants();
		return options[source.nextInt(options.length - 1)];
	}

	public static synchronized void reseed(long seed) {
		source.setSeed(seed);
	}
}

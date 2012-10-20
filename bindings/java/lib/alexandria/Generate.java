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

import java.util.Random;

import static lib.alexandria.ModelConstants.LABEL_POOL;
import static lib.alexandria.ModelConstants.DEFAULT_LABEL_LENGTH;

/**
 * Provides static methods to generate some standard entities, like labels.
 * Other utilities 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Generate {
	private static final Random source;
	
	static {
		source = new Random();
	}
	
	/* Templators */
	
	/**
	 * A templator built mainly for enumerations.
	 * It allows a class to specify that it categorizes
	 * a certain type of entites, which happen to have labels.
	 * This is used to provide a shared interface for many functions.
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 * @param <L> a type with an intrinsic label
	 * @see lib.alexandria.functional
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

	/**
	 * Generate a random label of the default length.
	 * @return a textual label with
	 * @see lib.alexandria.ModelConstants.DEFAULT_LABEL_LENGTH
	 */
	public static String randomLabel() {
		return randomLabel(DEFAULT_LABEL_LENGTH);
	}
	
	public static <E> E randomElement(E... elements) {
		return elements[source.nextInt(elements.length - 1)];
	}
	
	public static String randomLabel(int words) {
		StringBuilder sb = new StringBuilder();
		for (int max = LABEL_POOL.length - 1; words > 0; --words) {
			sb.append(LABEL_POOL[source.nextInt(max)]);
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

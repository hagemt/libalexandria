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

/**
 * Provides static methods to generate some standard entities, like labels
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class Generate {
	private static Random source;
	private static final String[] pool;
	
	static {
		source = new Random();
		pool = new String[] {
				"Alfa",
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
				"Zulu"
				};
	}
	
	public static interface A<L extends LabelledEntity> {
		boolean knows(String label);
		L knownAs(String label);
		L getDefault();
		String toString();
	}

	public static String randomLabel() {
		return randomLabel(3);
	}
	
	public static String randomLabel(int words) {
		StringBuilder sb = new StringBuilder();
		for (int max = pool.length - 1; words > 0; --words) {
			sb.append(pool[source.nextInt(max)]);
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

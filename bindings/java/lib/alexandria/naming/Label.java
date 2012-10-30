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
package lib.alexandria.naming;

import static lib.alexandria.ModelConstants.LAJ_LABEL_POOL;
import static lib.alexandria.ModelConstants.DEFAULT_LABEL_LENGTH;

import java.util.HashMap;
import java.util.Map;

import lib.alexandria.Generate;

/**
 * A sample, standalone version of a LabelledEntity.
 * Can be used to integrate other classes into libalexandria's label scheme.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @see lib.alexandria.naming.LabelledEntity
 */
public class Label extends LabelledEntity implements CharSequence {
	protected final CharSequence chars;
	
	private final static Map<Character, String> quick;
	static {
		quick = new HashMap<Character, String>();
		for (String s : LAJ_LABEL_POOL) {
			quick.put(s.charAt(0), s);
		}
	}
	
	private static String decode(char[] glyphs) {
		StringBuilder buffer = new StringBuilder(glyphs.length * 5);
		for (char glyph : glyphs) {
			String s = quick.get(glyph);
			if (s != null) {
				buffer.append(s);
			}
		}
		return buffer.toString();
	}
	
	public Label() {
		this(DEFAULT_LABEL_LENGTH);
	}
	
	public Label(int length) {
		this(Generate.chars(length));
	}

	public Label(char... glyphs) {
		super(decode(glyphs));
		this.chars = new StringBuilder(new String(glyphs));
	}

	@Override
	public char charAt(int index) {
		return chars.charAt(index);
	}

	@Override
	public int length() {
		return chars.length();
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return chars.subSequence(start, end);
	}
	
	public String toString(boolean full) {
		return (full) ? super.toString() : chars.toString();
	}
	
	@Override
	public String toString() {
		return toString(true);
	}
}

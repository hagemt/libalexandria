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

import static lib.alexandria.ModelConstants.LABEL_POOL;
import static lib.alexandria.ModelConstants.DEFAULT_LABEL_LENGTH;

import java.util.HashMap;
import java.util.Map;

/**
 * Note: this entire class is a grok. FIXME soon!
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Label extends LabelledEntity implements CharSequence {
	protected final CharSequence chars, buffer;
	
	private final static Map<Character, String> quick;
	private final static Character[] codes;
	static {
		quick = new HashMap<Character, String>();
		for (String s : LABEL_POOL) {
			quick.put(s.charAt(0), s);
		}
		codes = quick.keySet().toArray(new Character[quick.size()]);
	}

	public static char[] code(int length) {
		char[] code = new char[length];
		for (int i = 0; i < length; ++i) {
			code[i] = Generate.randomElement(codes);
		}
		return code;
	}
	
	public Label() {
		this(DEFAULT_LABEL_LENGTH);
	}
	
	public Label(int length) {
		this(code(length));
	}

	public Label(String s) {
		this(s.toUpperCase().toCharArray());
	}
	
	private Label(char... entries) {
		this.chars = new StringBuilder(new String(entries));
		StringBuilder buffer = new StringBuilder();
		for (char c : entries) {
			buffer.append(quick.get(c));
		}
		this.buffer = buffer;
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
		return (full) ? buffer.toString() : chars.toString();
	}
	
	@Override
	public String toString() {
		return toString(true);
	}
}

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
package libalexandria.functional.params;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/*
 * TODO review if this is necessary...
 */

public class MutableParameter<N extends Number> extends SimpleEntry<String, N> implements Parameter<N> {
	/**
	 * @since libalexandria v0.1
	 */
	private static final long serialVersionUID = -6340640910422166874L;

	protected MutableParameter(String key, N value) {
		super(key, value);
	}

	public MutableParameter(MutableParameter<N> parameter) {
		super(parameter);
		/* TODO does this clone as desired? */
	}

	@Override
	public Entry<String, N> entry() {
		return this;
	}
}

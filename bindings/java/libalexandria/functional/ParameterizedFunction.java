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
package libalexandria.functional;

import libalexandria.functional.params.ParameterMap;

public abstract class ParameterizedFunction<N extends Number> extends ParameterMap<N> implements Function<N> {
	public ParameterizedFunction(String label) {
		super(label);
	}
	
	protected ParameterizedFunction(ParameterMap<N> m) {
		super(m);
	}
	
	public static void lookup(String label) {
		// TODO make Kernel get() more generic here?
	}
	
	/**
	 * This will be great for introspection
	 * @return
	 */
	public abstract Enum<? extends Enum<?>> getType();
}

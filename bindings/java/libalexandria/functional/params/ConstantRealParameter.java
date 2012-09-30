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

import java.util.Map.Entry;

/*
 * TODO review if this is necessary...
 */

public class ConstantRealParameter implements Parameter<Double> {
	private final MutableParameter<Double> parameter;
	
	public ConstantRealParameter(String name, Double value) {
		parameter = new MutableParameter<Double>(name, value);
	}

	@Override
	public String getKey() {
		return parameter.getKey();
	}

	@Override
	public Double getValue() {
		return parameter.getValue();
	}

	@Override
	public Entry<String, Double> entry() {
		return new MutableParameter<Double>(parameter);
	}
}

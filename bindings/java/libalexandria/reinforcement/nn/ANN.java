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
package libalexandria.reinforcement.nn;

import libalexandria.LearningModel;
import libalexandria.ModelConstants;

public abstract class ANN extends LearningModel {
	protected ANN(ModelConstants.ModelType type) {
		this(type, type.name());
	}

	protected ANN(ModelConstants.ModelType type, String label) {
		super(type, label);
	}
}
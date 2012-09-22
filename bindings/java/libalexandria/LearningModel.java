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
package libalexandria;

public abstract class LearningModel {
	protected final ModelType type;
	protected String label;

	protected LearningModel(ModelType type) {
		this(type, type.name());
	}

	protected LearningModel(ModelType type, String label) {
		this.type = type;
		this.label = label;
	}

	public ModelType getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}

	public String setLabel(String label) {
		String oldLabel = this.label;
		if (label == null) {
			this.label = "";
		} else {
			this.label = label;
		}
		return oldLabel;
	}
}

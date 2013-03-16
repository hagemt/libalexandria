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
package lib.alexandria.models;

import static lib.alexandria.models.ModelConstants.ModelType;
import lib.alexandria.Profileable;
import lib.alexandria.naming.LabelledEntity;
import lib.alexandria.pipeline.Step;

/**
 * A general template from which all learning models inherit;
 * properties common to any "learning model" are encapsulated here.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public abstract class LearningModel extends LabelledEntity implements Profileable, Step {
	/**
	 * Represents the mechanism of this model's operation
	 */
	private final ModelType type;

	/**
	 * Represents whether or not this model can operate online
	 */
	private final boolean online;
	
	protected LearningModel(String label, ModelType type) {
		this(label, type, false);
	}

	protected LearningModel(String label, ModelType type, boolean online) {
		super(label);
		this.type = type;
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	@Override
	public <E extends Enum<E>> E getType(Class<E> category) throws ClassCastException {
		return category.cast(type);
	}
}

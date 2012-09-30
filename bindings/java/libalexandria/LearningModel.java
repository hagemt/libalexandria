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

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public abstract class LearningModel extends LabelledEntity implements Profileable {
	public static enum ModelType {
		SUPERVISED, UNSUPERVISED, REINFORCEMENT;
	}
	
	/**
	 * Represents the mechanism of this model's operation
	 */
	protected final ModelType type;

	/**
	 * Represents whether or not this model can operate online
	 */
	protected final boolean online;

	protected LearningModel(ModelType type, String label) {
		this(type, label, false);
	}

	protected LearningModel(ModelType type, String label, boolean online) {
		super(label);
		this.type = type;
		this.online = online;
	}

	public ModelType getType() {
		return type;
	}

	public boolean isOnline() {
		return online;
	}
}
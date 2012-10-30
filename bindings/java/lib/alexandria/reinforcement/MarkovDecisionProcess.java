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
package lib.alexandria.reinforcement;

import lib.alexandria.LearningModel;
import lib.alexandria.ModelConstants;
import lib.alexandria.annotation.TODO;

@TODO(priority = TODO.Priority.NORMAL)
public abstract class MarkovDecisionProcess extends LearningModel {
	protected MarkovDecisionProcess(String label) {
		super(label, ModelConstants.ModelType.REINFORCEMENT, true);
	}
}

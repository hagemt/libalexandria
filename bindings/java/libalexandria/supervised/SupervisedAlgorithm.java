package libalexandria.supervised;

import libalexandria.LearningModel;
import libalexandria.ModelType;

public abstract class SupervisedAlgorithm extends LearningModel {
	protected SupervisedAlgorithm() {
		super(ModelType.SUPERVISED);
	}

	
}

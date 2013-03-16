package lib.alexandria.planning;

import java.io.IOException;

import lib.alexandria.models.LearningModel;
import lib.alexandria.models.ModelConstants.ModelType;

public abstract class Trainer extends LearningModel {

	protected Trainer(String label, ModelType type) {
		super(label, type);
	}

	@Override
	public void benchmark() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit() {
		// TODO Auto-generated method stub

	}

}

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

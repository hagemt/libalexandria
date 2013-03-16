package lib.alexandria.planning;

import lib.alexandria.naming.LabelledEntity;

public class Objective extends LabelledEntity {
	private String expr;
	
	public Objective(String label) {
		super(label);
		expr = null;
	}
	
	public String setExpression(String s) {
		String old = expr;
		expr = s;
		return old;
	}
	
	public String getExpression() {
		return expr;
	}
}

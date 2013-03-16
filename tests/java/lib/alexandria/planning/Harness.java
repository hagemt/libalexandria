package lib.alexandria.planning;

import lib.alexandria.naming.LabelledEntity;

public abstract class Harness extends LabelledEntity implements Planner, AutoCloseable {
	private int parallelism;
	
	public Harness(String label) {
		super(label);
		parallelism = Runtime.getRuntime().availableProcessors();
	}

	@Override
	public synchronized void setParallelism(int n) {
		parallelism = n;
	}
	
	public synchronized int getParallelism() {
		return parallelism;
	}
	
	@Override
	public void addLowerObjective(Objective j1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addUpperObjective(Objective j2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addTrainer(Trainer t) {
		// TODO Auto-generated method stub
	}

	@Override
	public void attach(Stream s) {
		// TODO Auto-generated method stub
	}
	
	// public abstract void onDisconnect();
}

package lib.alexandria.data.scaling;

import java.util.Collection;

import lib.alexandria.data.ClosedInterval;
import lib.alexandria.pipeline.Step;

public class Scaler<T extends Comparable<T>> implements Step {
	private ClosedInterval<T> interval;
	
	public Scaler(T low, T high) {
		interval = new ClosedInterval<T>(low, high);
	}
	
	public void scale(Collection<? extends T> c) {
		for (T t : c) {
			scale(t);
		}
	}
	
	public void scale(T t) {
		t.compareTo(interval.getLowerBound());
	}

	@Override
	public void visit() {
		// TODO Auto-generated method stub
	}
}

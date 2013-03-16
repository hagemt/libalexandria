package lib.alexandria.data.scaling;

public interface Scalable<N extends Number> extends Comparable<N> {
	void scale();
	N scaled();
}

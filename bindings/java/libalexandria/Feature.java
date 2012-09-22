package libalexandria;

public abstract class Feature<T extends Comparable> implements Comparable<Feature<T>> {
	protected final T datum;

	protected Feature(T t) {
		datum = t;
	}
}

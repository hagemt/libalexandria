package libalexandria;

public abstract class Feature<T extends Comparable> implements Comparable<Feature<T>>, Comparable<T> {
	protected final T datum;

	protected Feature(T t) {
		datum = t;
	}

	public final int compareTo(Feature<T> f) {
		return datum.compareTo(f.datum);
	}

	public abstract int compareTo(T t);
}

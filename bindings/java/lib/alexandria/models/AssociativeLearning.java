package lib.alexandria.models;

import lib.alexandria.Profileable;

/**
 * 
 * @author Tor E Hagemann
 */
public interface AssociativeLearning<T> extends Profileable {
	T recommended();
	T closest(T t);
}

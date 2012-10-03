package libalexandria;

import java.util.Map.Entry;

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 * @param <V>
 */
public interface Copyable<V> extends Cloneable {
	<P extends Entry<String, V>> Copyable<V> clone(String label, P... members);
}

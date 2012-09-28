package libalexandria.functional;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import libalexandria.LabelledEntity;

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <N>
 */
public class ParameterTreeMap<N extends Number> extends LabelledEntity implements Parameterized<N>, Iterable<String> {
	protected Map<String, N> parameters;
	
	public ParameterTreeMap(String label) {
		super(label);
		this.parameters = new TreeMap<String, N>();
	}
	
	public ParameterTreeMap(String label, ParameterTreeMap<? extends N> m) {
		this(label);
		if (m != null) {
			this.parameters.putAll(m.parameters);
		}
	}

	@Override
	public boolean addParameter(String name, N n) {
		/* don't clobber old mappings */
		if (this.hasParameter(name)) {
			return false;
		}
		/* put returns the old mapping, which should be null */
		return (parameters.put(name, n) == null);
	}

	@Override
	public N getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public boolean removeParameter(String name) {
		return (parameters.remove(name) != null);
	}

	@Override
	public synchronized boolean renameParameter(String oldName, String newName) {
		/* This is an ACID transaction */
		if (this.hasParameter(newName) || !this.hasParameter(oldName)) {
			return false;
		}
		/* value is not null, see getParameter */
		N value = this.getParameter(oldName);
		if (this.addParameter(newName, value)) {
			if (this.removeParameter(oldName)) {
				return true;
			}
			/* if removal of the old value fails, try to remove the new one */
			this.removeParameter(newName);
			/* in the worst case, oldName will be aliased to newName */
		}
		return false;
	}

	@Override
	public N setParameter(String name, N n) {
		if (this.hasParameter(name)) {
			return parameters.put(name, n);
		}
		return null;
	}

	@Override
	public boolean hasParameter(String name) {
		return parameters.containsKey(name);
	}

	@Override
	public Iterator<String> iterator() {
		return parameters.keySet().iterator();
	}
}

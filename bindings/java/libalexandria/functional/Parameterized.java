package libalexandria.functional;

public interface Parameterized<N extends Number> {
	public N getParameter(String name);
	public N setParameter(String name, N n);
	public boolean hasParameter(String name);
	public boolean addParameter(String name, N n);
	public boolean removeParameter(String name);
	public boolean renameParameter(String oldName, String newName);
}

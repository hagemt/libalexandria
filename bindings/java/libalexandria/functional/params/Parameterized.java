/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
package libalexandria.functional.params;

/**
 * Provides mechanisms for a type with parameters:
 * A "parameter" in this sense is a key-value pair,
 * of type (String, N), and is mutable by nature.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <N> a numeric type
 */
public interface Parameterized<N extends Number> extends Iterable<Parameter<N>> {
	/**
	 * Returns the numeric value of a named parameter.
	 * @param name the desired parameter's identifier
	 * @return the desired parameter's numeric value
	 */
	public N getParameter(String name);
	
	/**
	 * Assigns a given identifier some numeric value, provided that identifier is known.
	 * @param name the identifier to update, @see {@link #addParameter(String, Number)}
	 * @param n the numeric value with which to update the identified parameter
	 * @return if no such parameter exists, return null, otherwise, return the value that was replaced
	 */
	public N setParameter(String name, N n);
	
	/**
	 * Checks to see if a named parameter is represented in the type.
	 * @param name the desired parameter's identifier
	 * @return true if such a parameter is present, false otherwise
	 */
	public boolean hasParameter(String name);
	
	/**
	 * Appends a parameter with the given contents to this type.
	 * Please note that duplicate keys are not permitted.
	 * @param name the identifier of the parameter to assign
	 * @param n the numeric value to assign that parameter
	 * @return true if the addition was successful, false otherwise
	 */
	public boolean addParameter(String name, N n);
	
	/**
	 * Removes a parameter with the given identifier from this type.
	 * @param name the identifier of the parameter to remove
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean removeParameter(String name);
	
	/**
	 * Moves the identifier by which a certain parameter is known.
	 * This action should be considered a safe transaction.
	 * @param oldName an existing paramter's identifier
	 * @param newName a new identifier for the given parameter
	 * @return true if the parameter was renamed successfully, false otherwise
	 */
	public boolean renameParameter(String oldName, String newName);
}

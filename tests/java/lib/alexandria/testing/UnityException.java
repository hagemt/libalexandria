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
package lib.alexandria.testing;

import static lib.alexandria.Generate.LOG;

import lib.alexandria.naming.Labelled;

public class UnityException extends Exception implements Labelled {
	private static final long serialVersionUID = -5200338082961405898L;
	public static enum Type { FAILURE, INTERRUPTED, TIMEOUT, UNKNOWN; }
	private final Type type;

	// TODO constructors for each Type
	
	public UnityException(InterruptedException ie) {
		this(ie, Type.INTERRUPTED);
	}
	
	protected UnityException(Exception e, Type t) {
		super(e.getMessage(), e.getCause());
		type = t;
		LOG.e(this, e.getMessage());
	}
	
	public Type getType() {
		return type;
	}

	@Override
	public String getLabel() {
		return type.name();
	}
}

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
package lib.alexandria.pipeline;

import java.io.IOException;

import lib.alexandria.naming.Labelled;

/**
 * Facilitates handling exceptional behavior due to the native environment.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public class NativeAllocationException extends IOException {
	private static final long serialVersionUID = -3960122176317023786L;
	private final String prefix;

	public NativeAllocationException(Labelled e) {
		this(e, e.getLabel());
	}

	public NativeAllocationException(Labelled e, String msg) {
		super(msg);
		prefix = "[" + e.getClass() + "." + e.getLabel() + "] ";
	}

	@Override
	public String toString() {
		return prefix + super.getMessage();
	}
}

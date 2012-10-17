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
package lib.alexandria.functional;

import java.nio.channels.InterruptibleChannel;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import lib.alexandria.Profileable;

public interface Function<T> extends Callable<T>, InterruptibleChannel, Profileable {
	public int getArity();
	public Class<T> getReturnType();
	public Future<T> getFuture();
}

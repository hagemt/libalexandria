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
package lib.alexandria.functions;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import lib.alexandria.Profileable;

/**
 * Encapsulates the notion of a mathematical function's abstract abilities.
 * Functions may be evaluated eagerly or lazily.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @param <R> the return type
 */
public interface Function<R> extends Callable<R>, Profileable {
	/**
	 * Retrieve the number of arguments this Function requires for each evaluation.
	 * @return this Function's arity
	 */
	int getArity();

	/**
	 * Retrieve the type this Function will return upon evaluation.
	 * @return this Function's return type
	 */
	Class<R> getReturnType();

	/**
	 * Apply this Function lazily.
	 * @return an evaluator for this Function
	 * @see java.util.concurrent.Future
	 */
	Future<R> getFuture();

	/**
	 * Apply this Function eagerly.
	 * @return the result of this Function
	 */
	@Override
	public R call() throws Exception;
}

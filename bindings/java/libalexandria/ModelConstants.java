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
package libalexandria;

public interface ModelConstants {
	int MIN_HTM_DIMENSION = 32;
	int DEFAULT_HTM_DIMENSION = 32;

	int DEFAULT_RUN_TIME = 10000;
	int DEFAULT_JOIN_TIME = 1000;
	
	int DEFAULT_BUFFER_SIZE = 1024;
	
	String LA_PREFIX = "laJ";
	
	public static interface For<E extends Enum<E>> {
		E getType();
	}
	
	public static interface Some<E extends Enum<E>> {
		E getFlavor();
	}

	public static enum ModelType {
		SUPERVISED, UNSUPERVISED, REINFORCEMENT;
	}
}

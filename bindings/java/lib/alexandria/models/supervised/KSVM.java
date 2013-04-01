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
package lib.alexandria.models.supervised;

import java.io.IOException;

import lib.alexandria.functions.kernels.Kernel;
import lib.alexandria.functions.kernels.KernelType;

public class KSVM extends SupportVectorMachine {
	private final Kernel kernel;
	
	public KSVM(String label, KernelType type) {
		this(label, type.getDefault());
	}
	
	public KSVM(String label, Kernel k) {
		super(label);
		this.kernel = k;
	}
	
	public KSVM(Kernel k) {
		this(k.getLabel(), k);
	}

	@Override
	public void benchmark() {
		kernel.benchmark();
	}
	
	@Override
	public void close() throws IOException {
		kernel.close();
	}

	@Override
	public void visit() {
		// TODO Auto-generated method stub
	}
}

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
package libalexandria.supervised;

import java.io.IOException;

import libalexandria.Generate;
import libalexandria.functional.kernels.Kernel;
import libalexandria.functional.kernels.KernelType;

public class KSVM extends SupportVectorMachine {
	private final Kernel kernel;
	
	public KSVM() {
		this(Generate.randomLabel());
	}
	
	public KSVM(String label) {
		this(label, Generate.randomType(KernelType.class));
	}
	
	public KSVM(String label, KernelType type) {
		this(label, type.getDefault());
	}
	
	public KSVM(String label, Kernel k) {
		super(label);
		this.kernel = k;
	}
	
	@Override
	public void benchmark() {
		kernel.benchmark();
		try {
			kernel.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			System.err.println("Closed kernel " + kernel.getLabel() + " for " + this.getLabel());
		}
	}
}

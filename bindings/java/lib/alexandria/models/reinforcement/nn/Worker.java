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
package lib.alexandria.models.reinforcement.nn;

import java.io.IOException;
import java.io.InputStream;

import static lib.alexandria.Generate.LOG;
import static lib.alexandria.Generate.randomInteger;

import lib.alexandria.naming.LabelledEntity;

public abstract class Worker extends LabelledEntity implements Runnable {
	private InputStream input;
	protected byte[] data;
	
	public Worker(Cortex cortex) {
		super(cortex.getLabel() + "-worker");
		/* Flatten the columns */
		int dim = cortex.getDimension();
		this.data = new byte[dim * dim];
		/* Provide a general source of input */
		this.input = new InputStream() {
			@Override
			public int read() throws IOException {
				return randomInteger(0x100);
			}
		};
	}

	public abstract void operate(byte[] a, byte[] b);

	@Override
	public void run() {
		/* Apply the operate function */
		long iterations = 0;
		byte[] next = new byte[data.length];
		try {
			/* This should run forever, uninterrupted */
			while (input.read(next) == next.length) {
				if (Thread.interrupted()) {
					break;
				}
				operate(data, next);
				++iterations;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			LOG.i(this, "thread completed " + iterations + " iterations");
		}
	}
}

package libalexandria.ann;

import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import libalexandria.LabelledEntity;
import libalexandria.LearningModel;
import libalexandria.ModelType;

public class Cortex extends LearningModel {
	private static interface Constants {
		public static final int DIM = 128;
		public static final long TIMEOUT = 10000;
		public static final String SONAME = "cortex";
		public static final String SUFFIX = "-worker";
	}
	
	static {
		System.loadLibrary(Constants.SONAME);
	}
	
	/* Each column on an NxN grid */
	private byte[][] columns;
	protected Thread worker;
	
	public static abstract class Worker extends LabelledEntity implements Runnable {
		private InputStream input;
		protected byte[] data;
		protected Cortex cortex;
		
		public Worker(Cortex cortex) {
			super(cortex.label + Constants.SUFFIX);
			this.cortex = cortex;
			/* Flatten the columns */
			int size = this.cortex.columns.length;
			this.data = new byte[size * size];
			this.unwind(this.cortex.columns, this.data);
			/* Provide a general source of input */
			this.input = new InputStream() {
				private Random source = new Random();
				@Override
				public int read() throws IOException {
					return source.nextInt();
				}
			};
		}

		public abstract void operate(byte[] a, byte[] b);
		public abstract void unwind(byte[][] src, byte[] dst);
		public abstract void wind(byte[] src, byte[][] dst);

		@Override
		public void run() {
			/* Apply the operate function */
			long iterations = 0;
			byte[] next = new byte[data.length];
			try {
				while (input.read(next) == next.length) {
					operate(data, next);
					if (Thread.interrupted()) {
						break;
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				this.wind(data, cortex.columns); 
				System.err.println("[thread: " + label + "] has completed " + ++iterations + " iterations...");
			}
		}
	}
	
	public Cortex(String label, boolean isNative) {
		super(ModelType.REINFORCEMENT, label, true);
		columns = new byte[Constants.DIM][];
		for (int i = 0; i < columns.length; ++i) {
			columns[i] = new byte[Constants.DIM];
			Arrays.fill(columns[i], (byte)(0));
		}
		if (isNative) {
			worker = new Thread(new NativeWorker(this));
		} else {
			worker = new Thread(new Worker(this) {
				@Override
				public void operate(byte[] a, byte[] b) {
					for (int i = 0; i < a.length; ++i) {
						a[i] ^= b[i % b.length];
					}
				}
				@Override
				public void unwind(byte[][] src, byte[] dst) {
					for (int i = 0; i < dst.length; ++i) {
						dst[i] = src[i / src.length][i % src.length];
					}
				}
				@Override
				public void wind(byte[] src, byte[][] dst) {
					for (int i = 0; i < src.length; ++i) {
						dst[i / dst.length][i % dst.length] = src[i];
					}
				}
			});
		}
	}
	
	public void learn() {
		worker.start();
	}
	
	public void halt() {
		worker.interrupt();
		try {
			worker.join(Constants.TIMEOUT);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public static void main(String... args) throws InterruptedException {
		Cortex java_cortex, native_cortex;
		/* Test Java performance */
		java_cortex = new Cortex("JavaCortex", false);
		java_cortex.learn();
		Thread.sleep(Constants.TIMEOUT);
		java_cortex.halt();
		/* Test native performance */
		native_cortex = new Cortex("NativeCortex", true);
		native_cortex.learn();
		Thread.sleep(Constants.TIMEOUT);
		native_cortex.halt();
	}
}

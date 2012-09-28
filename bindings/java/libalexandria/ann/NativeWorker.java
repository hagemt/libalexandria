package libalexandria.ann;

import libalexandria.ann.Cortex.Worker;

public class NativeWorker extends Worker {
	public NativeWorker(Cortex c) {
		super(c);
	}

	@Override
	public native void operate(byte[] a, byte[] b);

	@Override
	public native void wind(byte[] src, byte[][] dst);

	@Override
	public native void unwind(byte[][] src, byte[] dst);
}

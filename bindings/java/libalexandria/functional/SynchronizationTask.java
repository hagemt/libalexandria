package libalexandria.functional;

import java.io.DataInputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;

public class SynchronizationTask implements Runnable, Callable<Double> {
	private RealParameterizedFunction<? extends Number> func;
	private DataInputStream stream;
	
	public SynchronizationTask(RealParameterizedFunction<? extends Number> f, ReadableByteChannel c) {
		this.func = f;
		this.stream = new DataInputStream(Channels.newInputStream(c));
	}

	@Override
	public void run() {
		func.sync(stream);
	}

	@Override
	public Double call() throws Exception {
		return stream.readDouble();
	}
}

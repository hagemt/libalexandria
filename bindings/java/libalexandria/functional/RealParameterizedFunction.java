package libalexandria.functional;

import java.io.DataInput;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import java.nio.ByteBuffer;

import java.util.Collection;
import java.util.Iterator;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import libalexandria.ModelConstants;
import libalexandria.functional.params.ParameterMap;

public abstract class RealParameterizedFunction<N extends Number> extends ParameterMap<N> implements Function<Double> {
	private ByteBuffer buffer;
	private FileChannel results;

	public void initialize() throws IOException {
		buffer = alloc();
		assert(buffer.isDirect());
		/* TODO in memory? */
		File temp_file = File.createTempFile(ModelConstants.LA_PREFIX, getLabel());
		results = new RandomAccessFile(temp_file, "rw").getChannel();
	}
	
	protected RealParameterizedFunction(String label) {
		super(label);
		try {
			initialize();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected RealParameterizedFunction(RealParameterizedFunction<? extends N> base) {
		super(base);
		try {
			initialize();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Class<Double> getReturnType() {
		return Double.class;
	}
	
	@Override
	public Future<Double> getFuture() {
		return new FutureTask<Double>(this);
	}

	@Override
	public Double call() throws Exception {
		return new SynchronizationTask(this, results).call();
	}

	/* Required of subclasses */
	private native ByteBuffer alloc();
	protected abstract void sync(DataInput stream);
	private native void free();

	/* Provided as convenience to subclasses */
	protected synchronized int remaining() {
		return (buffer == null) ? 0 : buffer.remaining();
	}
	
	public synchronized int offer(Collection<? extends N> c) {
		int written = 0;
		Iterator<? extends N> itr = c.iterator();
		for (int r = remaining(); r >= Double.SIZE; ++written) {
			if (itr.hasNext()) {
				buffer.putDouble(itr.next().doubleValue());
			} else {
				// TODO name this thread / nix this thread?
				new Thread(new SynchronizationTask(this, results)).start();
				break;
			}
		}
		return written;
	}

	/* Implemented functions of InterruptableChannel */
	
	@Override
	public synchronized boolean isOpen() {
		return (buffer != null);
	}
	
	@Override
	public synchronized void close() throws IOException {
		buffer = null; free();
		results.close();
	}
	
}

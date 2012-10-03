package libalexandria.functional;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import libalexandria.ModelConstants;
import libalexandria.functional.params.ParameterMap;

public abstract class RealParameterizedFunction<N extends Number> extends ParameterMap<N> implements Function<Double> {
	private Buffer queue;
	
	protected void initializeBuffer() {
		queue = ByteBuffer.allocateDirect(ModelConstants.DEFAULT_BUFFER_SIZE);
	}

	protected RealParameterizedFunction(String label) {
		super(label);
		initializeBuffer();
	}

	protected RealParameterizedFunction(ParameterMap<? extends N> base) {
		super(base);
		initializeBuffer();
	}

	@Override
	public Class<Double> getReturnType() {
		return Double.class;
	}
	
	@Override
	public Future<Double> callLater() {
		FutureTask<Double> result = null;
		try {
			result = new FutureTask<Double>(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	protected int getRemaining() {
		return (queue == null) ? 0 : queue.remaining();
	}
	
	public abstract boolean hasEnough();
}

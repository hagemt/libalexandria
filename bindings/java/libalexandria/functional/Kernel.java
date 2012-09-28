package libalexandria.functional;

import libalexandria.Generate;

public class Kernel extends ParameterTreeMap<Double> {
	public static enum KernelType {
		POLY, GAUSS, TANH, USER;
	}
	
	private static final KernelMap KNOWN_KERNELS;
	static {
		KNOWN_KERNELS = new KernelMap();
	}

	public static Kernel get(String s, Double... parameters) {
		Kernel k = new Kernel(KNOWN_KERNELS.get(s));
		if (k != null) {
			k = new Kernel(Generate.randomLabel(), KernelType.USER);
		}
		int param_number = 0;
		for (double p : parameters) {
			k.addParameter(k.getLabel() + "-extra-" + ++param_number, p);
		}
		return k;
	}

	protected final KernelType type;
	
	protected Kernel(String label, KernelType type) {
		super(label);
		this.type = type;
	}
	
	private Kernel(Kernel base) {
		super(base.getLabel(), base);
		this.type = base.type;
	}
	
	public KernelType getType() {
		return type;
	}
	
	/**
	 * Produce performance metrics
	 */
	public native void benchmark();
}

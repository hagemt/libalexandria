package libalexandria.functional.kernels;

import java.util.TreeMap;

import libalexandria.Generate;

/**
 * Describes a kernel by family (category)
 * @author Tor E Hagemann <hagemt@rpi.edu
 * @since libalexandria 0.1
 */
public enum KernelType implements Generate.A<Kernel> {
	POLY(
			new PolynomialKernel("LinearHomogeneous", 1),
			new PolynomialKernel("QuadraticHomogeneous", 2),
			new PolynomialKernel("LinearInhomogeneousUnit", 1, 1),
			new PolynomialKernel("QuadraticInhomogeneousUnit", 2, 1)),
	GAUSS(
			new GaussianKernel("RadialBasisGammaUnit", StatFlavor.GAMMA, 1),
			new GaussianKernel("RadialBasisSigmaUnit", StatFlavor.SIGMA, 1)),
	HYPER(
			new HyperbolicKernel("HyperbolicSineUnit", TrigFlavor.SIN, 1, -1),
			new HyperbolicKernel("HyperbolicCosineUnit", TrigFlavor.COS, 1, -1),
			new HyperbolicKernel("HyperbolicTangentUnit", TrigFlavor.TAN, 1, -1)),
	USER;
	
	private TreeMap<String, Kernel> known_kernels;
	
	private KernelType(Kernel... kernels) {
		known_kernels = new TreeMap<String, Kernel>();
		for (Kernel k : kernels) {
			known_kernels.put(k.getLabel(), k);
		}
		Kernel k = this.getDefault();
		known_kernels.put(k.getLabel(), k);
	}

	@Override
	public Kernel getDefault() {
		return new Kernel(this);
	}

	@Override
	public Kernel knownAs(String label) {
		return known_kernels.get(label);
	}

	@Override
	public boolean knows(String label) {
		return known_kernels.containsKey(label);
	}
	
	public String toString() {
		return this.name() + "KERNEL";
	}
}
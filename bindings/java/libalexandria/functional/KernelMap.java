package libalexandria.functional;

import java.util.EnumMap;
import java.util.TreeMap;

import libalexandria.functional.Kernel.KernelType;

@SuppressWarnings("serial")
public class KernelMap extends TreeMap<String, Kernel> {
	/**
	 * A polynomial kernel has the form K(A,B) = (DOT(A,B)+C)^D
	 * where D is a positive (TODO real?) number
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 */
	public static class PolynomialKernel extends Kernel {
		protected PolynomialKernel(String label, double degree, double constant) {
			super(label, KernelType.POLY);
			if (degree <= 0) {
				throw new IllegalArgumentException("degree must be positive");
			}
			this.addParameter("degree", degree);
			this.addParameter("constant", constant);
		}
		/* By default, make kernel homogeneous (C = 0) */
		protected PolynomialKernel(String label, double degree) {
			this(label, degree, 0);
		}
	}
	
	/**
	 * A Gaussian kernel has the form K(A,B) = EXP(-GAMMA*NORM(A-B)^2)
	 * where GAMMA > 0 and may instead be specified GAMMA = 1/2*SIGMA^2
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 */
	public static class GaussianKernel extends Kernel {
		public static enum Parameterization { GAMMA, SIGMA }
		private Parameterization type;
		protected GaussianKernel(String label, Parameterization type, double value) {
			super(label, KernelType.GAUSS);
			if (value <= 0) {
				throw new IllegalArgumentException("parameter must be positive");
			}
			this.type = (type == null) ? Parameterization.GAMMA : type;
			this.addParameter(this.type.name().toLowerCase(), value);
		}
	}
	
	/**
	 * A Hyperbolic kernel has the form K(A,B) = TANH(KAPPA*DOT(A,B)+C)
	 * where KAPPA > 0 and C < 0
	 * @author Tor E Hagemann <hagemt@rpi.edu>
	 */
	public static class HyperbolicKernel extends Kernel {
		protected HyperbolicKernel(String label, double kappa, double constant) {
			super(label, KernelType.TANH);
			this.addParameter("kappa", kappa);
			this.addParameter("", constant);
		}
	}
	
	private static EnumMap<KernelType, String> DEFAULT_LABELS;
	static {
		DEFAULT_LABELS = new EnumMap<KernelType, String>(KernelType.class);
		DEFAULT_LABELS.put(KernelType.POLY, "QuadraticHomogeneous");
		DEFAULT_LABELS.put(KernelType.GAUSS, "RadialBasisSigmaUnit");
		DEFAULT_LABELS.put(KernelType.TANH, "HyperbolicTangentUnit");
		DEFAULT_LABELS.put(KernelType.USER, "UserEmpty");
	}
	
	public KernelMap() {
		this.add(new PolynomialKernel("LinearHomogeneous", 1));
		this.add(new PolynomialKernel("QuadraticHomogeneous", 2));
		this.add(new PolynomialKernel("LinearInhomogeneousUnit", 1, 1));
		this.add(new PolynomialKernel("QuadraticInhomogeneousUnit", 2, 1));
		this.add(new GaussianKernel("RadialBasisGammaUnit", GaussianKernel.Parameterization.GAMMA, 1));
		this.add(new GaussianKernel("RadialBasisSigmaUnit", GaussianKernel.Parameterization.SIGMA, 1));
		this.add(new HyperbolicKernel("HyperbolicTangentUnit", 1, -1));
		this.add(Kernel.get("UserEmpty"));
		for (KernelType t : KernelType.values()) {
			this.put(t.name(), this.get(DEFAULT_LABELS.get(t)));
		}
	}
	
	private void add(Kernel k) {
		this.put(k.getLabel(), k);
	}
}

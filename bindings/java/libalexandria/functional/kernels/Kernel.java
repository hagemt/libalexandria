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
package libalexandria.functional.kernels;

import java.util.EnumMap;
import java.util.TreeMap;

import libalexandria.functional.ParameterizedFunction;
import libalexandria.functional.params.ConstantRealParameter;

/**
 * Does not encapsulate computation of a kernel function,
 * instead, provides metadata concerning parameters and such.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Kernel extends ParameterizedFunction<Double> {
	/**
	 * Describes a kernel by family (category)
	 * @author Tor E Hagemann <hagemt@rpi.edu
	 * @since libalexandria 0.1
	 */
	public static enum KernelType {
		POLY, GAUSS, TANH, USER;
	}

	/**
	 * Provide a mapping for fetching common kernels
	 */
	private static final TreeMap<String, Kernel> KNOWN_KERNELS;
	/**
	 * Define the default mappings between each KernelType and Kernel metadata
	 */
	private static final EnumMap<KernelType, String> DEFAULT_LABELS;

	/**
	 * Crikey, there's a lot of static data here
	 */
	static {
		DEFAULT_LABELS = new EnumMap<KernelType, String>(KernelType.class);
		DEFAULT_LABELS.put(KernelType.POLY, "QuadraticHomogeneous");
		DEFAULT_LABELS.put(KernelType.GAUSS, "RadialBasisSigmaUnit");
		DEFAULT_LABELS.put(KernelType.TANH, "HyperbolicTangentUnit");
		DEFAULT_LABELS.put(KernelType.USER, "UserEmpty");
		KNOWN_KERNELS = new TreeMap<String, Kernel>() {
			{
				add(new PolynomialKernel("LinearHomogeneous", 1));
				add(new PolynomialKernel("QuadraticHomogeneous", 2));
				add(new PolynomialKernel("LinearInhomogeneousUnit", 1, 1));
				add(new PolynomialKernel("QuadraticInhomogeneousUnit", 2, 1));
				add(new GaussianKernel("RadialBasisGammaUnit", GaussianKernel.Parameterization.GAMMA, 1));
				add(new GaussianKernel("RadialBasisSigmaUnit", GaussianKernel.Parameterization.SIGMA, 1));
				add(new HyperbolicKernel("HyperbolicTangentUnit", 1, -1));
				add(new Kernel(DEFAULT_LABELS.get(KernelType.USER), KernelType.USER));
				/* Ensure each type has its default */
				for (KernelType t : KernelType.values()) {
					put(t.name(), this.get(DEFAULT_LABELS.get(t)));
				}
			}
			private final void add(Kernel k) {
				put(k.getLabel(), k);
			}
			/**
			 * @since libalexandria v0.1
			 */
			private static final long serialVersionUID = 4966877215970022791L;
		};
	}

	/**
	 * An important function used to retrieve a certain kernel.
	 * If no such kernel is known, one is constructed and returned.
	 * This kernel is independent and has mutable parameters.
	 * @param name an identifier for the kernel (e.g. any KernelType.name())
	 * @param parameters a list of extra parameters desired in the kernel
	 * @return an instantiated kernel with certain parameters
	 */
	public static Kernel get(String name, ConstantRealParameter... parameters) {
		/* Make sure to produce a copy so we can preserve the DB */
		Kernel k = new Kernel(KNOWN_KERNELS.get(name));
		if (k != null) {
			k = new Kernel(name, KernelType.USER);
		}
		/* TODO work out parameter classes... */
		for (ConstantRealParameter parameter : parameters) {
			k.addParameter(parameter.getKey(), parameter.getValue());
		}
		return k;
	}

	/**
	 * Describes this kernel's family (category)
	 */
	protected final KernelType type;
	
	protected Kernel(String label, KernelType type) {
		super(label);
		this.type = type;
	}

	/**
	 * Used internally to deep-copy the parameterization of a kernel
	 * @param base a kernel from which to construct a copy
	 */
	private Kernel(Kernel base) {
		/* a Kernel is a ParameterMap, so use this */
		super(base);
		this.type = base.type;
	}
	
	/**
	 * Retrieve the family (category) of this kernel
	 * @return a type representing this kernel
	 */
	public KernelType getType() {
		return type;
	}
	
	/**
	 * Produce performance metrics for this kernel, with current parameters
	 */
	@Override
	public native void benchmark();

	@Override
	public int arity() {
		return 2;
	}

	@Override
	public Class<Double> returnType() {
		return Double.class;
	}
}

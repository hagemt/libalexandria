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
package lib.alexandria;

/**
 * Encapsulates the concept of a complex (imaginary) number
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public final strictfp class ComplexNumber extends Number {
	/**
	 * @since libalexandria v0.1
	 */
	private static final long serialVersionUID = 4821064244514849408L;
	private static char IMAGINARY_UNIT = 'i';
	
	public static synchronized char setSymbol(char c) {
		char oldUnit = ComplexNumber.IMAGINARY_UNIT;
		ComplexNumber.IMAGINARY_UNIT = c;
		return oldUnit;
	}

	private final double a, b;
	
	public ComplexNumber(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	private double normalized() {
		return StrictMath.sqrt(a * a + b * b);
	}

	@Override
	public double doubleValue() {
		return this.normalized();
	}

	@Override
	public float floatValue() {
		return (float)(this.doubleValue());
	}

	@Override
	public int intValue() {
		return StrictMath.round(this.floatValue());
	}

	@Override
	public long longValue() {
		return StrictMath.round(this.doubleValue());
	}

	@Override
	public String toString() {
		return a + (b < 0 ? "" : "+") + b + ComplexNumber.IMAGINARY_UNIT;
	}
}

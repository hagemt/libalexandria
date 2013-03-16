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
package lib.alexandria.functions.wavelets;

import java.util.TreeMap;

import lib.alexandria.Generate;

/**
 * Indicates whether or not a wavelet's form is continuous
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public enum WaveletType implements Generate.A<Wavelet> {
	CONTINUOUS, DISCRETE;
	
	private TreeMap<String, Wavelet> known_wavelets;
	
	private WaveletType(Wavelet... wavelets) {
		known_wavelets = new TreeMap<String, Wavelet>();
		for (Wavelet w : wavelets) {
			known_wavelets.put(w.getLabel(), w);
		}
		known_wavelets.put(this.toString(), new Wavelet(this));
	}

	@Override
	public Wavelet knownAs(String label) {
		return known_wavelets.get(label);
	}

	@Override
	public Wavelet getDefault() {
		return knownAs(this.toString());
	}

	@Override
	public boolean knows(String label) {
		return known_wavelets.containsKey(label);
	}

	@Override
	public String toString() {
		return this.name() + "WAVELET";
	}
}
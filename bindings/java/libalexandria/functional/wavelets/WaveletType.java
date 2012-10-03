package libalexandria.functional.wavelets;

import java.util.TreeMap;

import libalexandria.Generate;

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
package lib.alexandria.planning;

public abstract class HarnessException extends RuntimeException implements Recoverable {
	private static final long serialVersionUID = -6290429754120779447L;
	private final Harness source;
	
	public HarnessException(Harness h) {
		source = h;
	}
	
	public Harness getSource() {
		return source;
	}
}

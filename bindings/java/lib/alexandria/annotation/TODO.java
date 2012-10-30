package lib.alexandria.annotation;

/**
 * Marks an entity as needing work.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public @interface TODO {
	String[] value() default "everything";
	public enum Priority { LOW, NORMAL, HIGH, CRITICAL };
	Priority priority() default Priority.NORMAL;
}

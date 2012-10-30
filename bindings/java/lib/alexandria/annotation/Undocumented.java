package lib.alexandria.annotation;

import java.lang.annotation.ElementType;

/**
 * Specifies that an entity is undocumented.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
public @interface Undocumented {
	ElementType[] value() default ElementType.TYPE;
	boolean fixme() default true;
}

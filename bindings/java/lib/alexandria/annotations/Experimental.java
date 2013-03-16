package lib.alexandria.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an entity as an experimental feature.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 * @since libalexandria v0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Experimental {
	String value() default "because the developer didn't feel like testing";
}

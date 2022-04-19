package Discarpet.script.parsable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Fields annotated with {@link Optional} are not required to be
 * present in maps when trying to parse them
 * to a {@link ParsableConstructor} using {@link Parser}.
 * Instead, a default value can be provided by directly
 * assigning the field in the target class.
 */
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface Optional {
    
}

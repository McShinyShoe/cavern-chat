package net.shinyshoe.cavernChat.client.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a setting the player may look at but never click. The value is still
 * listed with its current state, and it can still be changed in the config
 * file manually.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReadOnly {
}

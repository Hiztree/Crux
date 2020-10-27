package io.github.hiztree.crux.api.command.annotations

/**
 * An annotation to specify the description of the command.
 *
 * @param description the description
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Description(val description: String)
package io.github.hiztree.crux.api.command.annotations

/**
 * An annotation to specify the labels of the command.
 *
 * @param labels the labels/aliases
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Label(val labels: Array<out String>)
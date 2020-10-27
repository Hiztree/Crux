package io.github.hiztree.crux.api.command.annotations

/**
 * An annotation to specify if a command is player only (console can't send it)
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PlayerOnly
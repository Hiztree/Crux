package io.github.hiztree.crux.api.command.annotations

/**
 * An annotation to specify the permission of the command.
 *
 * @param permission the permission
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(val permission: String)
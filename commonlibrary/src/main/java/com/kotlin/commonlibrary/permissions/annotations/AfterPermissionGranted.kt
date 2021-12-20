package com.kotlin.commonlibrary.permissions.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AfterPermissionGranted(val value: Int)

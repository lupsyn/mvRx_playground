package com.tui.tda.test

import org.mockito.Mockito

object KotlinTypeSafeMatchers {

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    inline fun <reified T : Any> argThat(noinline predicate: T.() -> Boolean): T {
        return Mockito.argThat { arg: T? -> arg?.predicate() ?: false } ?: uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}
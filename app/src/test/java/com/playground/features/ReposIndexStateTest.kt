package com.playground.features

import com.google.common.truth.Truth.assertThat
import com.playground.base.TestBase
import com.playground.models.GitRepo

import org.junit.Test
import org.mockito.Mock

class ReposIndexStateTest: TestBase() {
    @Mock
    private lateinit var gitRepo: GitRepo

    @Test
    fun testState() {
        val list = listOf(gitRepo)
        val state = ReposIndexState(repos = list)

        assertThat(state.repos).isEqualTo(list)
    }
}
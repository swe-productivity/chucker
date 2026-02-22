package com.chuckerteam.chucker.api

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests for [ChuckerTransactionListFragment].
 *
 * Run with:
 *   ./gradlew :library:connectedAndroidTest
 */
@RunWith(AndroidJUnit4::class)
class ChuckerTransactionListFragmentTest {
    @Test
    fun newInstance_returnsNonNullFragment() {
        val fragment = ChuckerTransactionListFragment.newInstance()
        assertThat(fragment).isNotNull()
    }

    @Test
    fun getTransactionListFragment_viaChuckerObject_returnsFragment() {
        val fragment = Chucker.getTransactionListFragment()
        assertThat(fragment).isInstanceOf(ChuckerTransactionListFragment::class.java)
    }

    @Test
    fun setOnTransactionClickListener_doesNotThrow() {
        val fragment = ChuckerTransactionListFragment.newInstance()
        fragment.setOnTransactionClickListener { /* no-op */ }
        // No exception means the API is wired correctly
    }

    @Test
    fun fragment_canBeLaunchedInContainer() {
        val scenario = launchFragmentInContainer<ChuckerTransactionListFragment>()
        scenario.onFragment { fragment ->
            assertThat(fragment.isAdded).isTrue()
        }
    }
}

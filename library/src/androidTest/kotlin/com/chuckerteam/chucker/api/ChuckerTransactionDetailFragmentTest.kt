package com.chuckerteam.chucker.api

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation tests for [ChuckerTransactionDetailFragment].
 *
 * Run with:
 *   ./gradlew :library:connectedAndroidTest
 */
@RunWith(AndroidJUnit4::class)
class ChuckerTransactionDetailFragmentTest {
    @Test
    fun newInstance_returnsNonNullFragment() {
        val fragment = ChuckerTransactionDetailFragment.newInstance(transactionId = 1L)
        assertThat(fragment).isNotNull()
    }

    @Test
    fun getTransactionDetailFragment_viaChuckerObject_returnsFragment() {
        val fragment = Chucker.getTransactionDetailFragment(transactionId = 42L)
        assertThat(fragment).isInstanceOf(ChuckerTransactionDetailFragment::class.java)
    }

    @Test
    fun newInstance_withTransactionIdZero_doesNotThrow() {
        val fragment = ChuckerTransactionDetailFragment.newInstance(transactionId = 0L)
        assertThat(fragment).isNotNull()
    }

    @Test
    fun fragment_canBeLaunchedInContainer() {
        val scenario =
            launchFragmentInContainer {
                ChuckerTransactionDetailFragment.newInstance(transactionId = 0L)
            }
        scenario.onFragment { fragment ->
            assertThat(fragment.isAdded).isTrue()
        }
    }
}

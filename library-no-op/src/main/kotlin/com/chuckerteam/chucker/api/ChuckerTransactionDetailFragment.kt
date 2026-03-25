package com.chuckerteam.chucker.api

import androidx.fragment.app.Fragment

/**
 * No-op implementation.
 */
@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
public class ChuckerTransactionDetailFragment : Fragment() {
    public companion object {
        @JvmStatic
        public fun newInstance(transactionId: Long): ChuckerTransactionDetailFragment =
            ChuckerTransactionDetailFragment()
    }
}

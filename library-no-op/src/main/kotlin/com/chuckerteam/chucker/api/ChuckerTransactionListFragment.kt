package com.chuckerteam.chucker.api

import androidx.fragment.app.Fragment

/**
 * No-op implementation.
 */
@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
public class ChuckerTransactionListFragment : Fragment() {
    public fun setOnTransactionClickListener(listener: (Long) -> Unit) {
        // Empty method for the library-no-op artifact
    }

    public companion object {
        @JvmStatic
        public fun newInstance(): ChuckerTransactionListFragment = ChuckerTransactionListFragment()
    }
}

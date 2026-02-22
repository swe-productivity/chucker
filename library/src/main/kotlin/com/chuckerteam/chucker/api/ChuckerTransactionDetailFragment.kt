package com.chuckerteam.chucker.api

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chuckerteam.chucker.databinding.ChuckerFragmentTransactionDetailBinding
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import com.chuckerteam.chucker.internal.ui.transaction.TransactionPagerAdapter
import com.chuckerteam.chucker.internal.ui.transaction.TransactionViewModel
import com.chuckerteam.chucker.internal.ui.transaction.TransactionViewModelFactory

/**
 * A [Fragment] that displays the details of a single HTTP transaction, with tabs for
 * Overview, Request, and Response.
 *
 * This fragment can be embedded directly into your app's Activities or Fragments,
 * giving you full control over the surrounding UI (e.g. your own toolbar or navigation).
 *
 * Use [newInstance] with a transaction ID to create an instance. The transaction ID is
 * typically provided by [ChuckerTransactionListFragment.setOnTransactionClickListener].
 *
 * **Note:** Only one [ChuckerTransactionDetailFragment] should be shown per Activity at a time,
 * as it shares its [TransactionViewModel] via the Activity's ViewModel store.
 *
 * **Example usage:**
 * ```kotlin
 * val detailFragment = Chucker.getTransactionDetailFragment(transactionId)
 * supportFragmentManager.beginTransaction()
 *     .replace(R.id.container, detailFragment)
 *     .addToBackStack(null)
 *     .commit()
 * ```
 */
public class ChuckerTransactionDetailFragment : Fragment() {
    private val transactionId: Long by lazy {
        requireArguments().getLong(ARG_TRANSACTION_ID)
    }

    private var bindingRef: ChuckerFragmentTransactionDetailBinding? = null
    private val binding get() = bindingRef!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        RepositoryProvider.initialize(context.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pre-create TransactionViewModel in the Activity's ViewModel store so the inner
        // fragments (TransactionOverviewFragment, TransactionPayloadFragment), which rely on
        // activityViewModels(), find it already initialised with the correct transactionId.
        ViewModelProvider(
            requireActivity(),
            TransactionViewModelFactory(transactionId),
        )[TransactionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bindingRef = ChuckerFragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = TransactionPagerAdapter(requireContext(), childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingRef = null
    }

    public companion object {
        private const val ARG_TRANSACTION_ID = "transaction_id"

        /**
         * Creates a new instance of [ChuckerTransactionDetailFragment] for the given transaction.
         *
         * @param transactionId The ID of the HTTP transaction to display.
         */
        @JvmStatic
        public fun newInstance(transactionId: Long): ChuckerTransactionDetailFragment =
            ChuckerTransactionDetailFragment().apply {
                arguments =
                    Bundle().apply {
                        putLong(ARG_TRANSACTION_ID, transactionId)
                    }
            }
    }
}

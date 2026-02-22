package com.chuckerteam.chucker.api

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.chuckerteam.chucker.databinding.ChuckerFragmentTransactionListBinding
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import com.chuckerteam.chucker.internal.ui.MainViewModel
import com.chuckerteam.chucker.internal.ui.transaction.TransactionAdapter

/**
 * A [Fragment] that displays the list of recorded HTTP transactions.
 *
 * This fragment can be embedded directly into your app's Activities or Fragments,
 * giving you full control over the surrounding UI (e.g. your own toolbar or navigation).
 *
 * Use [newInstance] to create an instance, and [setOnTransactionClickListener] to be
 * notified when the user taps a transaction row.
 *
 * **Example usage:**
 * ```kotlin
 * val listFragment = Chucker.getTransactionListFragment()
 * listFragment.setOnTransactionClickListener { transactionId ->
 *     val detailFragment = Chucker.getTransactionDetailFragment(transactionId)
 *     // add detailFragment to your back stack
 * }
 * supportFragmentManager.beginTransaction()
 *     .replace(R.id.container, listFragment)
 *     .commit()
 * ```
 */
public class ChuckerTransactionListFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    private var binding: ChuckerFragmentTransactionListBinding? = null

    private lateinit var transactionsAdapter: TransactionAdapter

    private var onTransactionClickListener: ((Long) -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        RepositoryProvider.initialize(context.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ChuckerFragmentTransactionListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        transactionsAdapter =
            TransactionAdapter(
                context = requireContext(),
                onTransactionClick = { transactionId ->
                    val listener = onTransactionClickListener
                    if (listener != null) {
                        listener.invoke(transactionId)
                    } else {
                        parentFragmentManager
                            .beginTransaction()
                            .replace(
                                id,
                                ChuckerTransactionDetailFragment.newInstance(transactionId),
                            ).addToBackStack(null)
                            .commit()
                    }
                },
                onTransactionLongClick = {},
            )

        binding!!.transactionsRecyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = transactionsAdapter
        }

        viewModel.transactions.observe(viewLifecycleOwner) { transactionTuples ->
            transactionsAdapter.submitList(transactionTuples)
            binding!!.emptyStateText.isVisible = transactionTuples.isEmpty()
            binding!!.transactionsRecyclerView.isVisible = transactionTuples.isNotEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Sets a listener to be invoked when a transaction row is tapped.
     *
     * @param listener A callback receiving the ID of the selected transaction. Use it to
     *                 navigate to a detail screen, e.g. by adding a [ChuckerTransactionDetailFragment].
     */
    public fun setOnTransactionClickListener(listener: (Long) -> Unit) {
        onTransactionClickListener = listener
    }

    public companion object {
        /**
         * Creates a new instance of [ChuckerTransactionListFragment].
         */
        @JvmStatic
        public fun newInstance(): ChuckerTransactionListFragment = ChuckerTransactionListFragment()
    }
}

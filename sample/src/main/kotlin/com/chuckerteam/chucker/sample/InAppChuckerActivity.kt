package com.chuckerteam.chucker.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerTransactionDetailFragment
import com.chuckerteam.chucker.api.ChuckerTransactionListFragment

/**
 * Demonstrates embedding Chucker screens directly inside your own Activity,
 * rather than launching the standalone Chucker Activity.
 *
 * The transaction list is shown first. Tapping a row replaces it with the detail
 * view — all inside this single Activity with a back stack.
 */
class InAppChuckerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_app_chucker)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "In-App HTTP Inspector"

        if (savedInstanceState == null) {
            val listFragment: ChuckerTransactionListFragment = Chucker.getTransactionListFragment()

            listFragment.setOnTransactionClickListener { transactionId ->
                val detailFragment: ChuckerTransactionDetailFragment =
                    Chucker.getTransactionDetailFragment(transactionId)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, listFragment)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InAppChuckerActivity::class.java))
        }
    }
}

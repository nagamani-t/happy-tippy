package com.example.happy

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val PREFS_NAME = "TippyPreferences"
private const val BASE_AMOUNT_KEY = "baseAmount"
private const val TIP_PERCENTAGE_KEY = "tipPercentage"
private const val TIP_AMOUNT_KEY = "tipAmount"
private const val TOTAL_AMOUNT_KEY = "totalAmount"

/**
 * A simple [Fragment] subclass.
 * Use the [WalletFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalletFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var baseAmountTextView: TextView
    private lateinit var tipPercentageTextView: TextView
    private lateinit var tipAmountTextView: TextView
    private lateinit var totalAmountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0)
        baseAmountTextView = view.findViewById(R.id.baseNumberNote)
        tipPercentageTextView = view.findViewById(R.id.progressPercentNote)
        tipAmountTextView = view.findViewById(R.id.tipAmountNote)
        totalAmountTextView = view.findViewById(R.id.totalAmountNote)
        retrieveAndDisplayData()
        return view
    }

    private fun retrieveAndDisplayData() {
        val baseAmount = sharedPreferences.getString(BASE_AMOUNT_KEY, "N/A")
        val tipPercentage = sharedPreferences.getString(TIP_PERCENTAGE_KEY, "N/A")
        val tipAmount = sharedPreferences.getString(TIP_AMOUNT_KEY, "N/A")
        val totalAmount = sharedPreferences.getString(TOTAL_AMOUNT_KEY, "N/A")

        baseAmountTextView.text = " $baseAmount"
        tipPercentageTextView.text = " $tipPercentage%"
        tipAmountTextView.text = " $tipAmount"
        totalAmountTextView.text = " $totalAmount"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WalletFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WalletFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}

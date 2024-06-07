package com.example.happy

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "TippyFragment"
private const val INITIAL_TIP_PERCENTAGE = 15
private const val PREFS_NAME = "TippyPreferences"
private const val BASE_AMOUNT_KEY = "baseAmount"
private const val TIP_PERCENTAGE_KEY = "tipPercentage"
private const val TIP_AMOUNT_KEY = "tipAmount"
private const val TOTAL_AMOUNT_KEY = "totalAmount"

class TippyFragment : Fragment() {
    private lateinit var happyBaseAmount: EditText
    private lateinit var happyTipProgressbar: SeekBar
    private lateinit var happyTipAmount: TextView
    private lateinit var happyTotalAmount: TextView
    private lateinit var happyProgressPercentage: TextView
    private lateinit var happySaveInfoButton: Button
    private lateinit var happyTipDescription: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tippy, container, false)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        happyBaseAmount = view.findViewById(R.id.baseNumber)
        happyTipProgressbar = view.findViewById(R.id.progressBar)
        happyTipAmount = view.findViewById(R.id.tipAmount)
        happyTotalAmount = view.findViewById(R.id.totalAmount)
        happyProgressPercentage = view.findViewById(R.id.progress)
        happyTipDescription = view.findViewById(R.id.tipDescription)
        happySaveInfoButton = view.findViewById(R.id.saveTipButton)

        happyTipProgressbar.progress = INITIAL_TIP_PERCENTAGE
        happyProgressPercentage.text = "$INITIAL_TIP_PERCENTAGE%"
        updateTipDescription(INITIAL_TIP_PERCENTAGE)

        happyTipProgressbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "progressBarChanging $progress")
                happyProgressPercentage.text = "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        happyBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "baseAmount $s")
                computeTipAndTotal()
            }
        })

        happySaveInfoButton.setOnClickListener {
            if (happyBaseAmount.text.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter the base amount", Toast.LENGTH_SHORT).show()
            } else {
                saveTipInfo()
                Toast.makeText(requireContext(), "Information saved", Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.TOP, 0, 0)
                }.show()
                navigateToWalletFragment()
            }
        }


        return view
    }

    @SuppressLint("RestrictedApi")
    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when (tipPercent) {
            in 0..12 -> "Poor"
            in 13..23 -> "Acceptable"
            in 24..34 -> "Good"
            in 35..50 -> "Great"
            else -> "Amazing"
        }
        happyTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat() / happyTipProgressbar.max,
            ContextCompat.getColor(requireContext(), R.color.tintRed),
            ContextCompat.getColor(requireContext(), R.color.smoothGreen)
        ) as Int
        happyTipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if (happyBaseAmount.text.isEmpty()) {
            happyTipAmount.text = ""
            happyTotalAmount.text = ""
            return
        }
        val baseTipAmount = happyBaseAmount.text.toString().toDouble()
        val tipPercentage = happyTipProgressbar.progress
        val tipAmount = baseTipAmount * tipPercentage / 100
        val totalAmount = tipAmount + baseTipAmount
        happyTipAmount.text = "%.2f".format(tipAmount)
        happyTotalAmount.text = "%.2f".format(totalAmount)
    }

    private fun saveTipInfo() {
        val baseAmount = happyBaseAmount.text.toString()
        val tipPercentage = happyTipProgressbar.progress.toString()
        val tipAmount = happyTipAmount.text.toString()
        val totalAmount = happyTotalAmount.text.toString()

        val editor = sharedPreferences.edit()
        editor.putString(BASE_AMOUNT_KEY, baseAmount)
        editor.putString(TIP_PERCENTAGE_KEY, tipPercentage)
        editor.putString(TIP_AMOUNT_KEY, tipAmount)
        editor.putString(TOTAL_AMOUNT_KEY, totalAmount)
        editor.apply()

        Log.i(TAG, "Tip info saved: baseAmount=$baseAmount, tipPercentage=$tipPercentage, tipAmount=$tipAmount, totalAmount=$totalAmount")
    }
    private fun navigateToWalletFragment() {
        // Navigate to WalletFragment
        findNavController().navigate(R.id.action_happy_tippy_item_to_happy_wallet_item)

        // Update bottom navigation to highlight the wallet icon
        val navView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navView.selectedItemId = R.id.happy_wallet_item
    }
}



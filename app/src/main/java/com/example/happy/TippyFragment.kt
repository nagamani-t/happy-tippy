package com.example.happy

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator

private const val TAG = "TippyFragment"
private const val INITIAL_TIP_PERCENTAGE = 15

class TippyFragment : Fragment() {
    private lateinit var happyBaseAmount: EditText
    private lateinit var happyTipProgressbar: SeekBar
    private lateinit var happyTipAmount: TextView
    private lateinit var happyTotalAmount: TextView
    private lateinit var happyProgressPercentage: TextView
    private lateinit var happyTipDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tippy, container, false)
        happyBaseAmount = view.findViewById(R.id.baseNumber)
        happyTipProgressbar = view.findViewById(R.id.progressBar)
        happyTipAmount = view.findViewById(R.id.tipAmount)
        happyTotalAmount = view.findViewById(R.id.totalAmount)
        happyProgressPercentage = view.findViewById(R.id.progress)
        happyTipDescription = view.findViewById(R.id.tipDescription)

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

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        happyBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "baseAmount $s")
                computeTipAndTotal()
            }
        })

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
}

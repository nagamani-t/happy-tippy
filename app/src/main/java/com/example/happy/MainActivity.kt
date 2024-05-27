package com.example.happy

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator

private const val TAG = "MAINACTIVITY"
private const val INITAIL_TIP_PERCENTAGE = 15

class MainActivity : AppCompatActivity() {
    private lateinit var happyBaseAmount: EditText
    private lateinit var happyTipProgressbar: SeekBar
    private lateinit var happytipAmount: TextView
    private lateinit var happytotalAmount: TextView
    private lateinit var happyProgressPercentage: TextView
    private lateinit var happyTipDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        happyBaseAmount = findViewById(R.id.baseNumber)
        happyTipProgressbar = findViewById(R.id.progressBar)
        happytipAmount = findViewById(R.id.tipAmount)
        happytotalAmount = findViewById(R.id.totalAmount)
        happyProgressPercentage = findViewById(R.id.progress)
        happyTipDescription = findViewById(R.id.tipDescription)
        happyTipProgressbar.progress = INITAIL_TIP_PERCENTAGE
        happyProgressPercentage.text = "$INITAIL_TIP_PERCENTAGE%"
        updateTipDescription(INITAIL_TIP_PERCENTAGE)
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "baseAmount $s")
                computeTipAndTotal()
            }
        })

    }

    @SuppressLint("RestrictedApi")
    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when(tipPercent){
            in 0..12->"Poor"
            in 13..23->"Acceptable"
            in 24..34->"Good"
            in 35..50->"Great"
            else->"Amazing"
        }
        happyTipDescription.text = tipDescription
//UPDATE THE COLOR BASED ON THE TIP PERCENTAGE
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat() / happyTipProgressbar.max,
            ContextCompat.getColor(this,R.color.tintRed),
            ContextCompat.getColor(this,R.color.smoothGreen)
        )as Int
        happyTipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(happyBaseAmount.text.isEmpty()){
            happytipAmount.text =""
            happytotalAmount.text = ""
            return
        }
        //1.Get the value of base and percentage
       // declaring the variables
        val baseTipAmount = happyBaseAmount.text.toString().toDouble()
        val  TipPercentage = happyTipProgressbar.progress
        // 2. compute the tip and total
        val tipAmount = baseTipAmount * TipPercentage/100
        val totalAmount = tipAmount+ baseTipAmount
        //3.update the UI
        happytipAmount.text = "%.2f".format(tipAmount)
        happytotalAmount.text = "%.2f".format(totalAmount)
    }
}
package com.example.calculator

import android.icu.number.NumberFormatter.TrailingZeroDisplay
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var isClear = true
        var last = ""
        var operation = ""

            fun onNumberButtonClicked(number: String) {
                if (isClear) {
                    findViewById<TextView>(R.id.textNumber).text = number
                    isClear = false
                } else {
                    findViewById<TextView>(R.id.textNumber).append(number)
                }
            }

            fun onDotButtonClicked() {
                if (isClear) {
                    findViewById<TextView>(R.id.textNumber).text = "."
                    isClear = false
                } else {
                    findViewById<TextView>(R.id.textNumber).append(".")
                }
            }

            fun onOperationButtonClicked(op: String) {
                last = findViewById<TextView>(R.id.textNumber).text.toString()
                isClear = true
                operation = op
            }

             fun onClearButtonClicked() {
                findViewById<TextView>(R.id.textNumber).text = "0"
                isClear = true
                last = ""
                operation = ""
            }
            fun processCalculation(operand: String, before: String, current: String): Float {
                try {
                    var ret = before.toFloat()
                    var second = current.toFloat()

                    when (operand) {
                        "+" -> ret += second
                        "-" -> ret -= second
                        "*" -> ret *= second
                        "/" -> ret /= second
                    }

                    return ret
                }
                catch (e: Exception) {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT)
                    return 0F
                }

            }

             fun onEqualsButtonClicked() {
                val result = processCalculation(operation, last, findViewById<TextView>(R.id.textNumber).text.toString())
                findViewById<TextView>(R.id.textNumber).text = result.toString()
                isClear = true
            }



            // Assign click listeners to buttons
            findViewById<Button>(R.id.btn1).setOnClickListener { onNumberButtonClicked("1") }
            findViewById<Button>(R.id.btn2).setOnClickListener { onNumberButtonClicked("2") }
            findViewById<Button>(R.id.btn3).setOnClickListener { onNumberButtonClicked("3") }
            findViewById<Button>(R.id.btn4).setOnClickListener { onNumberButtonClicked("4") }
            findViewById<Button>(R.id.btn5).setOnClickListener { onNumberButtonClicked("5") }
            findViewById<Button>(R.id.btn6).setOnClickListener { onNumberButtonClicked("6") }
            findViewById<Button>(R.id.btn7).setOnClickListener { onNumberButtonClicked("7") }
            findViewById<Button>(R.id.btn8).setOnClickListener { onNumberButtonClicked("8") }
            findViewById<Button>(R.id.btn9).setOnClickListener { onNumberButtonClicked("9") }
            findViewById<Button>(R.id.btn0).setOnClickListener { onNumberButtonClicked("0") }
            findViewById<Button>(R.id.btnDot).setOnClickListener { onDotButtonClicked() }
            findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperationButtonClicked("+") }
            findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperationButtonClicked("-") }
            findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperationButtonClicked("*") }
            findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperationButtonClicked("/") }
            findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsButtonClicked() }
        }







    }

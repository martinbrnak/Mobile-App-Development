package com.example.simplecalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val operand = ""

        val spinner: Spinner = findViewById<Spinner>(R.id.spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.operations_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        fun onCalculateClicked() {
                val num1Text = findViewById<EditText>(R.id.textNumber2).text.toString()
                val num2Text = findViewById<EditText>(R.id.textNumber1).text.toString()
                val operand = findViewById<Spinner>(R.id.spinner).selectedItem.toString()

                // Convert num1 and num2 to floats
                val num1 = num1Text.toFloatOrNull()
                val num2 = num2Text.toFloatOrNull()

                if (num1 == null || num2 == null) {
                    // Handle invalid input
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                    return
                }

                try {
                    when (operand) {
                        "Add ( + )" -> findViewById<TextView>(R.id.textResult).text = (num1 + num2).toString()
                        "Subtract ( - )" -> findViewById<TextView>(R.id.textResult).text = (num1 - num2).toString()
                        "Multiply ( * )" -> findViewById<TextView>(R.id.textResult).text = (num1 * num2).toString()
                        "Divide ( / )" -> {
                            if (num2 == 0f) {
                                // Handle division by zero
                                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT)
                                    .show()
                                return
                            }
                            findViewById<TextView>(R.id.textResult).text = (num1 / num2).toString()
                        }

                        "Remainder ( % )" -> {
                            if (num2 == 0f) {
                                // Handle division by zero
                                Toast.makeText(
                                    this,
                                    "Cannot calculate remainder with divisor zero",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return
                            }
                            findViewById<TextView>(R.id.textResult).text = (num1 % num2).toString()

                        }

                        else -> {
                            // Handle unsupported operation
                            Toast.makeText(this, "Unsupported operation", Toast.LENGTH_SHORT).show()
                            return
                        }

                    }
                }
                    catch(e: Exception){
                        Toast.makeText(this, "Something went wrong, please put in valid numbers into all slots", Toast.LENGTH_SHORT).show()
                        return
                    }



            }





        findViewById<Button>(R.id.calculate).setOnClickListener { onCalculateClicked() }

        //implementation taken from https://developer.android.com/develop/ui/views/components/spinner


    }
}
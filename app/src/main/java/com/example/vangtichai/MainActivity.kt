package com.example.vangtichai

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentAmount = ""

    private lateinit var takaText: TextView

    private lateinit var count500: TextView
    private lateinit var count100: TextView
    private lateinit var count50: TextView
    private lateinit var count20: TextView
    private lateinit var count10: TextView
    private lateinit var count5: TextView
    private lateinit var count2: TextView
    private lateinit var count1: TextView

    private val notes = intArrayOf(500, 100, 50, 20, 10, 5, 2, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentAmount = savedInstanceState?.getString("amount") ?: ""

        takaText = findViewById(R.id.takaText)

        count500 = findViewById(R.id.count500)
        count100 = findViewById(R.id.count100)
        count50 = findViewById(R.id.count50)
        count20 = findViewById(R.id.count20)
        count10 = findViewById(R.id.count10)
        count5 = findViewById(R.id.count5)
        count2 = findViewById(R.id.count2)
        count1 = findViewById(R.id.count1)

        val digitButtons = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (buttonId in digitButtons) {
            val button: Button = findViewById(buttonId)
            button.setOnClickListener {
                addDigit(button.text.toString())
            }
        }

        val clearButton: Button = findViewById(R.id.btnClear)
        clearButton.setOnClickListener {
            currentAmount = ""
            updateScreen()
        }

        updateScreen()
    }

    private fun addDigit(digit: String) {
        if (currentAmount.length < 12) {
            currentAmount += digit
        }

        updateScreen()
    }

    private fun updateScreen() {
        val amount = currentAmount.toLongOrNull() ?: 0L

        takaText.text = if (currentAmount.isEmpty()) {
            "Taka:"
        } else {
            "Taka: $currentAmount"
        }

        val result = calculateChange(amount)

        count500.text = result[500].toString()
        count100.text = result[100].toString()
        count50.text = result[50].toString()
        count20.text = result[20].toString()
        count10.text = result[10].toString()
        count5.text = result[5].toString()
        count2.text = result[2].toString()
        count1.text = result[1].toString()
    }

    private fun calculateChange(amount: Long): Map<Int, Long> {
        var remainingAmount = amount
        val result = mutableMapOf<Int, Long>()

        for (note in notes) {
            result[note] = remainingAmount / note
            remainingAmount %= note
        }

        return result
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("amount", currentAmount)
    }
}
package com.example.dateofbirthcalcualtor

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var staticSelectedDate: TextView? = null
    private var ageInMinutes: TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton: Button = findViewById(R.id.datePickButton)
        staticSelectedDate = findViewById(R.id.staticSelectedDate)
        ageInMinutes = findViewById(R.id.ageInMinutes)

        myButton.setOnClickListener {
            clickOnDate()
        }
    }

    private fun clickOnDate() {

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                Toast.makeText(
                    this, "Successful Operation",
                    Toast.LENGTH_LONG,
                ).show()

                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                staticSelectedDate?.text = selectedDate
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val actualDate = simpleDateFormat.parse(selectedDate)
                actualDate?.let {
                    val selectedDateInMinutes = actualDate.time / 60000
                    val currentDate =
                        simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        ageInMinutes?.text = differenceInMinutes.toString()
                    }

                }

            },
            year, month, day,
        )

        picker.datePicker.maxDate = System.currentTimeMillis() - 86_000_000
        picker.show()
    }
}
package com.example.buycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CarDetails : AppCompatActivity() {
    private lateinit var carDbHelper: CarDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        carDbHelper = CarDbHelper(this)

        val carId = intent.getLongExtra("car_id", -1)
        if (carId != -1L) {
            val car = carDbHelper.getCarById(carId)
            if (car != null) {
                // Update UI with car details
                val titleTextView = findViewById<TextView>(R.id.titleTextView)
                val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
                val priceTextView = findViewById<TextView>(R.id.priceTextView)
                // ... Bind other views with car details

                titleTextView.text = car.title
                descriptionTextView.text = car.description
                priceTextView.text = car.price.toString()
            }
        }
    }
}
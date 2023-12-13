package com.example.buycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buycar.databinding.ActivityCarListBinding
import com.example.buycar.databinding.ActivityLoginBinding

class CarList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var carAdapter: CarAdapter
    private lateinit var binding: ActivityCarListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)
        binding = ActivityCarListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        carAdapter = CarAdapter()
        recyclerView.adapter = carAdapter

        // Replace this with your method to fetch the list of cars from the database
        val cars = getActualCarData()
        carAdapter.setData(cars)
    }

    private fun getActualCarData(): List<CarDbHelper.Car> {
        val dbHelper = CarDbHelper(this)
        return dbHelper.getAllCars()
    }
}
package com.example.buycar

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private var carList = mutableListOf<CarDbHelper.Car>()

    fun setData(cars: List<CarDbHelper.Car>) {
        carList.clear()
        carList.addAll(cars)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = carList[position]
        holder.bind(car)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(car: CarDbHelper.Car) {
            itemView.findViewById<TextView>(R.id.titleTextView).text = car.title
            itemView.findViewById<TextView>(R.id.priceTextView).text = car.price.toString()
            Glide.with(itemView.context).load(Uri.parse(car.photoUri)).into(itemView.findViewById(R.id.carImageView))

            // Set OnClickListener on the card item
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, CarDetails::class.java)
                intent.putExtra("car_id", car.id) // Pass the car ID to the detail activity
                context.startActivity(intent)
            }
        }
    }

}


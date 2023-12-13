package com.example.buycar

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.buycar.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val imageList = listOf(
        R.drawable._x0,
        R.drawable.car,
        R.drawable.amg,
        R.drawable.lamb,
        R.drawable.lamb
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val dotsLayout: LinearLayout = findViewById(R.id.dotsLayout)

        viewPager.adapter = ImagePagerAdapter(imageList)


        // Add the dots to the dots indicator
        for (i in 0 until imageList.size) {
            val dotView = ImageView(this)
            dotView.setImageResource(R.drawable.dot_item)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dotsLayout.addView(dotView, params)
        }

        // Set up the dots indicator listener
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDotsIndicator(position)
            }
        })
    }

    private fun updateDotsIndicator(currentPosition: Int) {
        val dotsLayout: LinearLayout = findViewById(R.id.dotsLayout)
        for (i in 0 until dotsLayout.childCount) {
            val dotView = dotsLayout.getChildAt(i) as ImageView
            dotView.setImageResource(
                if (i == currentPosition) R.drawable.selected_dot_item
                else R.drawable.dot_item
            )
        }
    }

}
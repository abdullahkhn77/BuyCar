package com.example.buycar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsedCars.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsedCars : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_used_cars, container, false)

        val viewPager: ViewPager2 = rootView.findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerFragmentAdapter(this)

        val tabLayout: TabLayout = rootView.findViewById(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Category"
                1 -> tab.text = "Budget"
                2 -> tab.text = "Brand"
                3 -> tab.text = "Model"
                4 -> tab.text = "Cities"
            }
        }.attach()

        val bottomNavigationView : BottomNavigationView = rootView.findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sell -> {
                    // Handle Home item click
                    val intent = Intent(requireContext(), SellActivity::class.java)
                    startActivity(intent)
                    true
                }


            R.id.home -> {
            // Handle Search item click
            true
        }
            else -> {
            // Handle unrecognized menu item clicks here
            false
        }
        }

        }




        return rootView
    }
    class CategoryFragment : Fragment(R.layout.fragment_category)
    class BudgetFragment : Fragment(R.layout.fragment_budget)
    class BrandFragment : Fragment(R.layout.fragment_brand)
    class CitiesFragment : Fragment(R.layout.fragment_cities)

    inner class ViewPagerFragmentAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 4 // Number of tabs

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CategoryFragment()
                1 -> BudgetFragment()
                2 -> BrandFragment()
                3 -> CitiesFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }

    }
    private fun showAboveFragment() {
        // Replace the existing fragment with AboveFragment
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, AboveFragment())
        transaction.addToBackStack(null) // Optional: Allows back navigation
        transaction.commit()
    }
    class AboveFragment : Fragment(R.layout.fragment_category) {

    }
}
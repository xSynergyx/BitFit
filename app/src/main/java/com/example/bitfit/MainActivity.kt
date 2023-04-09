package com.example.bitfit


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bitfit.Fragments.DashboardFragment
import com.example.bitfit.Fragments.LogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logFragment: Fragment = LogFragment()
        val dashboardFragment: Fragment = DashboardFragment()

        // Default fragment displayed
        replaceFragment(logFragment)

        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottom_navbar)
        bottomNavBar.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.bottom_nav_log -> fragment = logFragment
                R.id.bottom_nav_dashboard -> fragment = dashboardFragment
            }
            replaceFragment(fragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
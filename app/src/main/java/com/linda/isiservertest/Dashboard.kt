package com.linda.isiservertest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {
    lateinit var bottomNavView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        bottomNavView= findViewById(R.id.bottom_navigation)

        bottomNavView.selectedItemId = R.id.nav_requests
        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_categories -> category()
                R.id.nav_requests -> Dashboard()
                R.id.Profile -> ProfileE()
                R.id.Info -> InfoI()
            }
            true
        }

    }
    //Buttom navBar manipulation
    private fun category(){
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }
    private fun ProfileE(){
        val intent = Intent(this, ProfileEActivity::class.java)
        startActivity(intent)
    }
    private fun InfoI(){
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }
    private fun Dashboard(){
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
    }
}
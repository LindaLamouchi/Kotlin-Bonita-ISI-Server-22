package com.linda.isiservertest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.linda.isiservertest.SessionManager.SessionManagement
import kotlinx.android.synthetic.main.activity_profile_e.*

class ProfileEActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_e)
        bottomNavView= findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId = R.id.Profile
        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_categories -> category()
                R.id.nav_requests -> Dashboard()
                R.id.Profile -> ProfileE()
                R.id.Info -> InfoI()
            }
            true
        }

        val mypreference = getSharedPreferences("Shared_Pref_Data", Context.MODE_PRIVATE)

        var UserId = mypreference.getString("User", "none").toString()
        userIdentifier.setText("Your Identifier : "+UserId)

        logout.setOnClickListener {
            val mypreference = SessionManagement(this)
            mypreference.setLogin(" ") //saving the token
            mypreference.setSessionID(" ")
           var i=Intent(this, MainActivity::class.java)
            startActivity(i)
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
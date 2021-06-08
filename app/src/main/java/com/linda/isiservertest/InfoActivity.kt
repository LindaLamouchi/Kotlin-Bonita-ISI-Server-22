package com.linda.isiservertest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    lateinit var bottomNavView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        bottomNavView= findViewById(R.id.bottom_navigation)
        bottomNavView.selectedItemId = R.id.Info
        bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_categories -> category()
                R.id.nav_requests -> Dashboard()
                R.id.Profile -> ProfileE()
                R.id.Info -> InfoI()
            }
            true
        }
        linktoIsi.setOnClickListener {
            var u:Uri
            u=Uri.parse("http://www.isi.rnu.tn/")
           var i:Intent
           i=Intent(Intent.ACTION_VIEW,u)
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
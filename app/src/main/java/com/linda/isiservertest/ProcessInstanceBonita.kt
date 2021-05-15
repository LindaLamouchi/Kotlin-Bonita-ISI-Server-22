package com.linda.isiservertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProcessInstanceBonita : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_instance_bonita)

        var intent=intent
        val name=intent.getStringExtra("name")
        val  id=intent.getStringExtra("id")
        val processname= findViewById<TextView>(R.id.ProcessName)

        processname.text=name
    }
}
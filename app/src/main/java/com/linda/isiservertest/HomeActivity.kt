package com.linda.isiservertest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.linda.isiservertest.adapter.MyAdapter
import com.linda.isiservertest.model.ProcessBonita
import com.linda.isiservertest.repository.Repository
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(),MyAdapter.OnItemClickListener {
    private lateinit var viewModel: MainViewModel
    private val myAdapter by lazy { MyAdapter(this) }
    lateinit var sessionId:String
    lateinit var token:String
    lateinit var listP:List<ProcessBonita>
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val mypreference=getSharedPreferences("Shared_Pref_Data", Context.MODE_PRIVATE)
         sessionId = mypreference.getString("Session","none").toString()
         token= mypreference.getString("Token","none").toString()

        System.out.println("this is the Home page :  Saved sessionId and Cookie in shared preferences:"+sessionId+" "+token)



        val policy= StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
       //val map : HashMap<String,String> = HashMap<String,String>(1)
        val cooo:String
        if (sessionId != null) {

          // map["Cookie"]=
               cooo="JSESSIONID=$sessionId;X-Bonita-API-Token=$token; bonita.tenant=1; BOS_Locale=en"

            viewModel.getCustomProcess(cooo)
            viewModel.myCustomPosts2.observe(this, Observer {response ->
                if(response.isSuccessful){
                    response.body()?.let { myAdapter.setData(it) }
                    listP= response.body()!!
                    Log.d("Main", response.body().toString())

                }else {
                    Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                }

            })
        }

    }

    override fun onItemClick(position: Int) {
        val liste:List<ProcessBonita> =listP
        val ClickedItem=liste[position]
        val intent=Intent(this, ProcessInstanceBonita::class.java)
        System.out.println("selected object : "+ClickedItem.name)
        intent.putExtra("Id",ClickedItem.id)
        intent.putExtra("name",ClickedItem.name)
        startActivity(intent)
    }

    private fun setupRecyclerview() {
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
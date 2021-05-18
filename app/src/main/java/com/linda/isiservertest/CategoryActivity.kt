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
import com.linda.isiservertest.adapter.CategoryAdapter
import com.linda.isiservertest.adapter.MyAdapter
import com.linda.isiservertest.model.Category
import com.linda.isiservertest.model.ProcessBonita
import com.linda.isiservertest.repository.Repository
import kotlinx.android.synthetic.main.activity_categorie.*
import kotlinx.android.synthetic.main.activity_home.*

class CategoryActivity : AppCompatActivity() , CategoryAdapter.OnItemClickListener {

    private lateinit var viewModel: MainViewModel
    private val categoryAdapter by lazy { CategoryAdapter(this) }
    lateinit var sessionId: String
    lateinit var token: String
    lateinit var UserId: String
    lateinit var listP: List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorie)
        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val mypreference = getSharedPreferences("Shared_Pref_Data", Context.MODE_PRIVATE)
        sessionId = mypreference.getString("Session", "none").toString()
        token = mypreference.getString("Token", "none").toString()
        UserId =mypreference.getString("User","none").toString()
        System.out.println("this is the Home page :  Saved sessionId, Cookie, User_Id in shared preferences:" + sessionId + " " + token+" "+UserId)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val cooo: String
        if (sessionId != null) {


            cooo = "JSESSIONID=$sessionId;X-Bonita-API-Token=$token; bonita.tenant=1; BOS_Locale=en"

            viewModel.getCategoryList(cooo,0)

            viewModel.CategoryList.observe(this, Observer {response ->

                if(response.isSuccessful){
                    response.body()?.let { categoryAdapter.setData(it)
                        listP = response.body()!!
                        Log.d("Main", response.body().toString())
                    }
                }else{
                    Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                    Log.d("error",response.message())
                }
            })
        }

    }

    override fun onItemClick(position: Int) {
        val liste: List<Category> = listP
        val ClickedItem = liste[position]
        //val intent = Intent(this, ProcessInstanceBonita::class.java)

        val intent = Intent (this,HomeActivity::class.java)
        System.out.println("selected category  : " + ClickedItem.name)

        intent.putExtra("IdC", ClickedItem.id)
        intent.putExtra("nameC", ClickedItem.name)
        startActivity(intent)
    }
    private fun setupRecyclerview() {
        recyclerView0.adapter = categoryAdapter
        recyclerView0.layoutManager = LinearLayoutManager(this)
    }
}
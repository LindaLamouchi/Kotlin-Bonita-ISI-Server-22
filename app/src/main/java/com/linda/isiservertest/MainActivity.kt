package com.linda.isiservertest

import android.app.ProgressDialog
import android.app.ProgressDialog.*
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.linda.isiservertest.SessionManager.SessionManagement
import com.linda.isiservertest.adapter.MyAdapter
import com.linda.isiservertest.model.User
import com.linda.isiservertest.repository.Repository
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
   // private val myAdapter by lazy { MyAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val b1= findViewById<Button>(R.id.button)
        val username=findViewById<EditText>(R.id.username)
        val pass=findViewById<EditText>(R.id.password)

       var token:String
       var sessin:String



        b1.setOnClickListener{
            System.out.println(username.getText().toString()+" "+pass.getText().toString())
            val SDK_INT = Build.VERSION.SDK_INT
            if (SDK_INT > 8) {
                val policy= StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)


                //val myPost=User("etud1.l3sil","bpm");
                //viewModel.pushPost2("etud1.l3sil","bpm")

                viewModel.pushPost2(username.getText().toString(),pass.getText().toString())
                    
                // viewModel.getPost("11112222")
                viewModel.myResponse.observe(this, Observer { response ->
                    if(response.isSuccessful){
                        Log.d("Main", response.body().toString())
                        Log.d("Main", response.code().toString())
                        //Log.d("Main", response.headers().toString())
                        val s=response.headers().toString()

                        System.out.println("this is s:"+s);
                        token=extractInfoCookie(s)
                        System.out.println("Extracted Token :$token");
                        sessin=extractInfoSessionId(s)
                        System.out.println("Extracted SessionId :$sessin");


                        val mypreference=SessionManagement(this)
                        mypreference.setLogin(token) //saving the token
                        mypreference.setSessionID(sessin) //saving the session Id

                        val cooo="JSESSIONID=$sessin;X-Bonita-API-Token=$token; bonita.tenant=1; BOS_Locale=en"

                        System.out.println("In login Interface : user Id:"+cooo);
                        viewModel.getUserCredential(cooo)
                        viewModel.UserCred.observe(this, Observer { response ->

                            if(response.isSuccessful){
                                Log.d("UserID : ",response.body()!!.user_id)
                                val userId=response.body()!!.user_id.toString()
                                mypreference.setUserID(userId) //saving user id
                            }
                                else{
                                    Toast.makeText(this,"Not able to get user ID",Toast.LENGTH_SHORT).show()
                            }

                        })
                        System.out.println("In login Interface : Saved sessionId and Cookie in shared preferences:"+mypreference.getSession()+" "+mypreference.getLogin());
                        System.out.println("Saved User Id in shared preference :"+mypreference.getUserID())

                        show(this,"loading..","wait while loading") //Display a spinner
                        startActivity(Intent(this, CategoryActivity::class.java))


                    }else {
                        Toast.makeText(this, "check out your input", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                    }
                })





               /* var client = OkHttpClient().newBuilder()
                    .build()
                var mediaType: MediaType? = "application/x-www-form-urlencoded".toMediaTypeOrNull()
                // var s:String= "username=$user&password=$pass&redirect=false"

                var body: RequestBody = RequestBody.create(
                    mediaType,"username=etud1.l3sil&password=bpm&redirect=false")
                var request: Request = Request.Builder()
                    .url("http://digitalisi.tn:8080/bonita/loginservice")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build()
                var response: Response = client.newCall(request).execute()
                System.out.println(response.headers.toString());*/
        }
    }



}

    private fun extractInfoSessionId(s: String): String {
        val a:List<String>
        a=s.split(";")
        val co:String
        val i:Int=a.get(1).lastIndexOf("=")+1
        co=a.get(1).substring(i)
        return co;
    }

    private fun extractInfoCookie(s: String): String {
        val a:List<String>
        a=s.split(";")
        val co:String
        val i:Int=a.get(4).lastIndexOf("=")+1
        co=a.get(4).substring(i)
        return co;

    }

}
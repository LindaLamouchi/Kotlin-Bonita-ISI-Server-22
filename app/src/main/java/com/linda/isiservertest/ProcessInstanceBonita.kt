package com.linda.isiservertest


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.linda.isiservertest.databinding.ActivityProcessBinding
import com.linda.isiservertest.model.Contract
import com.linda.isiservertest.repository.Repository
import kotlinx.android.synthetic.main.activity_process.view.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class ProcessInstanceBonita : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    var binding: ActivityProcessBinding? = null
    lateinit var sessionId: String
    lateinit var token: String
    lateinit var listC: String


    var form_items: ArrayList<JSONObject>? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityProcessBinding.inflate(layoutInflater)
        setContentView(binding!!.rootLayout)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        var intent = intent

        val name = intent.getStringExtra("name")

        val id = intent.getStringExtra("Id")
        //val processname= findViewById<TextView>(R.id.ProcessName); processname.text=name
        binding!!.ProcessName.text = name


        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val mypreference = getSharedPreferences("Shared_Pref_Data", Context.MODE_PRIVATE)
        sessionId = mypreference.getString("Session", "none").toString()
        token = mypreference.getString("Token", "none").toString()
        val Cookie = "$sessionId;$token"
        System.out.println("this is the ProcessInstance page :  Saved sessionId and Cookie in shared preferences:$sessionId $token")




        Toast.makeText(this, name.toString() + " " + name, Toast.LENGTH_SHORT).show()
        val cookie: String
        if (sessionId != null) {

            cookie ="JSESSIONID=$sessionId;X-Bonita-API-Token=$token; bonita.tenant=1; BOS_Locale=en"
            if (id != null) {
                viewModel.getContracts(cookie, id)
                viewModel.contractsBonita.observe(this, Observer { response ->
                    if (response.isSuccessful) {
                        Log.d("Main", response.body().toString())
                        listC = response.body().toString()
                        System.out.println("Process Contract : " + listC)


                        //Creating a new view here!
                        response.body()?.let {
                            // my personalized view ;
                            var formC = it
                            newForm(formC, name)
                            // a submit Btn
                            var btn = Button(binding!!.root.context)
                            btn.text = "Submit"
                            btn.setTextColor(Color.WHITE)
                            btn.setBackgroundResource(R.drawable.button_shape2)
                            binding!!.editTextLinearLayout.addView(btn)
                            //calling the api
                            btn.setOnClickListener{

                                viewModel.submitForm(cookie,id!!,token,formC.inputs[0].name,binding!!.rootLayout)
                                Log.d("token",token)
                                viewModel.statusSubmit.observe(this@ProcessInstanceBonita, Observer { response ->
                                    Log.d("fail", response.toString())
                                    if(response.isSuccessful){
                                        Log.d("submissionForm", response.body().toString())
                                        Log.d("code", response.code().toString())
                                        Toast.makeText(this, "Form Submited", Toast.LENGTH_SHORT).show()
                                    }else{
                                        Log.d("fail", response.body().toString())
                                        Log.d("fail", response.toString())
                                    }



                                })


                            }




                        }


                    } else {
                        Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                    }

                })
            }


        }


    }

    private fun newForm(cont: Contract, name: String?) {
        var inputs = cont.inputs[0].inputs

        //Log.d("inputs",inputs.toString())
        val ediLinearLayout = binding!!.editTextLinearLayout
        for (inp in inputs) {
            when (inp.type) {
                "TEXT" -> {
                    val param = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    param.setMargins(0, 0, 0, 12)
                    val textView = TextView(this)
                    textView.text = inp.name
                    textView.textSize = 25F
                    textView.setTextColor(Color.BLUE)
                    textView.layoutParams = param
                    val editText = EditText(this)
                    editText.layoutParams = param
                    editText.setBackgroundResource(R.drawable.border_edittxt)
                    editText.hint = inp.name

                    ediLinearLayout?.addView(textView)
                    ediLinearLayout?.addView(editText)

                }
                "BOOLEAN" -> {
                    val checkBox = CheckBox(this)
                    checkBox.text = inp.name
                    checkBox.id = "123".toInt()
                    ediLinearLayout?.addView(checkBox)
                }
                "LOCALDATE" -> {
                    val param = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    param.setMargins(20, 0, 0, 0)

                    val textView = TextView(this)
                    textView.layoutParams = param
                    textView.text = inp.name
                    textView.textSize = 25F
                    textView.setTextColor(Color.BLACK)
                    val btn = Button(this)
                    btn.setHintTextColor(Color.WHITE)
                    btn.setBackgroundResource(R.drawable.button_shape)
                    btn.id = "456".toInt()
                    btn.tag = inp.name
                    ediLinearLayout?.addView(textView, param)
                    ediLinearLayout?.addView(btn, param)

                    //2012-04-23T18:25:43Z datetime

                    var cal = Calendar.getInstance()
                    var dayInt = cal.get(Calendar.DAY_OF_MONTH)
                    var monthInt = cal.get(Calendar.MONTH) + 1
                    var yearInt = cal.get(Calendar.YEAR)

                    var dayConverted = if (dayInt < 10) "0$dayInt" else dayInt.toString()
                    var monthConverted = if (monthInt < 10) "0$monthInt" else monthInt.toString()

                    btn.hint = "$yearInt-$monthConverted-$dayConverted"
                    btn.setOnClickListener {
                        val datePicker = DatePickerDialog(
                            this, { _, year, month, dayOfMonth ->
                                var correctMonth = month + 1
                                dayConverted =
                                    if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                                monthConverted =
                                    if (correctMonth < 10) "0$correctMonth" else correctMonth.toString()
                                btn.hint = "$year-$monthConverted-$dayConverted"
                            },
                            yearInt, monthConverted.toInt(), dayConverted.toInt()

                        )
                        datePicker.show()
                    }

                }
                "OFFSETDATETIME" -> {
                    val lb = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    val textView = TextView(this)
                    textView.layoutParams = lb
                    textView.text = inp.name
                    textView.textSize = 25F
                    textView.setTextColor(Color.BLUE)
                    lb.height = 80

                    lb.setMargins(0, 0, 0, 12)

                    var btn3 = Button(this)
                    btn3.layoutParams = lb
                    btn3.setBackgroundResource(R.drawable.button_shape)

                    btn3.id = "456".toInt()
                    ediLinearLayout?.addView(textView)
                    ediLinearLayout?.addView(btn3)
                    btn3.tag = inp.name
                    var cal = Calendar.getInstance()
                    var dayInt = cal.get(Calendar.DAY_OF_MONTH)
                    var monthInt = cal.get(Calendar.MONTH) + 1
                    var yearInt = cal.get(Calendar.YEAR)
                    var hour = cal.get(Calendar.HOUR)
                    var minute = cal.get(Calendar.MINUTE)


                    var dayConverted = if (dayInt < 10) "0$dayInt" else dayInt.toString()
                    var monthConverted = if (monthInt < 10) "0$monthInt" else monthInt.toString()
                    var hourConverted = if (hour < 10) "0$hour" else hour.toString()
                    var minuteConverted = if (minute < 10) "0$minute" else minute.toString()
                    btn3.hint =
                        "$yearInt-$monthConverted-$dayConverted" + "T" + "$hourConverted:$minuteConverted:00Z"

                    btn3.setOnClickListener {
                        val timePicker = TimePickerDialog(
                            this,
                            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                                hourConverted =
                                    if (hourOfDay < 10) "0$hourOfDay" else hourOfDay.toString()
                                minuteConverted = if (hour < 10) "0$minute" else minute.toString()
                                btn3.hint =
                                    "$yearInt-$monthConverted-$dayConverted" + "T" + "$hourConverted:$minuteConverted:00Z"
                            },
                            hour,
                            minute,
                            true
                        )

                        val datePicker = DatePickerDialog(
                            this, { _, year, month, dayOfMonth ->
                                var correctMonth = month + 1
                                dayConverted =
                                    if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
                                monthConverted =
                                    if (correctMonth < 10) "0$correctMonth" else correctMonth.toString()
                                yearInt = year
                                timePicker.show()


                            },
                            yearInt, monthConverted.toInt(), dayConverted.toInt()

                        )

                        datePicker.show()

                    }
                }
            }

        }


    }
}
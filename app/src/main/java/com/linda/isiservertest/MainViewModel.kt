package com.linda.isiservertest
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linda.isiservertest.api.RetrofitInstance
import com.linda.isiservertest.model.*
import com.linda.isiservertest.repository.Repository
import kotlinx.android.synthetic.main.activity_process.view.*

import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
class MainViewModel(private val repository: Repository): ViewModel() {

    var myResponse: MutableLiveData<Response<User>> = MutableLiveData()
    var UserCred: MutableLiveData<Response<UserCredentials>> = MutableLiveData()
    var CategoryList: MutableLiveData<Response<List<Category>>> = MutableLiveData()


    var myCustomPosts2: MutableLiveData<Response<List<ProcessBonita>>> = MutableLiveData()
    var contractsBonita: MutableLiveData<Response<Contract>> = MutableLiveData()
    var statusSubmit:MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    fun pushPost2(username:String,password : String) {
        viewModelScope.launch {
            val response = repository.pushPost(username,password)
            myResponse.value = response
        }
    }
    /*fun pushPost(post: User) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }

    fun pushPost2(userId: Int, id: Int, title: String, body: String) {
        viewModelScope.launch {
            val response = repository.pushPost2(userId, id, title, body)
            myResponse.value = response
        }
    }

    fun getPost(auth: String){
        viewModelScope.launch {
            val response = repository.getPost(auth)
            myResponse.value = response
        }
    }
    fun getPost2(number: Int) {
        viewModelScope.launch {
            val response = repository.getPost2(number)
            myResponse2.value = response
        }
    }
    fun getCustomPosts(userId: Int, sort: String, order: String) {
        viewModelScope.launch {
            val response = repository.getCustomPosts(userId, sort, order)
            myCustomPosts.value = response
        }
    }

    fun getCustomPosts2(userId: Int, options: Map<String, String>) {
        viewModelScope.launch {
            val response = repository.getCustomPosts2(userId, options)
            myCustomPosts2.value = response
        }
    }*/

    fun getUserCredential(Cookie: String){
        viewModelScope.launch {
            val response=repository.getUserCredentials(Cookie)
            UserCred.value = response
        }
    }
    fun getContracts(Cookie : String , processId : String) {
        viewModelScope.launch {

            val response = repository.getProcessDetails(Cookie, processId)
            contractsBonita.value = response

        }

    }
    fun getCategoryList(Cookie:String,p:Int){
        viewModelScope.launch {

            val response = repository.getCategorylist(Cookie, p)
            CategoryList.value = response

        }
    }

    fun getCustomProcess(map:String,UserId:String,CategoryId:String){

        viewModelScope.launch {
            val response = repository.getCustomProcess(map,0,100, "user_id=$UserId","categoryId=$CategoryId")
            myCustomPosts2.value = response
        }
    }
     fun submitForm(userCookie : String,processId: String,bonitaToken : String,contractName : String,v :View) {
         viewModelScope.launch {
             var wholeObj = JSONObject()
             var data = JSONObject()
             var count = v.editTextLinearLayout.childCount

             for (i in 0 until count - 1) {
                 var view: View = v.editTextLinearLayout.getChildAt(i)
                 if ((view is EditText)) {
                     data.put(view.hint.toString(), view.text.toString())
                     Log.d("elements", view.text.toString() + " " + view.hint.toString())

                 } else if (view is Button && view.id === 456) {
                     data.put(view.tag.toString(), view.hint.toString())
                     Log.d("date", view.hint.toString() + " " + view.tag.toString())

                 } else if (view is CheckBox && view.id === 123) {
                     data.put(view.text.toString(), view.isEnabled.toString())
                     Log.d("checkbox", view.isEnabled.toString() + " " + view.text)

                 }

             }
             wholeObj.put(contractName, data)
             val obj = wholeObj.toString()
             val requestBody: RequestBody =
                 RequestBody.create("application/json".toMediaTypeOrNull(), obj)


             var response = repository.submitb( bonitaToken,userCookie, processId, requestBody)

             Log.d("viewmodel", obj)
             statusSubmit.postValue(response)
         }




    }


}
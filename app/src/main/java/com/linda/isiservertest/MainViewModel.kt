package com.linda.isiservertest
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linda.isiservertest.model.ProcessBonita
import com.linda.isiservertest.model.User
import com.linda.isiservertest.repository.Repository

import kotlinx.coroutines.launch
import retrofit2.Response
class MainViewModel(private val repository: Repository): ViewModel() {

    var myResponse: MutableLiveData<Response<User>> = MutableLiveData()
    var myResponse2: MutableLiveData<Response<User>> = MutableLiveData()
    var myCustomPosts: MutableLiveData<Response<List<User>>> = MutableLiveData()


    var myCustomPosts2: MutableLiveData<Response<List<ProcessBonita>>> = MutableLiveData()


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

    fun getCustomProcess(
    map:String
    //map: Map<String,String>
    ){

        viewModelScope.launch {
            val response = repository.getCustomProcess(map)
           System.out.println("this is the Model page :  Saved sessionId and Cookie in shared preferences: "+map)

            myCustomPosts2.value = response
        }
    }
}
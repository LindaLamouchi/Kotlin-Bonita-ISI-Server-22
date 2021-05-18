package com.linda.isiservertest.repository

import com.linda.isiservertest.api.RetrofitInstance
import com.linda.isiservertest.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Query

class Repository {
   /* suspend fun getPost(auth: String): Response<User> {
        return RetrofitInstance.api.getPost(auth)
    }

    suspend fun getPost2(number: Int): Response<User> {
        return RetrofitInstance.api.getPost2(number)
    }

    suspend fun getCustomPosts(userId: Int, sort: String, order: String): Response<List<User>> {
        return RetrofitInstance.api.getCustomPosts(userId, sort, order)
    }

    suspend fun getCustomPosts2(userId: Int, options: Map<String, String>): Response<List<User>> {
        return RetrofitInstance.api.getCustomPosts2(userId, options)
    }

    suspend fun pushPost(post: Post): Response<User> {
        return RetrofitInstance.api.pushPost(post)
    }


*/
    //User Credentials
    suspend fun getUserCredentials(Cookie: String):Response<UserCredentials>{
        return RetrofitInstance.api.getUserCredentials(Cookie)
    }
    //contract
   suspend fun getProcessDetails( Cookie:String, processId:String): Response<Contract> {
       return RetrofitInstance.api.getProcessContract(Cookie,processId)
   }
    //Submit Form

    suspend fun submitb(  token : String ,Cookie: String, processN: String , data : RequestBody) : Response<ResponseBody>{
      return RetrofitInstance.api.submitForm(token,Cookie , processN, data)
    }
    //proc list
    suspend fun getCustomProcess( coo:String,p:Int,c:Int,UserId:String,CategoryId:String
    ): Response<List<ProcessBonita>> {
        return RetrofitInstance.api.getCustomProcess(coo,p, c, UserId, CategoryId)
    }

    //login
    suspend fun pushPost(username: String, password: String): Response<User> {
       return RetrofitInstance.api.pushPost(username, password)
   }

    suspend fun getCategorylist(cookie: String, p: Int): Response<List<Category>> {
        return RetrofitInstance.api.getCategories(cookie,p)
    }
}
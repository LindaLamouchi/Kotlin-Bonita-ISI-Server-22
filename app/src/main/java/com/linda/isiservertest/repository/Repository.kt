package com.linda.isiservertest.repository

import com.linda.isiservertest.api.RetrofitInstance
import com.linda.isiservertest.model.ProcessBonita
import com.linda.isiservertest.model.User
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
   suspend fun getCustomProcess( coo:String
      // map: Map<String,String>
   ): Response<List<ProcessBonita>> {
       return RetrofitInstance.api.getCustomProcess(coo)
   }

    suspend fun pushPost(username: String, password: String): Response<User> {
       return RetrofitInstance.api.pushPost(username, password)
   }
}
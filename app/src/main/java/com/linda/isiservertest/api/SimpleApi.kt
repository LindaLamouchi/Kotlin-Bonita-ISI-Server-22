package com.linda.isiservertest.api

import com.linda.isiservertest.model.*
import retrofit2.Response
import retrofit2.http.*
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface SimpleApi {
    /* @GET("posts/1")
     suspend fun getPost(@Header("Auth") auth: String): Response<Post>

     @GET("posts/{postNumber}")
     suspend fun getPost2(
         @Path("postNumber") number: Int
     ): Response<Post>

     @GET("posts")
     suspend fun getCustomPosts(
         @Query("userId") userId: Int,
         @Query("_sort") sort: String,
         @Query("_order") order: String
     ): Response<List<Post>>

     @GET("posts")
     suspend fun getCustomPosts2(
         @Query("userId") userId: Int,
         @QueryMap options: Map<String, String>
     ): Response<List<Post>>

     @POST("posts")
     suspend fun pushPost(
         @Body post: Post
     ): Response<Post>

     @FormUrlEncoded
     @POST("posts")
     suspend fun pushPost2(
         @Field("userId") userId: Int,
         @Field("id") id: Int,
         @Field("title") title: String,
         @Field("body") body: String
     ): Response<Post>
 */
    @FormUrlEncoded
    @POST("loginservice")
    suspend fun pushPost(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<User>


    @GET("API/system/session/unusedId")
    suspend fun getUserCredentials(
            @Header("Cookie") Cookie : String
    ) : Response<UserCredentials>

    @GET("API/bpm/process")
    suspend fun getCustomProcess(
        @Header("Cookie") cookie: String,
        @Query("p") p : Int=0,
        @Query("c") c : Int=100,
        @Query("f") userId : String,
        @Query("f") Category:String

    ): Response<List<ProcessBonita>>

    @GET("API/bpm/process/{processId}/contract")
    suspend fun getProcessContract(
        @Header("Cookie") Cookie: String,
        @Path("processId") processId: String
    ): Response<Contract>

    @Headers("Content-Type: application/json")
    @POST("API/bpm/process/{processId}/instantiation")
    suspend fun submitForm(
        @Header("X-Bonita-API-Token") BonitaToken: String,
        @Header("Cookie") userCookie: String,
        @Path("processId") processId: String,
        @Body data: RequestBody
    ): Response<ResponseBody>

    @GET("portal/custom-page/API/bpm/category")
    suspend fun getCategories(
        @Header("Cookie") Cookie: String,
        @Query("p") p : Int
    ):Response<List<Category>>
}
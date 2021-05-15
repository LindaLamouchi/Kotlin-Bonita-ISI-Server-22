package com.linda.isiservertest.api

import com.linda.isiservertest.model.ProcessBonita
import com.linda.isiservertest.model.User
import retrofit2.Response
import retrofit2.http.*
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
     ):Response<User>
/*              .addHeader("JSESSIONID",)
                .addHeader("X-Bonita-API-Token",)
                .addHeader("bonita.tenant")*/


    @GET("API/bpm/process?p=0&c=100&o=version%20desc&f=activationState=ENABLED&f=user_id=3")
    suspend fun getCustomProcess(
        //@HeaderMap headers:Map<String,String>,
        @Header("Cookie") cookie:String,
        /*@Query("p") p:Int=0,
        @Query("c") c:Int=100,
        @Query("o") o:String="version%20desc",
        @Query("f") e:String="activationStat=ENABLED",
        @Query("f") u:String="user_id=3"*/

    ): Response<List<ProcessBonita>>

    @GET("API/bpm/process/{id}/contract")
    suspend fun getPost2(
        @Path("id") number: Int
    ): Response<ProcessBonita>

}
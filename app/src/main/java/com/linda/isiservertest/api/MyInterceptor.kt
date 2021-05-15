package com.linda.isiservertest.api

import android.content.Context
import com.linda.isiservertest.HomeActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request :Request=chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
        return chain.proceed(request)
    }
}
package com.linda.isiservertest.SessionManager

import android.content.Context
import android.content.SharedPreferences

class SessionManagement (context:Context){
    val PREFERENCE_NAME="Shared_Pref_Data"
    val PREFERENCE_SESSION="Session"
    val PREFERENCE_LOGIN="Token"

    val preferences=context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    fun  getLogin(): String? {
        return preferences.getString(PREFERENCE_LOGIN," ")
    }
    fun  getSession(): String? {
        return preferences.getString(PREFERENCE_SESSION," ")
    }
    fun setLogin(login:String){
        val editor=preferences.edit()
        editor.putString(PREFERENCE_LOGIN,login)
        editor.apply()
    }
    fun setSessionID(sessionIid:String){
        val editor=preferences.edit()
        editor.putString(PREFERENCE_SESSION,sessionIid)
        editor.apply()
    }

}
package com.example.loanapp.utils


import android.content.Context
import com.example.loanapp.application.AppConstants
import com.example.loanapp.application.LoanApplication

object StorageUtils {

    private val sharedPref=LoanApplication.getInstance().getSharedPreferences(AppConstants.StorageUtilKeys.PREFERENCE_KEY,Context.MODE_PRIVATE)

    fun storeString(key: String, value: String){
        sharedPref.edit().putString(key,value).apply()
    }

    fun storeBoolean(key: String, value: Boolean){
        sharedPref.edit().putBoolean(key,value).apply()
    }

    fun storeInt(key: String, value: Int){
        sharedPref.edit().putInt(key,value).apply()
    }

    fun fetchBoolean(key: String,defaultValue:Boolean) : Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun fetchString(key: String,defaultValue:String) : String? {
        return sharedPref.getString(key, defaultValue)
    }

    fun fetchInt(key: String,defaultValue:Int) : Int {
        return sharedPref.getInt(key, defaultValue)
    }

    fun removeKey(key: String){
        sharedPref.edit().remove(key).apply()
    }
}
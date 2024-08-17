package com.example.loanapp.networkUtilities

import com.example.loanapp.application.AppConstants


open class BaseDataSource {
    fun url(api: String): String {
        val BASE_URL = AppConstants.Network.BASE_URL;
        return "$BASE_URL$api"
    }
}
package com.example.loanapp.networkUtilities

interface NetworkResponseListener<T> {

    fun onResponseSuccess(response: T)

    fun onResponseFailure(message: String?, response: T?, code: Int?)

}
package com.example.loanapp.models

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    var observeStatus: Boolean=true,
    var message:String?,
    val loans: ArrayList<Loan>?,
)

data class Record<T>(
    @SerializedName("record", alternate = arrayOf("records"))
    val record:T?,
    val token:String?,
    val totalRecords:Int?
)

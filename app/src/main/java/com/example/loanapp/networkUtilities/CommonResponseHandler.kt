package com.example.loanapp.networkUtilities

import android.util.Log
import com.example.loanapp.models.BaseResponse
import com.example.loanapp.utils.ToastUtils

import org.json.JSONObject
import retrofit2.*
import java.lang.Exception


class CommonResponseHandler<T> (private val mCallback: NetworkResponseListener<T>) :
    Callback<T> {
    private val TAG = "CommonResponseHandler"
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (call.isCanceled){
            return
        }
        var baseResponse = response.body()
        if (response.isSuccessful && baseResponse != null) {
            if (baseResponse is BaseResponse<*>){
                baseResponse.observeStatus=true
                mCallback.onResponseSuccess(baseResponse)
            }

        }else{
            var message=""
            try {
                val jObjError = JSONObject(response.errorBody()!!.string())
                message=jObjError.getString("message")
            }catch (e: Exception) {
                Log.e(TAG, "onResponse: "+e.message )
            }
            if (message==""){
                message= response.message()
            }
            mCallback.onResponseFailure(message, baseResponse, response.code())
            ToastUtils.showLongToast(message)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "onFailure: "+t.message )
        val errorMsg = t.message?.let {
            it
        } ?: "Could not connect to server."

        ToastUtils.showLongToast("Error connecting to server, please try again in a moment.")
        mCallback.onResponseFailure("Error connecting to server", null, 700)
    }

}
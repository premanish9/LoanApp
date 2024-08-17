package com.example.loanapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loanapp.models.BaseResponse
import com.example.loanapp.models.LoansResponse
import com.example.loanapp.networkUtilities.ApiDataSource
import com.example.loanapp.networkUtilities.NetworkResponseListener

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PreLoginViewModel(private val dataSource: ApiDataSource): ViewModel() {

    //signup
    private val _apiSignup = MutableLiveData<BaseResponse<HashMap<String,String>?>?>()
    val apiSignup get() =_apiSignup

    fun clearApiSignup(){
        _apiSignup.postValue(null)
    }

    fun callApiSignup(
        appUserSendOtp: HashMap<String,String>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.signupDataSource(appUserSendOtp,signUpResponseHandler())
        }
    }

    private fun signUpResponseHandler(): NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
        return object : NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
            override fun onResponseSuccess(response: BaseResponse<HashMap<String,String>?>?) {
                _apiSignup.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<HashMap<String,String>?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<HashMap<String,String>?>(false,message,null)
                _apiSignup.value=failedResponse
            }

        }
    }

    //signin
    private val _apiSignin = MutableLiveData<BaseResponse<HashMap<String,String>?>?>()
    val apiSignin get() =_apiSignin

    fun clearApiSignIn(){
        _apiSignin.postValue(null)
    }

    fun callApiSignIn(
        appUserSendOtp: HashMap<String,String>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.signinDataSource(appUserSendOtp,signInResponseHandler())
        }
    }

    private fun signInResponseHandler(): NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
        return object : NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
            override fun onResponseSuccess(response: BaseResponse<HashMap<String,String>?>?) {
                _apiSignin.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<HashMap<String,String>?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<HashMap<String,String>?>(false,message,null)
                _apiSignin.value=failedResponse
            }

        }
    }


    //update profile
    private val _apiUpdateProfile = MutableLiveData<BaseResponse<HashMap<String,String>?>?>()
    val apiUpdateProfile get() =_apiUpdateProfile

    fun clearupdateProfileResponseHandler(){
        _apiUpdateProfile.postValue(null)
    }

    fun callUpdateProfile(
        appUserSendOtp: HashMap<String,String>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.updateProfileDataSource(appUserSendOtp,updateProfileResponseHandler())
        }
    }

    private fun updateProfileResponseHandler(): NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
        return object : NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
            override fun onResponseSuccess(response: BaseResponse<HashMap<String,String>?>?) {
                _apiUpdateProfile.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<HashMap<String,String>?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<HashMap<String,String>?>(false,message,null)
                _apiUpdateProfile.value=failedResponse
            }

        }
    }


    //apply loan
    private val _apiApplyLoan = MutableLiveData<BaseResponse<HashMap<String,String>?>?>()
    val apiApplyLoan get() =_apiApplyLoan

    fun clearApplyLoanResponseHandler(){
        _apiApplyLoan.postValue(null)
    }

    fun callApplyLoan(
        appUserSendOtp: HashMap<String,String>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.applyLoanDataSource(appUserSendOtp,applyLoanResponseHandler())
        }
    }

    private fun applyLoanResponseHandler(): NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
        return object : NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
            override fun onResponseSuccess(response: BaseResponse<HashMap<String,String>?>?) {
                _apiApplyLoan.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<HashMap<String,String>?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<HashMap<String,String>?>(false,message,null)
                _apiApplyLoan.value=failedResponse
            }

        }
    }


    //view loan
    private val _apiViewLoan = MutableLiveData<BaseResponse<LoansResponse?>?>()
    val apiViewLoan get() =_apiViewLoan

    fun clearViewLoanResponseHandler(){
        _apiViewLoan.postValue(null)
    }

    fun callViewLoan(
        appUserSendOtp: HashMap<String,String>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.loanHistoryDataSource(appUserSendOtp.get("mobile")!!,viewLoanResponseHandler())
        }
    }

    private fun viewLoanResponseHandler(): NetworkResponseListener<BaseResponse<LoansResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<LoansResponse?>?> {
            override fun onResponseSuccess(response: BaseResponse<LoansResponse?>?) {
                _apiViewLoan.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<LoansResponse?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<LoansResponse?>(false,message,null)
                _apiViewLoan.value=failedResponse
            }

        }
    }



    //upload user data
    private val _apiUplUserData = MutableLiveData<BaseResponse<HashMap<String,String>?>?>()
    val apiUplUserData get() =_apiUplUserData

    fun clearUplUserDataResponseHandler(){
        _apiUplUserData.postValue(null)
    }

    fun callUplUserData(
        appUserSendOtp: HashMap<String,Any>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.uploadUserdataDataSource(appUserSendOtp,uplUserDataResponseHandler())
        }
    }

    private fun uplUserDataResponseHandler(): NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
        return object : NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
            override fun onResponseSuccess(response: BaseResponse<HashMap<String,String>?>?) {
                _apiUplUserData.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<HashMap<String,String>?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<HashMap<String,String>?>(false,message,null)
                _apiUplUserData.value=failedResponse
            }

        }
    }

    //upload photo
    private val _apiUplPhotoData = MutableLiveData<BaseResponse<HashMap<String,String>?>?>()
    val apiUplPhotoData get() =_apiUplPhotoData

    fun clearUplPhotoResponseHandler(){
        _apiUplPhotoData.postValue(null)
    }

    fun callUplPhotoData(
        appUserSendOtp: HashMap<String,String>
    ){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.uploadphotoDataSource(appUserSendOtp,uplUserPhotoResponseHandler())
        }
    }

    private fun uplUserPhotoResponseHandler(): NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
        return object : NetworkResponseListener<BaseResponse<HashMap<String,String>?>?> {
            override fun onResponseSuccess(response: BaseResponse<HashMap<String,String>?>?) {
                _apiUplPhotoData.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<HashMap<String,String>?>?,
                code: Int?
            ) {
                val failedResponse=BaseResponse<HashMap<String,String>?>(false,message,null)
                _apiUplPhotoData.value=failedResponse
            }

        }
    }

}
package com.example.loanapp.networkUtilities

import com.example.loanapp.application.AppConstants
import com.example.loanapp.models.BaseResponse
import com.example.loanapp.models.LoansResponse


class ApiDataSource: BaseDataSource() {


    fun signupDataSource(
        appUserSendOtp: HashMap<String,String>,
        callback: NetworkResponseListener<BaseResponse<HashMap<String,String>?>?>
    ) {
        ApiFactory.API_CALL.signupEndPoint(url(AppConstants.Network.signup),appUserSendOtp)
            .enqueue(CommonResponseHandler(callback))
    }


    fun signinDataSource(
        appUserSendOtp: HashMap<String,String>,
        callback: NetworkResponseListener<BaseResponse<HashMap<String,String>?>?>
    ) {
        ApiFactory.API_CALL.signInEndPoint(url(AppConstants.Network.signin),appUserSendOtp)
            .enqueue(CommonResponseHandler(callback))
    }

    fun applyLoanDataSource(
        verifyOtpRequest: HashMap<String,String>,
        callback: NetworkResponseListener<BaseResponse<HashMap<String,String>?>?>
    ) {
        ApiFactory.API_CALL.applyLoanEndPoint(url(AppConstants.Network.applyloan),verifyOtpRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun loanHistoryDataSource(
        registerRequest: String,
        callback: NetworkResponseListener<BaseResponse<LoansResponse?>?>
    ) {
        ApiFactory.API_CALL.loanHistoryEndPoint(url(AppConstants.Network.loanhistory),registerRequest)
            .enqueue(CommonResponseHandler(callback))
    }


    fun updateProfileDataSource(
        registerRequest: HashMap<String,String>,
        callback: NetworkResponseListener<BaseResponse<HashMap<String,String>?>?>
    ) {
        ApiFactory.API_CALL.updateProfileEndPoint(url(AppConstants.Network.updateprofile),registerRequest)
            .enqueue(CommonResponseHandler(callback))
    }


    fun uploadUserdataDataSource(
        registerRequest: HashMap<String,Any>,
        callback: NetworkResponseListener<BaseResponse<HashMap<String,String>?>?>
    ) {
        ApiFactory.API_CALL.uploaduserdataEndPoint(url(AppConstants.Network.uploaduserdata),registerRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun uploadphotoDataSource(
        registerRequest: HashMap<String,String>,
        callback: NetworkResponseListener<BaseResponse<HashMap<String,String>?>?>
    ) {
        ApiFactory.API_CALL.uploaduserphotoEndpoint(url(AppConstants.Network.uploadphoto),registerRequest)
            .enqueue(CommonResponseHandler(callback))
    }



//
//    fun uploadBookImageDataSource(
//        file: MultipartBody.Part,
//        callback: NetworkResponseListener<BaseResponseNonArray<UploadBookFrontImageResponse?>?>
//    ) {
//        ApiFactory.AUTHENTICATED_API_CALL.uploadBookPicture(url(MuftBookConstants.Network.upload_photo),file)
//            .enqueue(CommonResponseHandler(callback))
//    }
}
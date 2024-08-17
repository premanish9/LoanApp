package com.example.loanapp.networkUtilities

import com.example.loanapp.models.BaseResponse
import com.example.loanapp.models.LoansResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url


interface ApiEndPointInterface {


    @POST
    fun signupEndPoint(@Url url: String,@Body appUserSendOtp: HashMap<String,String>) : Call<BaseResponse<HashMap<String,String>?>?>

    @POST
    fun signInEndPoint(@Url url: String,@Body appUserSendOtp: HashMap<String,String>) : Call<BaseResponse<HashMap<String,String>?>?>

    @POST
    fun applyLoanEndPoint(@Url url: String,@Body appUserSendOtp: HashMap<String,String>) : Call<BaseResponse<HashMap<String,String>?>?>

    @GET
    fun loanHistoryEndPoint(@Url url: String, @Query("mobile") userId: String) : Call<BaseResponse<LoansResponse?>?>


    @POST
    fun updateProfileEndPoint(@Url url: String,@Body appUserSendOtp: HashMap<String,String>) : Call<BaseResponse<HashMap<String,String>?>?>

    @POST
    fun uploaduserdataEndPoint(@Url url: String,@Body appUserSendOtp: HashMap<String,Any>) : Call<BaseResponse<HashMap<String,String>?>?>

    @POST
    fun uploaduserphotoEndpoint(@Url url: String,@Body appUserSendOtp: HashMap<String,String>) : Call<BaseResponse<HashMap<String,String>?>?>

//    @Multipart
//    @POST
//    fun uploadBookPicture(@Url url:String, @Part filePart: MultipartBody.Part): Call<BaseResponseNonArray<UploadBookFrontImageResponse?>?>
}
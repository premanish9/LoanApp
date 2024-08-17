package com.example.loanapp.networkUtilities

import android.util.Log
import com.example.loanapp.application.AppConstants
import com.example.loanapp.utils.StorageUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit


class RestClient {
    companion object{
        private var retrofit: Retrofit?=null
        fun getClient():Retrofit{
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)


            if (retrofit == null){
                retrofit =Retrofit.Builder()
                    .baseUrl(AppConstants.Network.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build()
            }
            return retrofit!!
        }

        private const val BASE_URL = "https://fcm.googleapis.com/"
        fun getClientFirebaseClient(): Retrofit? {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }


        fun getAuthenticatedClient():Retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.Network.BASE_URL)
            .client(getTrustKit())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private fun getTrustKit():OkHttpClient{
            val clientBuilder=OkHttpClient.Builder()
            clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
            clientBuilder.readTimeout(10, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(10, TimeUnit.SECONDS)

           /* if (BuildConfig.DEBUG) {*/
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            /*}*/
//
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            clientBuilder.addInterceptor(httpLoggingInterceptor)

            HeaderInterceptor.addPostLoginHeaders(clientBuilder)

            return clientBuilder.build()
        }


        internal object HeaderInterceptor{
            private const val TAG = "RestClient"
            fun addPostLoginHeaders(clientBuilder: OkHttpClient.Builder){
                clientBuilder.addInterceptor {
                    var request=it.request()
                    val headerBuilder=request.headers.newBuilder()
                    Log.e(
                        TAG, "addPostLoginHeaders: Token:  "+ StorageUtils.fetchString(
                        AppConstants.StorageUtilKeys.token,"")!!)
                    headerBuilder.add("Authorization","Bearer " + StorageUtils.fetchString(
                        AppConstants.StorageUtilKeys.token,"")!!)
                    request=request.newBuilder().headers(headerBuilder.build()).build()
                    it.proceed(request)
                }
            }
        }
    }
}
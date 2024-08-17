package com.example.loanapp.application

import android.app.Application

class LoanApplication:Application() {
    companion object{
//        private var notification_update : MutableLiveData<DataModel> = MutableLiveData()

        private var instance = LoanApplication()
        fun getInstance():LoanApplication{
            return instance
        }

//        fun getNotificationSubject(): MutableLiveData<DataModel> {
//            return notification_update
//        }
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }

}
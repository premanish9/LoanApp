package com.example.loanapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loanapp.networkUtilities.ApiDataSource
import java.lang.IllegalArgumentException

class LoanAppViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(PreLoginViewModel::class.java)->{
                PreLoginViewModel(ApiDataSource()) as T
            }
            /*modelClass.isAssignableFrom(PostLoginViewModel::class.java)->{
                PostLoginViewModel(ApiDataSource()) as T
            }*/
            else->{
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
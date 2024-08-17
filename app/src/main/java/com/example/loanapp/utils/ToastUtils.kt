package com.example.loanapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
import com.example.loanapp.application.LoanApplication


class ToastUtils {
    companion object{
        fun showShortToast(message:String){
            message.let {
                Toast.makeText(LoanApplication.getInstance(),it,Toast.LENGTH_SHORT).show()
            }
        }

        fun showLongToast(message:String){
            message.let {
                Toast.makeText(LoanApplication.getInstance(),it,Toast.LENGTH_LONG).show()
            }
        }
    }

}
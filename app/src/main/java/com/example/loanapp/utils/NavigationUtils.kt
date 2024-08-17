package com.example.loanapp.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.loanapp.PreLoginActivity
import com.example.loanapp.R

class NavigationUtils {
    companion object{
        fun  goToFragment(activity: Activity,bundle: Bundle=Bundle(),navigateToId:Int){
            var navController:NavController?=null
            if (activity is PreLoginActivity){
                 navController=activity.findNavController(R.id.nav_host_fragment_content_main)
            }else if(activity is PreLoginActivity){
                navController=activity.findNavController(R.id.nav_host_fragment_content_main)
            }
            if (bundle.isEmpty){
                navController!!.navigate(navigateToId,null,setTransition())
            }else{
                navController!!.navigate(navigateToId,bundle,setTransition())
            }
        }

        fun popBackStack(activity: Activity, destinationId: Int? = null, inclusive: Boolean = false): Boolean {
            var navController:NavController?=null
            if (activity is PreLoginActivity){
                navController=activity.findNavController(R.id.nav_host_fragment_content_main)
            }else if(activity is PreLoginActivity){
                navController=activity.findNavController(R.id.nav_host_fragment_content_main)
            }
            return if (destinationId != null) {
                navController!!.popBackStack(destinationId, inclusive)
            } else {
                navController!!.popBackStack()
            }
        }

        fun changeActivity(fromActivity:Activity,toActivityClass:Class<*>){
            fromActivity.startActivity(Intent(fromActivity.applicationContext,toActivityClass))
        }

        private fun setTransition(): NavOptions {
            return navOptions {
                anim {
                    enter = androidx.appcompat.R.anim.abc_popup_enter
                    exit = com.google.android.material.R.anim.abc_popup_exit
                    popEnter = com.google.android.material.R.anim.m3_side_sheet_enter_from_left
                    popExit = com.google.android.material.R.anim.m3_side_sheet_exit_to_right
                }
            }
        }
    }
}
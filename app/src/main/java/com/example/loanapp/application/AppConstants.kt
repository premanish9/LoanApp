package com.example.loanapp.application

object AppConstants {
    object Network {
        const val BASE_URL = "http://98.70.11.251:5000"



        const val signup="/sign_up"
        const val signin = "/sign_in"
        const val applyloan = "/apply_loan"
        const val loanhistory = "/loan_history"
        const val updateprofile = "/update_profile"
        const val uploaduserdata = "/upload_user_data"
        const val uploadphoto = "/upload_photo"

    }

    object ActivityActions{
        const val REGISTER="REGISTER"
        const val LOGIN="LOGIN"
        const val LOGOUT="LOGOUT"
        const val HOME_PAGE="HOME_PAGE"

        const val CONTACT_US="CONTACT_US"
        const val APP_EXIT="APP_EXIT"
    }

//    USER_UPDATE => old details
//    BANK_DETAILS_UPDATE => old details
//    UPI_UPDATE => old details
//


    object StorageUtilKeys{
        const val PREFERENCE_KEY = "general"
        const val USER_INFO_PREFERENCE_KEY = "user_info_preference_key"

        const val token = "token"
        const val mobile = "mobile"
        const val dealerName = "dealerName"
        const val bankAddedStatus = "bank_added_status"
        const val name = "name"
        const val email = "email"
        const val points = "points"
        const val city = "stateCity"
        const val role = "role"
        const val state = "state"
        const val _id = "_id"
        const val mobileShow = "mobileShow"

        const val groupUnsubscribeTime_="groupUnsubscribeTime_"
    }

    object Other{
        const val points="points"
        const val schemes="schemes"
        const val from="from"
        const val terms="terms"
        const val privacy="privacy"
        const val contact="contact"
        const val about="about"
    }
}
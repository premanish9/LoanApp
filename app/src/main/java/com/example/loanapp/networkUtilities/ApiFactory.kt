package com.example.loanapp.networkUtilities

class ApiFactory {
    companion object{
        @JvmField
        val API_CALL: ApiEndPointInterface = RestClient.getClient().create(ApiEndPointInterface::class.java)

        @JvmField
        val AUTHENTICATED_API_CALL: ApiEndPointInterface =
            RestClient.getAuthenticatedClient().create(ApiEndPointInterface::class.java)
}

}
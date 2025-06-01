package vn.gmi.workzen.networks.api

class EndPoints {
    companion object{
        const val LOGIN_ENDPOINT = "/api/auth/login"
        const val CHECK_EXISTS_PHONE_ENDPOINT = "/api/auth/phone/exists"
        const val REGISTER_ENDPOINT = "/api/auth/signup"
        const val REFRESH_TOKEN = "/api/auth/refresh"

        const val UPDATE_IDENTIFICATION_ENDPOINT = "/api/identification"
        const val GET_IDENTIFICATION_ENDPOINT = "/api/identification"
    }
}
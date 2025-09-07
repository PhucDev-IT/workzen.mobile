package vn.gmi.workzen.data.models.request.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestModel (
    @SerializedName("phone")
    var numberPhone:String,
    var password:String,
    var deviceName:String,
    var deviceId:String,
    var deviceToken:String,
)
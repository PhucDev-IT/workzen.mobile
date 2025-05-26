package vn.gmi.workzen.networks.models.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel (
    @SerializedName("phone")
    var numberPhone:String,
    var password:String,
    var deviceName:String,
    var deviceId:String,
    var deviceToken:String,
)
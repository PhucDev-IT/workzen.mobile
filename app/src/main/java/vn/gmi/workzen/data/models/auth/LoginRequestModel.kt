package vn.gmi.workzen.data.models.auth

data class LoginRequestModel (
    var numberPhone:String,
    var password:String,
    var deviceName:String,
    var deviceId:String,
    var deviceToken:String,
)
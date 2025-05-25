package vn.gmi.workzen.networks.models.request

data class LoginRequestModel (
    var numberPhone:String,
    var password:String,
    var deviceName:String,
    var deviceId:String,
    var deviceToken:String,
)
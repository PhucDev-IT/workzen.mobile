package vn.gmi.workzen.services.socket

interface AppWebSocketListener {
    fun onReceiveMessage(data:String)
}
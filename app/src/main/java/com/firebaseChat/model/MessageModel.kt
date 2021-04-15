package com.firebaseChat.model

import java.text.SimpleDateFormat
import java.util.*

data class MessageModel(val msgTime: String = getTime()) {

    var userName: String=""
    var msg: String = ""
    var msgId: String =""

    companion object {
        fun getTime(): String {
            val dfHour = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
            return  dfHour.format(Calendar.getInstance().time)
        }
    }
}

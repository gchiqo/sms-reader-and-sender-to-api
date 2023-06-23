package com.example.smssender

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import kotlinx.coroutines.*

class MessageBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // getting bundle data on below line from intent.
        val data = intent.extras
        // creating an object on below line.
        val pdus = data!!["pdus"] as Array<Any>?
        // running for loop to read the sms on below line.
        var Message = ""
        var sender = ""
        for (pdu in pdus!!) {
            val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
            sender = smsMessage.displayOriginatingAddress
            Message += smsMessage.messageBody
        }
        val message = "Sender : " + sender + "Message: " + Message

        mListener!!.messageReceived(message)
        rListener?.responseReceived("waiting for response")
        GlobalScope.launch {
            val response = SendToApi().sendRequest(sender, Message)
            Thread.sleep(1000)
            rListener?.responseReceived(response)
        }

    }

    companion object {
        // creating a variable for a message listener interface on below line.
        private var mListener: MessageListenerInterface? = null
        private var rListener: MessageListenerInterface? = null

        // on below line we are binding the listener.
        fun bindListener(listener: MessageListenerInterface?) {
            mListener = listener
            rListener = listener
        }
    }
}
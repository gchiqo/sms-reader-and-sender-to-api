package com.example.smssender

interface MessageListenerInterface {
    // creating an interface method for messages received.
    fun messageReceived(message: String?)
    fun responseReceived(message: String?)
}
package com.example.smssender

import okhttp3.*
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

public class SendToApi {

    public suspend fun sendRequest(senderAddress: String, messageBody: String): String =
        withContext(Dispatchers.IO) {
            val apiUrl = "https://test3.2111190.click/api/msg/$senderAddress/$messageBody"

            val client = OkHttpClient()

            val request = Request.Builder().url(apiUrl).build()

            val response = client.newCall(request).execute()

            return@withContext response.body?.string() ?: ""
        }
}
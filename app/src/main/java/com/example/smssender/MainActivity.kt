package com.example.smssender

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import android.Manifest
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), MessageListenerInterface {

    private val smsPermissions = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS
    )
    private val smsPermissionRequestCode = 123

    private var msgTV: TextView? = null
    private var respon: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        msgTV = findViewById<TextView>(R.id.idTVMessage)
        respon = findViewById<TextView>(R.id.idResponse)

        requestSmsPermissions()
    }

    override fun messageReceived(message: String?) {
        // setting message in our text view on below line.
        msgTV!!.text = message
    }

    override fun responseReceived(message: String?) {
        runOnUiThread {
            respon?.text = message
        }
    }

    private fun requestSmsPermissions() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECEIVE_SMS
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, smsPermissions, smsPermissionRequestCode)
        } else {
            MessageBroadcastReceiver.bindListener(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == smsPermissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MessageBroadcastReceiver.bindListener(this)
            } else {
                requestSmsPermissions()
            }
        }
    }

}
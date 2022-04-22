package com.pbaileyapps.qrscannertest

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import java.io.*
import java.net.URL
import java.net.URLConnection
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: EditText = findViewById(R.id.eventCode)
        val qrButton:ImageButton = findViewById(R.id.qr_button)
        qrButton.setOnClickListener({
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null) {

            thread (start = true){

                val url = URL("https://victorf8.pythonanywhere.com/submit-attendance")
                val postData = "${result.contents}"

                val conn: URLConnection = url.openConnection()
                conn.setDoOutput(true)
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                conn.setRequestProperty("Content-Length", Integer.toString(postData.length))

                DataOutputStream(conn.getOutputStream()).use { dos -> dos.writeBytes(postData) }

                BufferedReader(
                    InputStreamReader(
                        conn.getInputStream()
                    )
                ).use { bf ->
                    var line: String?
                    while (bf.readLine().also { line = it } != null) {
                        println(line)
                    }
                }

            }



        }
    }
}
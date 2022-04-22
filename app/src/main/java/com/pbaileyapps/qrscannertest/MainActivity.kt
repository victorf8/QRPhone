package com.pbaileyapps.qrscannertest

import android.content.Intent
import android.os.Bundle
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
        val textView:TextView = findViewById(R.id.textView)
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
//            val url = URL("https://victorf8.pythonanywhere.com/submit-attendance")
//            val postData = "${result.contents}"
//
//            val conn = url.openConnection()
//            conn.doOutput = true
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//            conn.setRequestProperty("Content-Length", postData.length.toString())
//
//            Log.d("tag","we are herrrrre\n")
//
//            DataOutputStream(conn.getOutputStream()).use { it.writeBytes(postData) }
//            BufferedReader(InputStreamReader(conn.getInputStream())).use { bf ->
//                var line: String?
//                while (bf.readLine().also { line = it } != null) {
//                    Log.d("tag","0 " + line)
//                    println(line)
//                }
//            }

//            Log.d("tag","we are herrrrre1\n")
//
//            val message = "${result.contents}"
//            val serverURL: String = "https://victorf8.pythonanywhere.com/submit-attendance"
//            val url = URL(serverURL)
//            val connection = url.openConnection() as HttpsURLConnection
//            connection.requestMethod = "POST"
//            connection.connectTimeout = 300000
//            connection.doOutput = true
//
//            Log.d("tag","we are herrrrre2\n")
//
//            val postData: ByteArray = message.toByteArray(StandardCharsets.UTF_8)
//
//            connection.setRequestProperty("charset", "utf-8")
//            connection.setRequestProperty("Content-length", postData.size.toString())
//            connection.setRequestProperty("Content-Type", "application/json")
//
//            Log.d("tag","we are herrrrre3\n")
//
//            try {
//                val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
//                outputStream.write(postData)
//                outputStream.flush()
//            } catch (exception: Exception) {
//
//            }
//
//            Log.d("tag","we are herrrrre4\n")
//
//            if (connection.responseCode != HttpsURLConnection.HTTP_OK && connection.responseCode != HttpsURLConnection.HTTP_CREATED) {
//                try {
//                    val inputStream: DataInputStream = DataInputStream(connection.inputStream)
//                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
//                    val output: String = reader.readLine()
//
//                    println("There was error while connecting the chat $output")
//                    System.exit(0)
//
//                } catch (exception: Exception) {
//                    throw Exception("Exception while push the notification  $exception.message")
//                }
//            }

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
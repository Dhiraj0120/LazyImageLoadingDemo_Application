package com.demo.lazyimageloadingdemo_application

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchJson()
    }

    private fun fetchJson() {
        val url = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val listData = gson.fromJson(body, ListData::class.java)
                runOnUiThread {
                    val listview = findViewById<ListView>(R.id.main_listview)
                    listview.adapter = MyCustomAdapter(applicationContext, listData)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Fail to execute query")
            }
        })
    }
}
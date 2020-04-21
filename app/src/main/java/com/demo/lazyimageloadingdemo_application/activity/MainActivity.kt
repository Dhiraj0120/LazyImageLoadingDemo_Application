package com.demo.lazyimageloadingdemo_application.activity

import android.annotation.SuppressLint
import android.app.Service
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.lazyimageloadingdemo_application.model.ListData
import com.demo.lazyimageloadingdemo_application.adapters.MainAdapter
import com.demo.lazyimageloadingdemo_application.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var context = this
    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleview_main.layoutManager = LinearLayoutManager(this)

        no_internet.setText(resources.getText(R.string.no_internet_found))
        no_internet.setTextSize(22F)
        no_internet.setTextColor(Color.RED)
        no_internet.visibility = View.GONE
        recycleview_main?.visibility = View.VISIBLE

        refresh_button.setOnClickListener(View.OnClickListener { fetchJson() })
    }

    override fun onStart() {
        super.onStart()
        if (isConnected()) {
            no_internet.visibility = View.GONE
            recycleview_main?.visibility = View.VISIBLE
            fetchJson()
        } else {
            no_internet.visibility = View.VISIBLE
            recycleview_main?.visibility = View.GONE
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
    }


    fun isConnected(): Boolean {
        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo
            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }


    private fun fetchJson() {
        val url = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                //println(body)

                val gson = GsonBuilder().create()

                val listData = gson.fromJson(body, ListData::class.java)
                if (listData.rows.isNullOrEmpty() || listData.rows.size == 0) {
                    no_internet.setText(resources.getText(R.string.no_internet_found))
                } else {
                    loadDataInList(listData)

                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Fail to execute query")
            }
        })
    }

    private fun loadDataInList(listData: ListData){
        runOnUiThread {
            page_title.setText(listData.title)
            page_title.setTextSize(22F)
            page_title.setTypeface(Typeface.DEFAULT_BOLD)
            page_title.setTextColor(Color.BLUE)
            recycleview_main.adapter =
                MainAdapter(
                    listData,
                    applicationContext
                )
        }
    }
}



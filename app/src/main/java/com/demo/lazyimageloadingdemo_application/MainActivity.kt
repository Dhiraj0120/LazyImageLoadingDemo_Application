package com.demo.lazyimageloadingdemo_application

import android.annotation.SuppressLint
import android.app.Service
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var context = this
    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null
    var pullToRefresh: SwipeRefreshLayout? = null
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
        pullToRefresh = findViewById(R.id.pullToRefresh) as SwipeRefreshLayout
        pullToRefresh?.setOnRefreshListener{}

        //(SwipeRefreshLayout.OnRefreshListener { pullToRefresh?.setRefreshing(false) })
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
                    runOnUiThread {
                        recycleview_main.adapter = MainAdapter(listData, applicationContext)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Fail to execute query")
            }
        })
    }


    private fun SwipeRefreshLayout?.setOnRefreshListener(onRefreshListener: OnRefreshListener?) {

        onRefreshListener?.onRefresh() {
            println("Pulling..........")
            pullToRefresh?.setRefreshing(false)
            pullToRefresh?.isRefreshing = false
            Toast.makeText(context, "Refreshing Please wait....", Toast.LENGTH_LONG).show()
            if (isConnected()) {
                no_internet.visibility = View.GONE
                recycleview_main?.visibility = View.VISIBLE
                fetchJson()
            } else {
                no_internet.visibility = View.VISIBLE
                recycleview_main?.visibility = View.GONE
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

            println("Pulling..........")
        }

    }

    private fun OnRefreshListener?.onRefresh(function: () -> Unit) {
        println("Pulling..........")
        pullToRefresh?.setRefreshing(false)

        Toast.makeText(context, "Refreshing Please wait....", Toast.LENGTH_LONG).show()
        if (isConnected()) {
            no_internet.visibility = View.GONE
            recycleview_main?.visibility = View.VISIBLE
            fetchJson()
        } else {
            no_internet.visibility = View.VISIBLE
            recycleview_main?.visibility = View.GONE
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }

        println("Pulling..........")
    }
}



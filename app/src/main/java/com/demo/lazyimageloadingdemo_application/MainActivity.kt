package com.demo.lazyimageloadingdemo_application

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //listview.adapter = MyCustomAdapter(this)
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
                runOnUiThread() {
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

class ListData(val title: String, val rows: List<Rows>)

class Rows(val title: String, val description: String, val imageHref: String)

class MyCustomAdapter(context: Context, val listData: ListData) : BaseAdapter() {
    private val layoutInflater = LayoutInflater.from(context)
    private val names = arrayListOf("qqq", "w", "e", "r", "t")
    private val mcontext = context
    override fun getCount(): Int {
        return listData.rows.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return "position : $position"
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowMain = layoutInflater.inflate(R.layout.main_row, parent, false)
        val rowTitle = rowMain.findViewById<TextView>(R.id.main_title)
        val rowDesc = rowMain.findViewById<TextView>(R.id.main_description)
        val rowImage = rowMain.findViewById<ImageView>(R.id.main_imageview)

        val objectData = listData.rows.get(position)

        rowTitle.text = objectData.title
        rowDesc.text = objectData.description




            // load the image with Picasso
            val into: Any = Picasso.with(mcontext.getApplicationContext()) // give it the context
                .load(objectData.imageHref) // load the image
                .into(rowImage) // select the ImageView to load it into



        return rowMain
    }
}

class PicassoClient()
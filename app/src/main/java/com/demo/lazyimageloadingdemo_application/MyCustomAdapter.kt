package com.demo.lazyimageloadingdemo_application

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

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
            .fit() //
            .centerCrop()
            .into(rowImage) // select the ImageView to load it into
        return rowMain
    }
}
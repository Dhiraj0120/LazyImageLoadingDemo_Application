package com.demo.lazyimageloadingdemo_application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.lazyimageloadingdemo_application.model.ListData
import com.demo.lazyimageloadingdemo_application.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_item.view.*

class MainAdapter(
    val listData: ListData,
    applicationContext: Context
) :
    RecyclerView.Adapter<CustomViewHolder>() {
    private val mcontext = applicationContext
    override fun getItemCount(): Int {
        return listData.rows.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_item, parent, false)
        //layoutInflater.inflate(R.layout.row_item, parent, false)
        return CustomViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val dataObject = listData.rows.get(position)
        if (dataObject.imageHref != null) {

            holder?.view?.row_title?.text = dataObject.title
        }
        if (dataObject.imageHref != null) {
            holder?.view?.row_description?.text = dataObject.description
        }

        val thumbnail_rowImage = holder?.view?.row_imageview
        var uri: String? = null
        // load the image with Picasso
        if (!dataObject.imageHref.isNullOrBlank()) {
            uri = dataObject.imageHref
            println(">>>>>>> $uri");
            Picasso.get().load(uri).placeholder(R.mipmap.ic_launcher).into(thumbnail_rowImage)
        }else{
            Picasso.get().load(uri).placeholder(R.mipmap.ic_launcher).into(thumbnail_rowImage)
        }
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
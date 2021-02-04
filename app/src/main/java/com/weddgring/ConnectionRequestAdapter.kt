package com.weddgring

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weddgring.pojo.*

class ConnectionRequestAdapter(var context: Context, var userListPojo: ArrayList<ConnectRequestData>, var recycleViewClickListner: RecycleViewClickListner) :
    RecyclerView.Adapter<ConnectionRequestAdapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview),View.OnClickListener {
        lateinit var name: TextView
        lateinit var place: TextView
        lateinit var iv_image: ImageView
        lateinit var age: TextView
        lateinit var cl_main: ConstraintLayout

        init {
            name = itemview.findViewById(R.id.name)
            place = itemview.findViewById(R.id.place)
            age = itemview.findViewById(R.id.age)
            cl_main = itemview.findViewById(R.id.cl_main)
            iv_image = itemview.findViewById(R.id.iv_image)
            cl_main.setOnClickListener {
                Log.e("recycleViewClickListner","{$position.toString()}  click adpter")
                recycleViewClickListner.itemClick(position)
            }

        }

        override fun onClick(v: View?) {
            Log.e("recycleViewClickListner","{$position.toString()}  click adpter")

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.connect_request_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userListPojo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText("Name- "+userListPojo.get(position).fullname)
        holder.age.setText("Occupation- "+userListPojo.get(position).occupation)
        holder.place.setText("Place- "+userListPojo.get(position).city)
        Glide.with(context).load(userListPojo.get(position).dp_url).placeholder(R.drawable.ring_icon).into(holder.iv_image)

    }
}
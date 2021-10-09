package com.example.snapchatclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.snapchatclone.Emails
import com.example.snapchatclone.R
import com.example.snapchatclone.R.layout.listview

class EmailAdapter(private val context: Context, private val emailIds:List<Emails>):RecyclerView.Adapter<EmailAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val title1: TextView? = itemView.findViewById(R.id.txvTitle)
        fun setData(email:Emails?,pos: Int){
            title1?.text = email!!.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(context).inflate(listview,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
             val email = emailIds[position]
        holder.setData(email,position)
    }

    override fun getItemCount(): Int {
        return emailIds.size
    }
}
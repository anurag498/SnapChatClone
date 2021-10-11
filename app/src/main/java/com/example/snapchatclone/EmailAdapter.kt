package com.example.snapchatclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EmailAdapter(private val emailIds:List<Emails>):RecyclerView.Adapter<EmailAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val title1: TextView = itemView.findViewById(R.id.txvTitle)


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listview,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = emailIds[position]
        holder.title1.text = currentitem.emailData
    }

    override fun getItemCount(): Int {
        return emailIds.size
    }

}





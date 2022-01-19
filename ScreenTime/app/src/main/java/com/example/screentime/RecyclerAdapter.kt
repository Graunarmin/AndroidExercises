package com.example.screentime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter (var itemList: ArrayList<ExampleItem>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>()
{
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvText1: TextView = itemView.findViewById(R.id.tvText1)
        val tvText2: TextView = itemView.findViewById(R.id.tvText2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MyViewHolder
    {
        //Layout Inflater creates an Object from the "Blueprint" of a Layout Class: inflate turns Object into ViewObject
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int)
    {
        val currentItem = itemList[position]
        holder.ivIcon.setImageResource(currentItem.imagesResource)
        holder.tvText1.text = currentItem.text1
        holder.tvText2.text = currentItem.text2
    }

    override fun getItemCount(): Int
    {
        return itemList.size
    }

}
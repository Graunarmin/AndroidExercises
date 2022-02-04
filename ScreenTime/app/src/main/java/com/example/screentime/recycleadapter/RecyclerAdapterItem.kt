package com.example.screentime.recycleadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.R
import com.example.screentime.items.Item

open class RecyclerAdapterItem : RecyclerView.Adapter<RecyclerAdapterItem.ItemViewHolder>()
{
    open var itemList = ArrayList<Item>()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvUsageTime: TextView = itemView.findViewById(R.id.tvUsageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
    {
        //Layout Inflater creates an Object from the "Blueprint" of a Layout Class: inflate turns Object into ViewObject
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        val currentItem = itemList[position]
        holder.tvName.text = currentItem.name
        holder.ivIcon.setImageDrawable(currentItem.icon)
        holder.tvUsageTime.text = currentItem.readableUseTime
    }

    override fun getItemCount(): Int
    {
        return itemList.size
    }

    protected fun getNewItemIndex(itemToInsert: Item): Int
    {
        for (item: Item in itemList)
        {
            if(item < itemToInsert)
            {
                return itemList.indexOf(item)
            }
        }
        return itemList.size
    }
}
package com.example.screentime.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.R
import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.AppItem

/**
 * Recycler Adapter for the RecyclerView of set Limits in the ScreenTimeOverviewActivity
 */

private const val TAG = "com.example.screentime.adapter.recycleradapterlimit"
class RecyclerAdapterLimit : RecyclerView.Adapter<RecyclerAdapterLimit.LimitViewHolder>()
{
    private var itemList : ArrayList<AppItem> = ArrayList()

    class LimitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tvLimitAppName : TextView = itemView.findViewById(R.id.tvLimitAppName)
        val tvLimitedTo: TextView = itemView.findViewById(R.id.tvLimitedTo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LimitViewHolder
    {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.card_item_limit, parent, false)
        return LimitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LimitViewHolder, position: Int)
    {
        val currentItem = itemList[position]
        holder.tvLimitAppName.text = currentItem.itemName
        holder.tvLimitedTo.text = "Limit set to ${currentItem.limit} minutes"
    }

    override fun getItemCount(): Int
    {
        Log.d(TAG, "Limits: ${itemList.size}")
        return itemList.size
    }

    fun update()
    {
        itemList.clear()
        this.notifyDataSetChanged()

        itemList = ScreenTimeApp.appInstance.appList.getAppsWithLimit()
        this.notifyDataSetChanged()
    }
}
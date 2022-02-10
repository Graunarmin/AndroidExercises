package com.example.screentime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.screentime.R
import com.example.screentime.items.Item

private const val TAG = "com.example.screentime.adapter.recycleradapteritem"

/**
 * Interface for reacting to a click on an [Item] in one of the RecyclerViews
 */
interface OnTimeItemClickListener
{
    fun onTimeItemClicked(item: Item)
}

/**
 * RecyclerAdapter that fills the RecyclerViews for the [Item]s in the ScreenTimeOverviewActivity with data.
 *
 * This class provides the basic Functionality and implements the parent Class [RecyclerView.Adapter]
 * while the inheriting classes [RecyclerAdapterAppItem] and [RecyclerAdapterCategoryItem] update and hold their specific lists of items.
 */

open class RecyclerAdapterItem(var itemClickListener: OnTimeItemClickListener) : RecyclerView.Adapter<RecyclerAdapterItem.ItemViewHolder>()
{
    open var itemList : ArrayList<Item> = ArrayList()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvUsageTime: TextView = itemView.findViewById(R.id.tvUsageTime)

        fun bind(item: Item, clickListener: OnTimeItemClickListener)
        {
            itemView.setOnClickListener{
                clickListener.onTimeItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder
    {
        //Layout Inflater creates an Object from the "Blueprint" of a Layout Class: inflate turns Object into ViewObject
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.card_item_screentime, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int)
    {
        val currentItem = itemList[position]
        holder.tvName.text = currentItem.itemName
        holder.ivIcon.setImageDrawable(currentItem.icon)
        holder.tvUsageTime.text = currentItem.readableUseTime
        holder.bind(currentItem, itemClickListener)
    }

    override fun getItemCount(): Int
    {
        return itemList.size
    }
}
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


    private fun removeOldEntry(index: Int, adapter: RecyclerAdapterItem)
    {
        // index is -1 if Item was not in List
        if(index != -1)
        {
            // remove the entry
            itemList.removeAt(index)
            // then notify change at removed position
            adapter.notifyItemRemoved(index)
        }
    }

    private fun addNewEntry(newItem: Item, index: Int, adapter: RecyclerAdapterItem)
    {
        // add new entry
        itemList.add(index, newItem)

        // then notify change at added position
        adapter.notifyItemInserted(index)
    }

    protected fun updateEntry(newItem: Item, includeUnused: Boolean, adapter: RecyclerAdapterItem)
    {
        // 1. check if item is already in List
        var index = itemList.indexOf(itemList.find { x -> x.name == newItem.name })

        // 2. if so, remove it
        removeOldEntry(index, adapter)

        // 3. check if unused items should be included
        if(!includeUnused)
        {
            // 4. check if usagetime is 0
            if(!newItem.wasUsed)
            {
                // 5. if so, return
                return
            }
        }

        // 6. find out where in the List the new entry fits (sorted descending by usage time)
        index = getNewEntryIndex(newItem)

        //7. the add the new entry to the list
        addNewEntry(newItem, index, adapter)
    }

    protected fun getNewEntryIndex(itemToInsert: Item): Int
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
package com.example.screentime

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapterAppItem () : RecyclerView.Adapter<RecyclerAdapterAppItem.MyViewHolder>()
{
    var appList = ArrayList<App>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val ivAppIcon: ImageView = itemView.findViewById(R.id.ivAppIcon)
        val tvAppName: TextView = itemView.findViewById(R.id.tvAppName)
        val tvUsageTime: TextView = itemView.findViewById(R.id.tvUsageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterAppItem.MyViewHolder
    {
        //Layout Inflater creates an Object from the "Blueprint" of a Layout Class: inflate turns Object into ViewObject
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterAppItem.MyViewHolder, position: Int)
    {
        val currentItem = appList[position]
        holder.ivAppIcon.setImageDrawable(currentItem.appIcon)
        holder.tvAppName.text = currentItem.appName
        holder.tvUsageTime.text = currentItem.usageTime
    }

    override fun getItemCount(): Int
    {
        return appList.size
    }

    public fun addItem(icon: Drawable?, name: String, usage: String, category: Int)
    {
        // Check if item is already in list (by name)
        var index = appList.indexOf(appList.find { x -> x.appName == name })

        if(index != -1)
        {
            // if it is: remove the old entry
            appList.removeAt(index)
            // then notify change at removed position
            this.notifyItemRemoved(index);
        }

        // find out where in the List the new entry fits (sorted descending by usage time)
        val newAppItem = App(icon, name, usage, category)
        index = getNewItemIndex(newAppItem)

        // add new entry
        appList.add(index, newAppItem)

        // then notify change at added position
        this.notifyItemInserted(index)
    }

    private fun getNewItemIndex(itemToInsert: App): Int
    {
        for(item : App in appList)
        {
            // ToDo: Write Comparator for String time!!
            if(item.usageTime < itemToInsert.usageTime)
            {
                return appList.indexOf(item)
            }
        }
        return appList.size;
    }

}
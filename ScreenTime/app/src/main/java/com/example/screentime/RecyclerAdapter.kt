package com.example.screentime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter () : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>()
{
    var itemList = ArrayList<ExampleItem>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvText1: TextView = itemView.findViewById(R.id.tvText1)
        val tvText2: TextView = itemView.findViewById(R.id.tvText2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MyViewHolder
    {
        //Layout Inflater creates an Object from the "Blueprint" of a Layout Class: inflate turns Object into ViewObject
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
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

    public fun addItem(icon: Int, name: String, text: String, time: Float)
    {
        // Check if item is already in list (by name)
        var index = itemList.indexOf(itemList.find { x -> x.text1 == name })

        if(index != -1)
        {
            // if it is: remove the old entry
            itemList.removeAt(index)
            // then notify change at removed position
            this.notifyItemRemoved(index);
        }

        // find out where in the List the new entry fits (sorted descending by usage time)
        val newItem = ExampleItem(icon, name, text, time)
        index = getNewItemIndex(newItem)

        // add new entry
        itemList.add(index, newItem)

        // then notify change at added position
        this.notifyItemInserted(index)
    }

    private fun getNewItemIndex(itemToInsert: ExampleItem): Int
    {
        for(item : ExampleItem in itemList)
        {
            if(item.time < itemToInsert.time)
            {
                return itemList.indexOf(item)
            }
        }
        return itemList.size;
    }

}
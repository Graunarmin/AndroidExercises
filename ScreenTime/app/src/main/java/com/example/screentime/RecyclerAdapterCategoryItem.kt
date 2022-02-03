package com.example.screentime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterCategoryItem() : RecyclerView.Adapter<RecyclerAdapterCategoryItem.CategoryViewHolder>()
{
    var categoryList = ArrayList<AppCategory>()

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val ivAppIcon: ImageView = itemView.findViewById(R.id.ivAppIcon)
        val tvAppName: TextView = itemView.findViewById(R.id.tvAppName)
        val tvUsageTime: TextView = itemView.findViewById(R.id.tvUsageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterCategoryItem.CategoryViewHolder
    {
        //Layout Inflater creates an Object from the "Blueprint" of a Layout Class: inflate turns Object into ViewObject
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterCategoryItem.CategoryViewHolder, position: Int)
    {
        val currentItem = categoryList[position]
        holder.ivAppIcon.setImageResource(currentItem.categoryIcon)
        holder.tvAppName.text = currentItem.categoryName
        holder.tvUsageTime.text = currentItem.categoryTime
    }

    override fun getItemCount(): Int
    {
        return categoryList.size
    }

    //ToDo: Sort the App List into Categories and sum up the usage time

}
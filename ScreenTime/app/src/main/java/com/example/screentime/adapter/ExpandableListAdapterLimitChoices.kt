package com.example.screentime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.screentime.R
import com.example.screentime.categories.CategoriesMap

/**
 * Adapter for the Expandable List used in the LimitActivity
 *
 * @param [context] the context in which the expandable list exists
 * @param [categories] the map that links all Categoires to their respective apps
 */

private const val TAG: String = "com.example.screentime.adapter.ExpandableListAdapterLimitChoices"
class ExpandableListAdapterLimitChoices(var context: Context,
                                        var categories : CategoriesMap) : BaseExpandableListAdapter()
{
    override fun getGroupCount(): Int
    {
        return categories.size
    }

    override fun getChildrenCount(groupPosition: Int): Int
    {
        return categories.appCountAt(groupPosition)
    }

    override fun getGroup(groupPosition: Int): Any
    {
        return categories.categoryName(groupPosition)
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any
    {
        return categories.getAppName(groupPosition, childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long
    {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long
    {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean
    {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View
    {
        var convView = convertView
        val groupName = getGroup(groupPosition)

        if(convView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convView = inflater.inflate(R.layout.expandable_list_item_group, null)
        }

        val tvGroupCategoryName = convView!!.findViewById<TextView>(R.id.tvGroupCategoryName)
        tvGroupCategoryName.text = groupName.toString()

        return convView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View
    {
        var convView = convertView
        val childName = getChild(groupPosition, childPosition)

        if(convView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convView = inflater.inflate(R.layout.expandable_list_item_child, null)
        }

        val tvChildAppName = convView!!.findViewById<TextView>(R.id.tvChildAppName)
        tvChildAppName.text = childName.toString()

        return convView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean
    {
        return true
    }
}
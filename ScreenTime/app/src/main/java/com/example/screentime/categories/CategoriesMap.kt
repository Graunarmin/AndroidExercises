package com.example.screentime.categories

import com.example.screentime.ScreenTimeApp
import com.example.screentime.items.CategoryItem
import java.util.*
import kotlin.collections.ArrayList

/**
 * Holds an EnumMap of all AppCategories mapped to their respective Apps.
 * Is used by the ExpandableListView to easiliy access the Groups (Categories) and Children (respective Apps)
 */
private const val TAG = "com.example.screentime.categories.categoriesmap"
class CategoriesMap()
{
    var categoriesMap : EnumMap<AppCategory, ArrayList<String>> = EnumMap(AppCategory::class.java)

    init
    {
        val allCategories = ScreenTimeApp.appInstance.categoryList
        for(category in AppCategory.values())
        {
            val categoryItem = allCategories.getItemByName(category.printableName) as CategoryItem

            val apps = categoryItem.containedApps
            val names : ArrayList<String> = ArrayList()

            for (app in apps){
                names.add(app.itemName)
            }
            names.sort()

            categoriesMap[category] = names
        }
    }

    private val categoriesOrder = ArrayList(categoriesMap.keys)

    val size : Int
        get() = categoriesMap.size

    fun appCountAt(index: Int) : Int
    {
        return categoriesMap[categoriesOrder[index]]!!.size
    }

    fun categoryName(index: Int) : String
    {
        return categoriesOrder[index].printableName
    }

    fun getAppName(catIndex: Int, appIndex: Int) : String
    {
        return categoriesMap[categoriesOrder[catIndex]]!![appIndex]
    }
}
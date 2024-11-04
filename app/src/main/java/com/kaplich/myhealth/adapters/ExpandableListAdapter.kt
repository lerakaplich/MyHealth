package com.kaplich.myhealth.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class ExpandableListAdapter(private val context: Context, private val data: HashMap<String, List<String>>) : BaseExpandableListAdapter() {

    private val headers: List<String> = data.keys.toList()

    override fun getGroupCount(): Int {
        return headers.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data[headers[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): String {
        return headers[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return data[headers[groupPosition]]?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val listItem = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        val textView = listItem.findViewById<TextView>(android.R.id.text1)
        textView.text = getGroup(groupPosition)
        return listItem
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val listItem = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        val textView = listItem.findViewById<TextView>(android.R.id.text1)
        textView.text = getChild(groupPosition, childPosition)
        return listItem
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}

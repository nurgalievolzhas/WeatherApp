package com.example.weatherapp.common.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


@Suppress("UNCHECKED_CAST")
fun RecyclerView.getGroupieAdapter(): GroupAdapter<GroupieViewHolder> =
    adapter as GroupAdapter<GroupieViewHolder>

//inline var View.isVisible: Boolean
//    get() = visibility == View.VISIBLE
//    set(value) {
//        visibility = if (value) View.VISIBLE else View.GONE
//    }
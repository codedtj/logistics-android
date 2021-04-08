package com.example.duoblogistics.internal.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.duoblogistics.internal.data.RecyclerItem

@BindingAdapter("items")
fun setRecyclerViewItems(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?
) {
    var adapter = (recyclerView.adapter as? BaseRecyclerViewerAdapter)
    if (adapter == null) {
        adapter = BaseRecyclerViewerAdapter()
        recyclerView.adapter = adapter
    }

    adapter.updateData(items.orEmpty())
}
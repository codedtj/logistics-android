package com.example.duoblogistics.ui.storedItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.duoblogistics.ui.main.MainActivity
import com.example.duoblogistics.ui.main.MainViewModel
import com.example.duoblogistics.R

/**
 * A fragment representing a list of Items.
 */
class CodesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_codes_list, container, false)

        mainViewModel = (this.activity as MainActivity).mainViewModel

        val codesViewAdapter = CodesViewAdapter(mainViewModel);
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)

                adapter = codesViewAdapter
            }
        }

//        val codesAdapter = CodesViewAdapter()
//        codesRecycleView.layoutManager = LinearLayoutManager(this.context)
//        codesRecycleView.adapter = categoriesAdapter
//        codesRecycleView.itemAnimator = DefaultItemAnimator()

//
//        dapter = CodesViewAdapter();
//
        mainViewModel.codes.observe(viewLifecycleOwner, Observer { codes ->
            if (codes !== null)
                codesViewAdapter.setData(codes);
        })

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CodesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
package com.example.duoblogistics.ui.trips.storedItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.R
import com.example.duoblogistics.databinding.FragmentStoredItemBinding
import com.example.duoblogistics.databinding.FragmentTripBinding
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class StoredItemFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: FragmentStoredItemBinding

    private lateinit var tripsViewModel: TripsViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoredItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tripsViewModel.storedItemInfo.observe(viewLifecycleOwner, {
            binding.storedItemHeight.text = "Высота: " + it.height.toString()
            binding.storedItemWidth.text = "Ширина: " + it.width.toString()
            binding.storedItemLength.text = "Длина: " + it.length.toString()
            binding.storedItemWeight.text = "Вес: " + it.weight.toString()
        })

        tripsViewModel.selectedStoredItem?.apply {
            tripsViewModel.getStoredItemInfo(stored_item_info_id)
        }
    }
}
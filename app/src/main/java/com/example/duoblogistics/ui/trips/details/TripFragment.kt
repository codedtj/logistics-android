package com.example.duoblogistics.ui.trips.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.databinding.FragmentTripBinding
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class TripFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: FragmentTripBinding

    private lateinit var tripsViewModel: TripsViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        tripsViewModel.selectedTrip?.apply {
            tripsViewModel.fetchTripStoredItems(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTripBinding.inflate(inflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
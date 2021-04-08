package com.example.duoblogistics.ui.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.databinding.FragmentTripsBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class TripsFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()

    private lateinit var tripsViewModel: TripsViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    private lateinit var binding: FragmentTripsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
//        return inflater.inflate(R.layout.fragment_trips, container, false)
        binding = FragmentTripsBinding.inflate(inflater)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TripsAdapter()
        binding.tripsRv.adapter = adapter

        tripsViewModel.trips.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        tripsViewModel.fetchTrips()
    }
}


package com.example.duoblogistics.ui.trips.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duoblogistics.databinding.FragmentTripsBinding
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class TripsFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()

    private lateinit var tripsViewModel: TripsViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    private lateinit var binding: FragmentTripsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        tripsViewModel.fetchTrips()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTripsBinding.inflate(inflater)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TripsAdapter(this, tripsViewModel)
        binding.tripsRv.adapter = adapter
        binding.tripsRv.itemAnimator = null

        val divider = DividerItemDecoration(binding.tripsRv.context,
            LinearLayoutManager(this.context).orientation
        )

        binding.tripsRv.addItemDecoration(divider)

        tripsViewModel.trips.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        tripsViewModel.getTrips()
    }
}


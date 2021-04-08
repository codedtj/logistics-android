package com.example.duoblogistics.ui.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.R
import com.example.duoblogistics.BR
import com.example.duoblogistics.databinding.FragmentTripsBinding
import com.example.duoblogistics.internal.data.RecyclerItem
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
        binding = FragmentTripsBinding.inflate(inflater).also {
            it.viewModel = tripsViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        return  binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tripsViewModel.fetchTrips()

//        binding.testTextBox.text = "none";
//        tripsViewModel.counter.observe(viewLifecycleOwner, {v ->
//            binding.testTextBox.text = v.toString()
//        });

//        tripsViewModel.trips.observe(viewLifecycleOwner, {trips ->
//            trips.map {
//                trip ->
//                RecyclerItem(
//                    data = trip,
//                    variableId = BR.trip,
//                    layoutId = R.layout.viewholder_trip
//                )
//            }
//        })
    }
}


package com.example.duoblogistics.ui.trips.actions.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.duoblogistics.R
import com.example.duoblogistics.databinding.FragmentActionsListBinding
import com.example.duoblogistics.ui.trips.actions.ActionsViewModel
import com.example.duoblogistics.ui.trips.actions.ActionsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ActionsListFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var actionsViewModel: ActionsViewModel

    private val actionsViewModelFactory: ActionsViewModelFactory by instance()

    private lateinit var binding: FragmentActionsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActionsListBinding.inflate(inflater)
        return binding.root
    }

}
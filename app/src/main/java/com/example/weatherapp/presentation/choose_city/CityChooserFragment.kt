package com.example.weatherapp.presentation.choose_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.common.constants.ARGConstants.ARG_CITY_DATA
import com.example.weatherapp.common.ext.getGroupieAdapter
import com.example.weatherapp.common.utils.EventObserver
import com.example.weatherapp.databinding.FragmentCityChooserBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityChooserFragment : Fragment() {

    private var _binding: FragmentCityChooserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CityChooserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityChooserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadCities()
        initViews()
        observeViewModel()
    }

    private fun initViews() = with(binding) {
        rvCityChooser.adapter = GroupAdapter<GroupieViewHolder>()
    }

    private fun observeViewModel() = with(viewModel) {
        cities.observe(viewLifecycleOwner) {
            binding.rvCityChooser.getGroupieAdapter().update(it)
        }
        navigateToWeatherInfo.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(
                R.id.weatherInfoFragment,
                bundleOf(
                    ARG_CITY_DATA to it
                )
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

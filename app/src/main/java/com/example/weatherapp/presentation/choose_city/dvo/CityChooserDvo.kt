package com.example.weatherapp.presentation.choose_city.dvo

import android.view.View
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemCityBinding
import com.example.weatherapp.presentation.choose_city.args.CityArgs
import com.xwray.groupie.viewbinding.BindableItem


class CityDvo(
    val title: String,
    val cityData: CityArgs,
    val onClick: (CityArgs) -> Unit
) : BindableItem<ItemCityBinding>() {

    override fun getLayout() = R.layout.item_city

    override fun bind(binding: ItemCityBinding, position: Int) {
        binding.tvTitle.text = title
        binding.root.setOnClickListener {
            onClick.invoke(cityData)
        }
    }

    override fun initializeViewBinding(view: View): ItemCityBinding {
        return ItemCityBinding.bind(view)
    }
}

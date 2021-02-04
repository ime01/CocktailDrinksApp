package com.flowz.introtooralanguage.adapters

import androidx.recyclerview.widget.DiffUtil
import com.flowz.printfuljobtask.models.Drink

class DrinksDiffCallback : DiffUtil.ItemCallback<Drink>(){


    override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
        return oldItem.idDrink == newItem.idDrink
    }

    override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
        return oldItem == newItem
    }

}
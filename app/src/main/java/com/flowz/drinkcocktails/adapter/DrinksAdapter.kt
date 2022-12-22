package com.flowz.introtooralanguage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.flowz.drinkcocktails.R
import com.flowz.drinkcocktails.models.Drink
import kotlinx.android.synthetic.main.cocktail_list_item.view.*


class DrinksAdapter(val listener: DrinksViewHolder.DrinksRowClickListener) :ListAdapter<Drink, DrinksAdapter.DrinksViewHolder>(DrinksDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cocktail_list_item, parent, false)
        return DrinksViewHolder(view, listener)

    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {

        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(getItem(position))
        }

    }

    class DrinksViewHolder(view: View, val listener: DrinksRowClickListener) :
        RecyclerView.ViewHolder(view) {

        fun bind(drink: Drink) {

            itemView.cocktail_title.text = drink.strDrink
            itemView.cocktail_id.text =  "cocktail id:"  + drink.idDrink
            val imageIcon = itemView.cocktail_icon
            val imageUri = drink.strDrinkThumb

            Glide.with(imageIcon)
                .load(imageUri)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_no_drinks_24)
                .error(R.drawable.ic_baseline_no_drinks_24)
                .fallback(R.drawable.ic_baseline_no_drinks_24)
                .into(imageIcon)

        }

        interface DrinksRowClickListener {
            fun onItemClickListener(drink: Drink)

        }

    }

}
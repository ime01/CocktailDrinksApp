package com.flowz.drinkcocktails.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.flowz.drinkcocktails.R
import com.flowz.drinkcocktails.models.Drink
import kotlinx.android.synthetic.main.fragment_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private var drink: Drink? = null

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//
            drink = DetailFragmentArgs.fromBundle(it).cocktaildetails
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cocktail_title.setText(drink?.strDrink)
        cocktail_id.setText("Drink id is" + drink?.idDrink)
        cocktail_ingredient_measure1.setText(drink?.strMeasure1 + " " + drink?.strIngredient1)
        cocktail_ingredient_measure2.setText(drink?.strMeasure2 + " " + drink?.strIngredient2)
        cocktail_ingredient_measure3.setText(drink?.strMeasure3 + " " + drink?.strIngredient3)
        cocktail_ingredient_measure4.setText(drink?.strMeasure4 + " " + drink?.strIngredient4)
        cocktail_ingredient_measure5.setText(drink?.strMeasure5 + " " + drink?.strIngredient5)
        cocktail_ingredient_measure6.setText(drink?.strMeasure6 + " " + drink?.strIngredient6)
        cocktail_ingredient_measure7.setText(drink?.strMeasure7 + " " +drink?.strIngredient7)
        cocktail_instructions.setText(drink?.strInstructions)


        Glide.with(cocktail_icon)
            .load(drink?.strDrinkThumb)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_no_drinks_24)
            .error(R.drawable.ic_baseline_no_drinks_24)
            .fallback(R.drawable.ic_baseline_no_drinks_24)
            .into(cocktail_icon)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
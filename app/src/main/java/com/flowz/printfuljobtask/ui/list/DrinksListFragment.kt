package com.flowz.printfuljobtask.ui.list

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.flowz.forcastapp.display.base.ScopedFragment
import com.flowz.introtooralanguage.adapters.DrinksAdapter
import com.flowz.printfuljobtask.R
import com.flowz.printfuljobtask.drinkroomdb.DrinkDatabase
import com.flowz.printfuljobtask.drinksrepository.DrinksCocktailsRepository
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.network.DrinksRetrieverApiClient
//import com.flowz.printfuljobtask.roomdb.DrinksDatabase
import com.flowz.printfuljobtask.utils.getConnectionType
import com.flowz.printfuljobtask.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 *
 *
 *Hi Laura
  I will be fetching a list of  Margirita Cocktail drinks from this API https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita

*/

class ListFragment : ScopedFragment(), DrinksAdapter.OraNumViewHolder.DrinksRowClickListener {
    lateinit var drinkdadapter : DrinksAdapter
    lateinit var drinksviewModel: DrinksCocktailsViewModel

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmer_frame_layout.startShimmerAnimation()
        showWelcomeMarqueeText()

        val application= requireNotNull(activity).application
        val database = DrinkDatabase.invoke(application)
        val drinkDao = database.drinkDao()

        val drinksrepository = DrinksCocktailsRepository(DrinksRetrieverApiClient(), drinkDao )

        val drinksviewModelFactory = DrinksCocktailViewModelFactory(drinksrepository)
        drinksviewModel = ViewModelProviders.of(this.requireActivity(), drinksviewModelFactory).get(DrinksCocktailsViewModel::class.java)


        if (getConnectionType(context!!)) {

            drinksviewModel.drinksFromNetwork.observe(viewLifecycleOwner, Observer {
                loadRecyclerView(it)
                showSnackbar(welcome_text_marquee, "Data Feteched From Network")
            })

            drinksviewModel.setUp()

        } else {
            AlertDialog.Builder(this.requireContext()).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again, Your Data is currently fetched from Local Room Database")
                .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()

            drinksviewModel.drinksFromLocalDb.observe(viewLifecycleOwner, Observer {

                val mDrinks:Drinks = Drinks(it)

                Log.e("DbValuesShown", "$mDrinks")
                loadRecyclerViewFromDb(mDrinks)
                showSnackbar(welcome_text_marquee, "Data Feteched From Local Room Databse")

        })

        }
}

    fun loadRecyclerView(drinksrepo: Drinks){

        drinkdadapter = DrinksAdapter(this@ListFragment)
        drinkdadapter.submitList(drinksrepo.drinks)

        rv_drinks.layoutManager = LinearLayoutManager(this.context)
        rv_drinks.adapter = drinkdadapter
        val decoration = DividerItemDecoration(context, VERTICAL)
        rv_drinks.addItemDecoration(decoration)

        shimmer_frame_layout.stopShimmerAnimation()
        shimmer_frame_layout.visibility = View.GONE
    }

        fun loadRecyclerViewFromDb(drinks : Drinks){

            drinkdadapter = DrinksAdapter(this@ListFragment)
            drinkdadapter.submitList(drinks.drinks)

            rv_drinks.layoutManager = LinearLayoutManager(this.context)
            rv_drinks.adapter = drinkdadapter
            val decoration = DividerItemDecoration(context, VERTICAL)
            rv_drinks.addItemDecoration(decoration)

            shimmer_frame_layout.stopShimmerAnimation()
            shimmer_frame_layout.visibility = View.GONE
        }


        fun showWelcomeMarqueeText(){
        welcome_text_marquee.setSingleLine()
        welcome_text_marquee.ellipsize = TextUtils.TruncateAt.MARQUEE
        welcome_text_marquee.marqueeRepeatLimit = -1
        welcome_text_marquee.isSelected = true
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClickListener(drink: Drink) {

        val sendCocktailDetails = ListFragmentDirections.actionListFragmentToDetailFragment()
        sendCocktailDetails.cocktaildetails = drink
        Navigation.findNavController(view!!).navigate(sendCocktailDetails)
    }
}
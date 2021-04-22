package com.flowz.printfuljobtask.ui.list

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.flowz.introtooralanguage.adapters.DrinksAdapter
import com.flowz.printfuljobtask.R
import com.flowz.printfuljobtask.models.Drink
import com.flowz.printfuljobtask.models.Drinks
import com.flowz.printfuljobtask.utils.*
//import com.flowz.printfuljobtask.roomdb.DrinksDatabase
import dagger.hilt.android.AndroidEntryPoint
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
@AndroidEntryPoint
class ListFragment : Fragment(), DrinksAdapter.DrinksViewHolder.DrinksRowClickListener {
    lateinit var drinkdadapter: DrinksAdapter
    lateinit var drinkType: String

    private val drinksviewModel by viewModels<DrinksCocktailsViewModel>()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSettings()

        showWelcomeMarqueeText()
        drinkdadapter = DrinksAdapter(this@ListFragment)

        drinksviewModel.drinksFromLocalDb.observe(viewLifecycleOwner, Observer {

            val mDrinks: Drinks = Drinks(it)

            Log.e("DbValuesShown", "$mDrinks")
            loadRecyclerView(mDrinks)
            showSnackbar(welcome_text_marquee, " Drinks Feteched From Local Room Databse")

        })

        fetch_drinks.setOnClickListener {

            if (TextUtils.isEmpty(drink_name.text.toString())) {
                drink_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else {
                drinkType = drink_name.text.toString().trim()

                if (getConnectionType(requireContext())) {
                    drinksviewModel.searchDrnkTypeFromNetwork(drinkType)
                    drinksviewModel.drinkNetworkstatus.observe(viewLifecycleOwner, Observer {state->

                        state?.also {
                            when (it){
                                DrinkApiStatus.ERROR->{
                                    showSnackbar(welcome_text_marquee, getString(R.string.not_found))
                                    error_image.isVisible = true
                                }
                                DrinkApiStatus.LOADING->{
                                    shimmer_frame_layout.startShimmerAnimation()
                                    error_image.isVisible = false
                                }
                                DrinkApiStatus.DONE->{
                                    showSnackbar(welcome_text_marquee, "Drink Type Feteched From Network")
                                    drinksviewModel.drinksFromNetwork.observe(viewLifecycleOwner, Observer {

                                        loadRecyclerView(it)
                                        EspressoIdlingResource.decrement()

                                    })
                                }
                            }
                        }
                    })
                } else {
                    AlertDialog.Builder(this.requireContext()).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again, Your Data is currently fetched from Local Room Database")
                        .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }
        }
    }

    private fun loadSettings() {
      val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val nightMode = sp.getBoolean("nightorday", false)

        if (nightMode){
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

                inflater.inflate(R.menu.menu_layout, menu)
                val menuItem = menu!!.findItem(R.id.search_oraword)
                val searchView = menuItem.actionView as SearchView

                searchView.onQueryTextChanged {
                    searchDatabase(it)
                    Log.d(TAG, "Search Successful")
                }

            }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.settings ->{
                findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

            private fun searchDatabase(query: String) {
                val searchQuery = "%$query%"
                drinksviewModel.searchDrinkFromDb(searchQuery)
                    .observe(viewLifecycleOwner, Observer { list ->
                        list.let {
                            drinkdadapter.submitList(it)
                        }

                    })
            }

            fun loadRecyclerView(drinksrepo: Drinks) {

                error_image.isVisible = false
                drinkdadapter.submitList(drinksrepo.drinks)
                rv_drinks.layoutManager = LinearLayoutManager(this.context)
                rv_drinks.adapter = drinkdadapter
                val decoration = DividerItemDecoration(context, VERTICAL)
                rv_drinks.addItemDecoration(decoration)

                shimmer_frame_layout.stopShimmerAnimation()
                shimmer_frame_layout.visibility = View.GONE
            }

            fun showWelcomeMarqueeText() {
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
            const val TAG = "Drinks Fragment"

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

                val sendCocktailDetails =
                    ListFragmentDirections.actionListFragmentToDetailFragment()
                sendCocktailDetails.cocktaildetails = drink
                Navigation.findNavController(requireView()).navigate(sendCocktailDetails)
            }
        }

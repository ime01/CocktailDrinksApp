package com.flowz.drinkcocktails.ui.list

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.flowz.introtooralanguage.adapters.DrinksAdapter
import com.flowz.drinkcocktails.R
import com.flowz.drinkcocktails.models.Drink
import com.flowz.drinkcocktails.models.Drinks
import com.flowz.drinkcocktails.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*




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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSettings()
        swipeToDeleteNumber()
        showSnackbar(welcome_text_marquee, "Search and make the best exotic drinks")


        showWelcomeMarqueeText()
        drinkdadapter = DrinksAdapter(this@ListFragment)

        drinksviewModel.drinksFromLocalDb.observe(viewLifecycleOwner, Observer {

            val mDrinks: Drinks = Drinks(it)

            Log.e("DbValuesShown", "$mDrinks")
            loadRecyclerView(mDrinks)
        })

        fetch_drinks.setOnClickListener {

            if (TextUtils.isEmpty(drink_name.text.toString())) {
                drink_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else {
                drinkType = drink_name.text.toString().trim()

                if (getConnectionType(requireContext())) {
                    drinksviewModel.searchDrnkTypeFromNetwork(drinkType)
                    drinksviewModel.drinksFromNetwork.observe(viewLifecycleOwner, Observer {drinks->

                        drinks?.also {
                            when (it.status){
                                Status.ERROR->{
                                    showSnackbar(welcome_text_marquee, it.message!!)
                                    error_image.isVisible = true
                                }
                                Status.LOADING->{
                                    shimmer_frame_layout.startShimmerAnimation()
                                    error_image.isVisible = false
                                }
                                Status.SUCCESS->{
                                    showSnackbar(welcome_text_marquee, "Drink Type Feteched From Network")
                                    loadRecyclerView(it.data)
                                    EspressoIdlingResource.decrement()
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

    private fun swipeToDeleteNumber() {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val drink = drinkdadapter.currentList[viewHolder.adapterPosition]

                    drinksviewModel.deleteDrink(drink)
                    Snackbar.make(rv_drinks, " ${drink.strDrink} Deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO"){ drinksviewModel.insertDrink(drink) }.show()

            }

        }).attachToRecyclerView(rv_drinks)

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

            R.id.delete_all ->{
                AlertDialog.Builder(this.requireContext()).setTitle(getString(R.string.delete_all_title))
                        .setMessage(getString(R.string.sure_to_delete_all))
                        .setPositiveButton(getString(R.string.ok)) { _, _ ->
                            drinksviewModel.clearAllDrinks()
                        }
                        .setNegativeButton(getString(R.string.cancel)){ _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()


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

            fun loadRecyclerView(drinksrepo: Drinks?) {

                error_image.isVisible = false
                drinkdadapter.submitList(drinksrepo?.drinks)
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

            const val TAG = "Drinks Fragment"

        }

            override fun onItemClickListener(drink: Drink) {

                val sendCocktailDetails =
                    ListFragmentDirections.actionListFragmentToDetailFragment()
                sendCocktailDetails.cocktaildetails = drink
                Navigation.findNavController(requireView()).navigate(sendCocktailDetails)
            }
        }

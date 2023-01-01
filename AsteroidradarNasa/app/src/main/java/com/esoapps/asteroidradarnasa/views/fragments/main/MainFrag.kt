package com.esoapps.asteroidradarnasa.views.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.esoapps.asteroidradarnasa.App
import com.esoapps.asteroidradarnasa.R
import com.esoapps.asteroidradarnasa.databinding.MainFragBinding
import com.esoapps.asteroidradarnasa.network.api.getNextSevenDaysFormattedDates
import com.esoapps.asteroidradarnasa.viewModels.MainViewModel
import com.esoapps.asteroidradarnasa.viewModels.MainViewModelFactory
import com.esoapps.asteroidradarnasa.views.adapters.AsteroidAdapter


class MainFrag : Fragment(), Toolbar.OnMenuItemClickListener {

    private var binding: MainFragBinding? = null


    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity?.application as App).repository)

    }


    private var asteroidAdapter: AsteroidAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MainFragBinding.inflate(inflater)

        //setHasOptionsMenu(true)//DEPRECATED

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.lifecycleOwner = this
        //binding!!.lifecycleOwner = viewLifecycleOwner

        binding!!.viewModel = viewModel

        binding!!.statusLoadingWheel.visibility = View.VISIBLE


        setAllViewModelObservers()

        setRecyclerView()



        setUpMenu()


    }


    private fun setAllViewModelObservers() {
        viewModel.pictureOfDay?.observe(viewLifecycleOwner) { pictureOfDay ->

            if (pictureOfDay != null) {
                binding!!.pictureOfDay = pictureOfDay
            }
        }



        viewModel.allDbAsteroid.observe(viewLifecycleOwner) {
            if (it != null) {
                binding!!.statusLoadingWheel.visibility = View.GONE
                asteroidAdapter?.submitList(it)

            }
        }

    }


    private fun setRecyclerView() {
        asteroidAdapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {asteroid->

            val action = MainFragDirections.actionShowDetail(asteroid)
            findNavController().navigate(action)
        })
        binding?.asteroidRecycler?.adapter = asteroidAdapter

    }

    private fun setUpMenu() {
        binding!!.toolbarList.title = getString(R.string.app_name)
        binding!!.toolbarList.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.c_white))
        binding!!.toolbarList.inflateMenu(R.menu.main_overflow_menu)

        binding!!.toolbarList.setOnMenuItemClickListener(this)

    }


    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {

        when (menuItem?.itemId) {
            R.id.show_seven -> {

                //WEEK:
                viewModel.getAllDbAsteroidByDurationDates()
                return true
            }

            R.id.show_today -> {
                //JUST TODAY:
                viewModel.getDbAsteroidByDate()

                return true
            }

            R.id.show_all -> {

                //ALL:
                viewModel.getAllDbAsteroid()
                return true
            }

        }
        return false
    }


    //DEPRECATED:
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.main_overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return true
//    }


}
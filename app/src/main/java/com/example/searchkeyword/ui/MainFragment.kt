package com.example.searchkeyword.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchkeyword.R
import com.example.searchkeyword.adapter.ListAdapter
import com.example.searchkeyword.adapter.OnItemClickedListener
import com.example.searchkeyword.model.Albummatches
import com.example.searchkeyword.utils.Utilities
import com.example.searchkeyword.viewModel.ViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(),
    OnItemClickedListener {

    //spinner selected item
    var categoryItem: String? = null

    // navigation controller obj
    var nav: NavController? = null

    // bundle obj
    var bundle = Bundle()

    companion object {
        const val URL: String = "url"
    }

    var viewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nav = Navigation.findNavController(view)
        // setting up spinner adapter
        spinner.adapter = ArrayAdapter.createFromResource(
            activity?.applicationContext!!,
            R.array.categories_list,
            android.R.layout.simple_spinner_dropdown_item
        )

        // spinner item selected listener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                categoryItem = resources.getString(R.string.album)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoryItem = parent?.getItemAtPosition(position).toString()
            }
        }

        search_view.setOnQueryTextListener(object : OnQueryTextListener {

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard(search_view)

                if (!TextUtils.isEmpty(query.toString())) {
                    progress_bar.visibility = View.VISIBLE
                    search(query.toString(), search_view)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun search(searchContent: String, view: SearchView) {
        if (Utilities().isOnline(requireContext().applicationContext)) {
            when {
                categoryItem.equals(resources.getString(R.string.album)) -> {
                    searchAlbumContent(searchContent, view)
                }
                categoryItem.equals(resources.getString(R.string.artist)) -> {
                    searchArtistContent(searchContent, view)
                }
                categoryItem.equals(resources.getString(R.string.songs)) -> {
                    searchSongContent(searchContent, view)
                }
            }
        } else {
            view?.let {
                Utilities().showSnackBar(
                    it,
                    resources.getString(R.string.network_error)
                )
            }
        }
    }

    private fun searchAlbumContent(searchContent: String, view: SearchView) {
        viewModel?.getAlbumData(
            searchContent,
            getString(R.string.api_key),
            getString(R.string.json)
        )?.observe(requireActivity(), Observer<com.example.searchkeyword.model.Response> {
            Log.i("API_RESPONSE", "" + it)
            progress_bar.visibility = View.GONE
            if (it != null) {
                recycle_view.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = it?.let {
                        ListAdapter(
                            it,
                            this@MainFragment
                        )
                    }
                }
            } else {
                Utilities().showSnackBar(
                    view,
                    resources.getString(R.string.api_error)
                )
            }
        })
    }
    private fun searchArtistContent(searchContent: String, view: SearchView) {
        viewModel?.getArtistData(
            searchContent,
            getString(R.string.api_key),
            getString(R.string.json)
        )?.observe(requireActivity(), Observer<com.example.searchkeyword.model.Response> {
            Log.i("API_RESPONSE", "" + it)
            progress_bar.visibility = View.GONE
            if (it != null) {
                recycle_view.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = it?.let {
                        ListAdapter(
                            it,
                            this@MainFragment
                        )
                    }
                }
            } else {
                Utilities().showSnackBar(
                    view,
                    resources.getString(R.string.api_error)
                )
            }
        })
    }
    private fun searchSongContent(searchContent: String, view: SearchView) {
        viewModel?.getSongData(
            searchContent,
            getString(R.string.api_key),
            getString(R.string.json)
        )?.observe(requireActivity(), Observer<com.example.searchkeyword.model.Response> {
            Log.i("API_RESPONSE", "" + it)
            progress_bar.visibility = View.GONE
            if (it != null) {
                recycle_view.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = it?.let {
                        ListAdapter(
                            it,
                            this@MainFragment
                        )
                    }
                }
            } else {
                Utilities().showSnackBar(
                    view,
                    resources.getString(R.string.api_error)
                )
            }
        })
    }

    // hide system keyboard
    fun hideKeyboard(view: View) {
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onItemSelected(url: String) {
        if (Utilities().isOnline(requireContext().applicationContext)) {
            bundle.putString(URL, url)
            nav?.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        } else {
            view?.let { Utilities().showSnackBar(it, resources.getString(R.string.network_error)) }
        }
    }
}
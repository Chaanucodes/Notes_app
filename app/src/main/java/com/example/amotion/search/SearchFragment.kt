package com.example.amotion.search

import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.amotion.R
import com.example.amotion.database.NotesDatabase
import com.example.amotion.databinding.SearchFragmentBinding
import com.example.amotion.notes.MyNotesListener

class SearchFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<SearchFragmentBinding>(
            inflater, R.layout.search_fragment, container,false
        )

        binding.nothingYetAnimView.loop(true)
        binding.nothingYetAnimView.setMaxFrame(180)
        binding.nothingYetAnimView.playAnimation()

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val database = NotesDatabase.getInstance(application).notesDatabaseDao

        val viewModelFactory = SearchViewModelFactory(database, application)
        val searchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        val adapter = SearchResultsAdapter(MyNotesListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToEditNoteFragment(it)
            )
            dismiss()
        }, searchViewModel.stringKey, viewLifecycleOwner
        )
        binding.recyclerResults.adapter = adapter


        binding.imageButton.setOnClickListener {
            dismiss()
            activity?.onBackPressed()
        }

        binding.editTextSearch.doOnTextChanged { text, start, count, after ->

            if (text.toString().trim().isNotEmpty()){
                searchViewModel.searchData(text.toString().trim())
            }else{
                if(binding.nothingYetAnimView.visibility == View.GONE){
                    binding.nothingYetAnimView.visibility = View.VISIBLE
                    binding.recyclerResults.visibility = View.GONE
                }
            }

        }
        activity?.window?.decorView.apply {
            this?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        }

        activity?.window?.decorView?.setOnSystemUiVisibilityChangeListener { visibility ->
            Handler().postDelayed({
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                    activity?.window?.decorView.apply {
                        this?.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
                    }
                }
            }, 3000)
        }



        searchViewModel.searchResults.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                searchViewModel.stringKey.value = binding.editTextSearch.text.toString()
            }
            if(binding.recyclerResults.visibility == View.GONE){
                binding.nothingYetAnimView.visibility = View.GONE
                binding.recyclerResults.visibility = View.VISIBLE
            }
        })
        return binding.root
    }

}

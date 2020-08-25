package com.example.amotion.notes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.amotion.R
import com.example.amotion.database.NotesDatabase
import com.example.amotion.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    private lateinit var titleFragViewModel: TitleFragViewModel
    private lateinit var binding: FragmentTitleBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title, container, false
        )
        binding.lottieSearch.apply {
            playAnimation()
            loop(true)
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

        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDatabaseDao

        val viewModelFactory = TitleFragViewModelFactory(dataSource, application)
        titleFragViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TitleFragViewModel::class.java)

        binding.noteData = titleFragViewModel
        binding.lifecycleOwner = this


        val adapter = NotesListAdapter(clickListener = (MyNotesListener {
            titleFragViewModel.navigateToEditing(it)
        }), deleteClickListener = (MyNotesListener {
            titleFragViewModel.deleteOneNote(it)
        }), shareListener = (MyNotesListener {
            titleFragViewModel.shareOneNote(it)
        })
        )
        binding.recyclerList.adapter = adapter

        // Observers

        titleFragViewModel.navigateToEditing.observe(viewLifecycleOwner, Observer {
            navController
                .navigate(TitleFragmentDirections.actionTitleFragmentToEditNoteFragment(it))
        })

        titleFragViewModel.navigateForNewNote.observe(viewLifecycleOwner, Observer {
            navController
                .navigate(TitleFragmentDirections.actionTitleFragmentToEditNoteFragment(Long.MIN_VALUE))
        })

        titleFragViewModel.notes.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        titleFragViewModel.intentSharing.observe(viewLifecycleOwner, Observer {
            val intTitle = it.title
            val intSubTitle = it.subtitle
            val intContent = it.text

            val intent = Intent(Intent.ACTION_SEND)
                .apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT, "Title : $intTitle\n" +
                                "Subtitle : $intSubTitle\n" +
                                "Content : $intContent"
                    )
                }

            startActivity(intent)
        })


        // Listeners

        binding.exitAppButton.setOnClickListener {
            activity?.finish()
        }

        binding.lottieSearch.setOnClickListener {
            navController.apply {
                navigate(TitleFragmentDirections.actionTitleFragmentToSearchFragment())
            }
            binding.lottieSearch.pauseAnimation()
        }

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.about -> titleFragViewModel.callTheFragment(activity, DialogAboutFragment())

                R.id.contact -> titleFragViewModel.callTheFragment(
                    activity,
                    DialogContactFragment()
                )

                R.id.delete -> titleFragViewModel.callTheFragment(activity, DialogDeleteFrag())
            }
            true
        }
        return binding.root
    }


}

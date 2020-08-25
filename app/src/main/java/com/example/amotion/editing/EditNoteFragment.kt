package com.example.amotion.editing

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.amotion.R
import com.example.amotion.database.MyNotes
import com.example.amotion.database.NotesDatabase
import com.example.amotion.databinding.FragmentEditNoteBinding
/**
 * A simple [Fragment] subclass.
 */
class EditNoteFragment : Fragment() {


    lateinit var binding : FragmentEditNoteBinding
    private lateinit var navController : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_note, container, false
        )


        Log.i("hhha", "lesta : ${activity?.supportFragmentManager?.backStackEntryCount}")

        binding.doneButton.apply {
            playAnimation()
            repeatCount = 1
            speed = 0.7f
        }

        val application = requireNotNull(this.activity).application
        val database = NotesDatabase.getInstance(application).notesDatabaseDao

        val args = EditNoteFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = EditNoteViewModelFactory(database, application)
        val editViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EditNoteViewModel::class.java)

        binding.noteData = editViewModel

        binding.lifecycleOwner = this

        activity?.window?.decorView.apply {
            this?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE
        }

        activity?.window?.decorView?.setOnSystemUiVisibilityChangeListener { visibility ->

            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                activity?.window?.decorView.apply {
                    this?.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                }
            }
        }

        if (args.notePassed != Long.MIN_VALUE) {
            editViewModel.setCurrentNote(args.notePassed)
        }


        editViewModel.currentNote.observe(viewLifecycleOwner, Observer {
            if (!it.title.isNullOrEmpty()) {
                binding.titleEditText.setText(it.title)
                binding.subTitleEditText.setText(it.subtitle)
                binding.contentEditText.setText(it.text)
            }
        })

        
        binding.doneButton.setOnClickListener {
            if (args.notePassed != Long.MIN_VALUE) {
                editViewModel.updateDatabase(
                    binding.titleEditText.text.toString(),
                    binding.subTitleEditText.text.toString(),
                    binding.contentEditText.text.toString()
                )
                setEditTextsBlank(binding)
                navController
                    .navigate(EditNoteFragmentDirections.actionEditNoteFragmentToTitleFragment())
            } else {
                val newNote = MyNotes()
                if (!binding.titleEditText.text.isNullOrEmpty()) {
                    newNote.apply {
                        title = binding.titleEditText.text.toString()
                        subtitle = binding.subTitleEditText.text.toString()
                        text = binding.contentEditText.text.toString()
                    }
                    editViewModel.insertInDatabase(newNote)
                    navController
                        .navigate(EditNoteFragmentDirections.actionEditNoteFragmentToTitleFragment())
                } else {
                    Toast.makeText(activity, "Enter a title", Toast.LENGTH_SHORT).show()
                }
            }
            hideKeyboard(binding)
            activity?.window?.decorView.apply {
                this?.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            }


        }

//        binding.backButton.setOnClickListener {
//            hideKeyboard(binding)
//            findNavController()
//                .navigate(EditNoteFragmentDirections.actionEditNoteFragmentToTitleFragment())
//        }
        return binding.root
    }

    private fun setEditTextsBlank(binding: FragmentEditNoteBinding) {
        binding.titleEditText.setText("")
        binding.subTitleEditText.setText("")
        binding.contentEditText.setText("")
    }

    private fun hideKeyboard(binding: FragmentEditNoteBinding) {
        val mgr = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.hideSoftInputFromWindow(binding.contentEditText.windowToken, 0)
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard(binding)
    }

}

package com.example.amotion.notes

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.amotion.R
import com.example.amotion.database.NotesDatabase
import com.example.amotion.databinding.DeleteFragLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogDeleteFrag : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : DeleteFragLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.delete_frag_layout, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = NotesDatabase.getInstance(application).notesDatabaseDao

        binding.yesButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO){
                    dataSource.deleteData()
                }
            }
            dismiss()
        }

        binding.LayoutDeleteAlert.setOnClickListener {  }

        binding.noButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}
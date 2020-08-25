package com.example.amotion.notes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.amotion.R
import com.example.amotion.databinding.EmbeddedFragViewBinding

class DialogAboutFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val binding : EmbeddedFragViewBinding = DataBindingUtil.inflate(
            inflater, R.layout.embedded_frag_view, container, false)

        binding.okayButton.setOnClickListener {
            this.dismiss()
        }

        binding.layoutAboutAlert.setOnClickListener {  }
        return binding.root
    }

}
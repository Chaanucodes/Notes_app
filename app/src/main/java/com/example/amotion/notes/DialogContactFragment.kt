package com.example.amotion.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.amotion.R
import com.example.amotion.databinding.EmbeddedContactFragBinding

class DialogContactFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val binding : EmbeddedContactFragBinding = DataBindingUtil.inflate(
            inflater, R.layout.embedded_contact_frag, container, false)

        binding.okayButton.setOnClickListener {
            this.dismiss()
        }

        binding.layoutContactAlert.setOnClickListener {  }

        binding.lottieSendAnimView.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                this.putExtra(Intent.EXTRA_TEXT, " ")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("ruchir.thapliyal@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Feedback/ review/ issue on 'Your Notes' app")
                type = "text/plain"
            }

            startActivity(intent)
            this.dismiss()
        }
        return binding.root
    }

}
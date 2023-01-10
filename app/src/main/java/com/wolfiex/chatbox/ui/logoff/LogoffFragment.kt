package com.wolfiex.chatbox.ui.logoff

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wolfiex.chatbox.MainActivity
import com.wolfiex.chatbox.MainActivity2
import com.wolfiex.chatbox.R
import com.wolfiex.chatbox.databinding.FragmentLogoffBinding

class LogoffFragment : Fragment() {

    private var _binding: FragmentLogoffBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoffBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var btnLogoff = root.findViewById<Button>(R.id.button_logoff)

        btnLogoff.setOnClickListener {
            val intent = Intent()
            intent.setClass(activity!!, MainActivity::class.java)
            startActivity(intent);
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
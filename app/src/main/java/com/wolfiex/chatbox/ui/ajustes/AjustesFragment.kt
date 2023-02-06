package com.wolfiex.chatbox.ui.ajustes

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wolfiex.chatbox.MainActivity
import com.wolfiex.chatbox.MainActivity2
import com.wolfiex.chatbox.R
import com.wolfiex.chatbox.databinding.FragmentAjustesBinding

class AjustesFragment : Fragment() {

    private var _binding: FragmentAjustesBinding? = null
    val db = Firebase.firestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjustesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val btnCambiarEstado: Button = root.findViewById(R.id.cambiarEstadoBoton)

        btnCambiarEstado.setOnClickListener {
            val textoEstadoNuevo: EditText = root.findViewById(R.id.campoEntradaEstado)
            val user = Firebase.auth.currentUser

            Log.d("Texto Estado", textoEstadoNuevo.text.toString())
            if (!textoEstadoNuevo.text.toString().equals("")){
                val newData = hashMapOf(
                    "estado" to textoEstadoNuevo.text.toString()
                )

                if (user != null) {
                    db.collection("users").document(user.email.toString())
                        .set(newData, SetOptions.merge())
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                }
                val intent = Intent()
                intent.setClass(requireActivity(), MainActivity2::class.java)
                startActivity(intent);
            }

        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
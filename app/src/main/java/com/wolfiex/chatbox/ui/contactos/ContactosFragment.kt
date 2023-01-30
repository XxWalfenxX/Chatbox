package com.wolfiex.chatbox.ui.contactos

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wolfiex.chatbox.R
import com.wolfiex.chatbox.databinding.FragmentContactosBinding

class ContactosFragment : Fragment() {

    private var _binding: FragmentContactosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val db = Firebase.firestore

    private var listaObtenida = ArrayList<Contactos>()


    fun initRecycler(root: View) {
        val recyclerViewRadios = root.findViewById<RecyclerView>(R.id.listaContactos)
        recyclerViewRadios.layoutManager =
            LinearLayoutManager(activity)
        val adapter = ContactosAdapter(listaObtenida.toList())
        recyclerViewRadios.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    listaObtenida.add(
                        Contactos(document.data["name"].toString(), document.data["estado"].toString())
                    )
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                initRecycler(root)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
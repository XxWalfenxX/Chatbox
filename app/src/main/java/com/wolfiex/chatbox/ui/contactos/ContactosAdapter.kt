package com.wolfiex.chatbox.ui.contactos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wolfiex.chatbox.R

class ContactosAdapter (val contactos: List<Contactos>):RecyclerView.Adapter<ContactosAdapter.ContactosHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactosHolder(layoutInflater.inflate(R.layout.chat_main, parent, false))
    }

    override fun getItemCount(): Int = contactos.size

    override fun onBindViewHolder(holder: ContactosHolder, position: Int) {
        holder.render(contactos[position])
    }

    class ContactosHolder(val view: View):RecyclerView.ViewHolder(view){
        fun render(contactos: Contactos){
            view.findViewById<TextView>(R.id.nomChat).text = contactos.name
            view.findViewById<TextView>(R.id.textoEstadoUsuarioEnChat).text = contactos.estado
            /*
            view.setOnClickListener{
                val intent = Intent(view.context, RadioListener::class.java)
                intent.putExtra("radio_nombre", radio.nombre)
                intent.putExtra("radio_img_url", radio.logoURL)
                intent.putExtra("streamURL", radio.streamURL)
                view.context.startActivity(intent)
            }*/
        }
    }
}
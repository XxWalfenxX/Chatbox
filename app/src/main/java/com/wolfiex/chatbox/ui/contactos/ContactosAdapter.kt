package com.wolfiex.chatbox.ui.contactos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wolfiex.chatbox.R

class ContactosAdapter (val contactos: List<Contactos>):RecyclerView.Adapter<ContactosAdapter.ContactosHolder>(){


    /**
     * Se sobreescribe el método onCreateViewHolder, que se encarga de crear un objeto de la clase ContactosHolder
     * Este objeto es el encargado de almacenar la vista que se mostrará en la lista
     *
     * @param parent
     * @param viewType
     * @return devuelve nuevo objeto ContactosHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosHolder {
        // Se obtiene un objeto LayoutInflater a partir del contexto de la vista padre
        val layoutInflater = LayoutInflater.from(parent.context)
        return ContactosHolder(layoutInflater.inflate(R.layout.chat_main, parent, false))
    }

    /**
     * Método que se encarga de devolver el tamaño de la lista de contactos
     *
     * @return
     */
    override fun getItemCount(): Int = contactos.size

    /**
     * Método que se encarga de enlazar cada elemento de la lista con una vista
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ContactosHolder, position: Int) {
        holder.render(contactos[position])
    }

    /**
     *  Se encarga de almacenar la vista y actualizar su contenido
     *
     * @property view
     */
    class ContactosHolder(val view: View):RecyclerView.ViewHolder(view){
        /**
         * Método que se encarga de actualizar la vista con la información del contacto correspondiente
         *
         * @param contactos
         */
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
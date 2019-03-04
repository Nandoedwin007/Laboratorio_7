package com.example.laboratorio_7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.laboratorio_7.R
import com.example.laboratorio_7.data.Contacto
import kotlinx.android.synthetic.main.contacto_item.view.*

class ContactoAdapter: androidx.recyclerview.widget.ListAdapter<Contacto, ContactoAdapter.ContactoHolder>(DIIF_CALLBACK) {

    companion object {
        private val DIIF_CALLBACK = object : DiffUtil.ItemCallback<Contacto>(){
            override fun areItemsTheSame(oldItem: Contacto, newItem: Contacto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contacto, newItem: Contacto): Boolean {
                return oldItem.nombre == newItem.nombre && oldItem.telefono == newItem.telefono
                        && oldItem.correo == newItem.correo &&oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): ContactoHolder {

        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.contacto_item,parent,false)
        return  ContactoHolder(itemView)

    }

    override fun onBindViewHolder(holder:ContactoHolder,position:Int){
        val currentContacto: Contacto = getItem(position)

        holder.textViewNombre.text = currentContacto.nombre
        holder.textViewTelefono.text = currentContacto.telefono
        holder.textViewPriority.text = currentContacto.priority.toString()
    }

    fun getContactoAt(position: Int):Contacto{
        return getItem(position)
    }

    inner class ContactoHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewNombre:TextView = itemView.text_view_nombre
        var textViewTelefono:TextView = itemView.text_view_telefono
        var textViewPriority:TextView = itemView.text_view_priority
    }

    interface OnItemClickListener {
        fun onItemClick (contacto: Contacto)
    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }
}
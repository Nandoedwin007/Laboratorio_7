package com.example.laboratorio_7.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.laboratorio_7.data.Contacto
import com.example.laboratorio_7.data.ContactoRepository

class ContactoViewModelCreate(application: Application) : AndroidViewModel(application) {
    private var repository:ContactoRepository =
            ContactoRepository(application)

    private var allContactos: LiveData<List<Contacto>> = repository.getAllContactos()

    fun insert(contacto: Contacto){
        repository.insert(contacto)
    }

}
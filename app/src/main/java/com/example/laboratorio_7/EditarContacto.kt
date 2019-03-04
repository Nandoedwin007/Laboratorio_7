package com.example.laboratorio_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laboratorio_7.R

class EditarContacto : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.laboratorio_7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.laboratorio_7.EXTRA_NOMBRE"
        const val EXTRA_TELEFONO = "com.example.laboratorio_7.EXTRA_TELEFONO"
        const val EXTRA_CORREO = "com.example.laboratorio_7.EXTRA_CORREO"
        const val EXTRA_PRIORITY = "com.example.laboratorio_7.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_contacto)
    }
}

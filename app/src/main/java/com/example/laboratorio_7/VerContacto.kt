package com.example.laboratorio_7

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laboratorio_7.data.Contacto
import com.example.laboratorio_7.viewmodels.ContactoViewModelMain
import kotlinx.android.synthetic.main.activity_agregar_contacto.*
import kotlinx.android.synthetic.main.activity_ver_contacto.*

class VerContacto : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.laboratorio_7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.laboratorio_7.EXTRA_NOMBRE"
        const val EXTRA_TELEFONO = "com.example.laboratorio_7.EXTRA_TELEFONO"
        const val EXTRA_CORREO = "com.example.laboratorio_7.EXTRA_CORREO"
        const val EXTRA_PRIORITY = "com.example.laboratorio_7.EXTRA_PRIORITY"

        const val ADD_CONTACTO_REQUEST = 1
        const val EDIT_CONTACTO_REQUEST = 2
        const val VER_CONTACTO_REQUEST = 3
    }

    private lateinit var contactoViewModelMain: ContactoViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_contacto)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)){
            title = "Ver Contacto"
            text_view_nombreV.text = intent.getStringExtra(EXTRA_NOMBRE)
            text_view_telefonoV.text = intent.getStringExtra(EXTRA_TELEFONO)
            text_view_correoV.text = intent.getStringExtra(EXTRA_CORREO)
            text_view_priorityV.text = intent.getIntExtra(EXTRA_PRIORITY,-1).toString()
        } else {
            title = "Ver Contacto"
        }


        //Bton que regresa a la MainActivity
        button_regresar.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                // Handler code here.

                finish()
            }
        })

        //Boton que abre editar contacto
        button_editar.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                var contacto: Contacto = Contacto(
                    intent.getStringExtra(EXTRA_NOMBRE),
                    intent.getStringExtra(EXTRA_TELEFONO),
                    intent.getStringExtra(EXTRA_CORREO),
                    intent.getIntExtra(EXTRA_PRIORITY,-1)
                )

                var intent = Intent(baseContext,AgregarContacto::class.java)
                intent.putExtra(AgregarContacto.EXTRA_ID,contacto.id)
                intent.putExtra(AgregarContacto.EXTRA_NOMBRE,contacto.nombre)
                intent.putExtra(AgregarContacto.EXTRA_TELEFONO,contacto.telefono)
                intent.putExtra(AgregarContacto.EXTRA_CORREO,contacto.correo)
                intent.putExtra(AgregarContacto.EXTRA_PRIORITY,contacto.priority)

                startActivityForResult(intent, MainActivity.EDIT_CONTACTO_REQUEST)

                finish()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MainActivity.ADD_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK){
            val newContacto = Contacto (
                data!!.getStringExtra(AgregarContacto.EXTRA_NOMBRE),
                data.getStringExtra(AgregarContacto.EXTRA_TELEFONO),
                data.getStringExtra(AgregarContacto.EXTRA_CORREO),
                data.getIntExtra(AgregarContacto.EXTRA_PRIORITY, 1)

            )
            contactoViewModelMain.insert(newContacto)

            Toast.makeText(this, "Contacto Creado!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == MainActivity.EDIT_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(VerContacto.EXTRA_ID,-1)

            if (id == -1){
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val verContacto = Contacto (
                data!!.getStringExtra(VerContacto.EXTRA_NOMBRE),
                data.getStringExtra(VerContacto.EXTRA_TELEFONO),
                data.getStringExtra(VerContacto.EXTRA_CORREO),
                data.getIntExtra(VerContacto.EXTRA_PRIORITY,1)
            )

            verContacto.id = data.getIntExtra(VerContacto.EXTRA_ID,-1)
            //contactoViewModelMain.update(verContacto)
        } else {
            //Toast.makeText(this, "Contacto not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.laboratorio_7

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_agregar_contacto.*

//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
class AgregarContacto : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.laboratorio_7.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.laboratorio_7.EXTRA_NOMBRE"
        const val EXTRA_TELEFONO = "com.example.laboratorio_7.EXTRA_TELEFONO"
        const val EXTRA_CORREO = "com.example.laboratorio_7.EXTRA_CORREO"
        const val EXTRA_PRIORITY = "com.example.laboratorio_7.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_contacto)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)){
            title = "Editar Contacto"
            edit_text_nombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
            edit_text_telefono.setText(intent.getStringExtra(EXTRA_TELEFONO))
            edit_text_correo.setText(intent.getStringExtra(EXTRA_CORREO))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        } else {
            title = "Agregar Contacto"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.save_contacto -> {
                saveContacto()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveContacto(){
        if (edit_text_nombre.text.toString().trim().isBlank() || edit_text_telefono.text.toString().trim().isBlank() ||
                edit_text_correo.text.toString().trim().isBlank()){
            Toast.makeText(this, "No se puede agregar contacto", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NOMBRE,edit_text_nombre.text.toString())
            putExtra(EXTRA_TELEFONO,edit_text_telefono.text.toString())
            putExtra(EXTRA_CORREO,edit_text_correo.text.toString())
            putExtra(EXTRA_PRIORITY,number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID,-1) != -1) {
                    putExtra(EXTRA_ID,intent.getIntExtra(EXTRA_ID,-1))
                }
        }

        setResult(Activity.RESULT_OK,data)
        finish()
    }
}

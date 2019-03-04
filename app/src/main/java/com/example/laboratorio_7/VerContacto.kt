package com.example.laboratorio_7

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laboratorio_7.adapters.ContactoAdapter
import com.example.laboratorio_7.data.Contacto
import com.example.laboratorio_7.viewmodels.ContactoViewModelMain
import kotlinx.android.synthetic.main.activity_agregar_contacto.*
import kotlinx.android.synthetic.main.activity_main.*
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

        val MY_PERMISSIONS_REQUEST_CALL_PHONE:Int = 1;

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        var telefonoActual:String = intent.getStringExtra(EXTRA_TELEFONO)

        //Función que dectecta si se precionó el TextView que posee el número de teléfono
        text_view_telefonoV.setOnClickListener{
            val phoneIntent = Intent(Intent.ACTION_CALL)
            phoneIntent.setData(Uri.parse("tel:"+telefonoActual))


            if ((ActivityCompat.checkSelfPermission(this@VerContacto,
                    android.Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED))
            {
                //En caso que no posea el permiso necesario se sale de la función setOnClickListener
                return@setOnClickListener
            }
            //Si posee los permismos necesarios inicia el Intent para realizar llamadas
            startActivity(phoneIntent)

        }

        //Función que detecta si se presionó el TextView del correo electrónico
        //Nuevamente se hace un Intent para iniciar otra actividad pero en este caso también
        //se envía el dato del correo al cual se enviará el mensaje por medio de la función
        //putString()
        text_view_correoV.setOnClickListener{
            val intent2 = Intent(this@VerContacto, EnviarCorreo::class.java)
            val parametro = Bundle()
            parametro.putString("Destinatario",intent.getStringExtra(EXTRA_CORREO))
            intent2.putExtras(parametro)
            startActivity(intent2)

        }

//        recycler_view.layoutManager = LinearLayoutManager(this )
//        recycler_view.setHasFixedSize(true)
//
//        var adapter = ContactoAdapter()
//
//        recycler_view.adapter = adapter
//
//        contactoViewModelMain = ViewModelProviders.of(this).get(ContactoViewModelMain::class.java)
//
//        contactoViewModelMain.getAllContactos().observe(this, Observer<List<Contacto>> {
//            adapter.submitList(it)
//        })

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

                startActivityForResult(intent, VerContacto.EDIT_CONTACTO_REQUEST)

                //finish()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == VerContacto.ADD_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK){
            val newContacto = Contacto (
                data!!.getStringExtra(AgregarContacto.EXTRA_NOMBRE),
                data.getStringExtra(AgregarContacto.EXTRA_TELEFONO),
                data.getStringExtra(AgregarContacto.EXTRA_CORREO),
                data.getIntExtra(AgregarContacto.EXTRA_PRIORITY, 1)

            )
            contactoViewModelMain.insert(newContacto)

            Toast.makeText(this, "Contacto Creado!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == VerContacto.EDIT_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AgregarContacto.EXTRA_ID,-1)

            if (id == -1){
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val editarContacto = Contacto (
                data!!.getStringExtra(AgregarContacto.EXTRA_NOMBRE),
                data.getStringExtra(AgregarContacto.EXTRA_TELEFONO),
                data.getStringExtra(AgregarContacto.EXTRA_CORREO),
                data.getIntExtra(AgregarContacto.EXTRA_PRIORITY,1)
            )

            editarContacto.id = data.getIntExtra(VerContacto.EXTRA_ID,-1)
            //contactoViewModelMain.update(editarContacto)
        } else {
            //Toast.makeText(this, "Contacto not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}

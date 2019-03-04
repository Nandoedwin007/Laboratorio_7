package com.example.laboratorio_7

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laboratorio_7.adapters.ContactoAdapter
import com.example.laboratorio_7.data.Contacto
import com.example.laboratorio_7.viewmodels.ContactoViewModelCreate
import com.example.laboratorio_7.viewmodels.ContactoViewModelEdit
import com.example.laboratorio_7.viewmodels.ContactoViewModelMain
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.InvocationTargetException

//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_CONTACTO_REQUEST = 1
        const val EDIT_CONTACTO_REQUEST = 2
        const val VER_CONTACTO_REQUEST = 3
        lateinit var contactoViewModelMain: ContactoViewModelMain
    }


    private lateinit var contactoViewModelEdit: ContactoViewModelEdit
    private lateinit var contactoViewModelCreate: ContactoViewModelCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddContacto.setOnClickListener {
            startActivityForResult(
                Intent(this, AgregarContacto::class.java),
                ADD_CONTACTO_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this )
        recycler_view.setHasFixedSize(true)

        var adapter = ContactoAdapter()

        recycler_view.adapter = adapter

        contactoViewModelMain = ViewModelProviders.of(this).get(ContactoViewModelMain::class.java)

        contactoViewModelMain.getAllContactos().observe(this,Observer<List<Contacto>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactoViewModelMain.delete(adapter.getContactoAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext,"Contacto Eliminado",Toast.LENGTH_LONG).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object :ContactoAdapter.OnItemClickListener{
            override fun onItemClick(contacto: Contacto) {
                var intent = Intent(baseContext,VerContacto::class.java)
                intent.putExtra(VerContacto.EXTRA_ID,contacto.id)
                intent.putExtra(VerContacto.EXTRA_NOMBRE,contacto.nombre)
                intent.putExtra(VerContacto.EXTRA_TELEFONO,contacto.telefono)
                intent.putExtra(VerContacto.EXTRA_CORREO,contacto.correo)
                intent.putExtra(VerContacto.EXTRA_PRIORITY,contacto.priority)

                startActivityForResult(intent, VER_CONTACTO_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.delete_all_contactos -> {
                contactoViewModelMain.deleteAllContactos()
                Toast.makeText(this, "Todos los contactos eliminados", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK){
            val newContacto = Contacto (
                data!!.getStringExtra(AgregarContacto.EXTRA_NOMBRE),
                data.getStringExtra(AgregarContacto.EXTRA_TELEFONO),
                data.getStringExtra(AgregarContacto.EXTRA_CORREO),
                data.getIntExtra(AgregarContacto.EXTRA_PRIORITY, 1)

            )
            contactoViewModelMain.insert(newContacto)

            Toast.makeText(this, "Contacto Creado!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == VER_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK) {
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
            contactoViewModelMain.update(verContacto)
        }  else if (requestCode == EDIT_CONTACTO_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AgregarContacto.EXTRA_ID,-1)

            if (id == -1){
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateContacto = Contacto (
                data!!.getStringExtra(AgregarContacto.EXTRA_NOMBRE),
                data.getStringExtra(AgregarContacto.EXTRA_TELEFONO),
                data.getStringExtra(AgregarContacto.EXTRA_CORREO),
                data.getIntExtra(AgregarContacto.EXTRA_PRIORITY,1)
            )

            updateContacto.id = data.getIntExtra(VerContacto.EXTRA_ID,-1)
            contactoViewModelMain.update(updateContacto)
        }
        else {
            //Toast.makeText(this, "Contacto not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}

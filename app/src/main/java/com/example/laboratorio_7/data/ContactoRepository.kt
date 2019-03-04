package com.example.laboratorio_7.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ContactoRepository(application: Application) {
    private var contactoDao:ContactoDao
    private var allContactos:LiveData<List<Contacto>>

    init {
        val database:ContactoDatabase = ContactoDatabase.getInstance(
            application.applicationContext)!!
        contactoDao = database.contactoDao()
        allContactos = contactoDao.getAllContactos()
    }

    fun insert(contacto: Contacto){
        val insertContactoAsyncTask = InsertContactoAsyncTask(contactoDao).execute(contacto)
    }

    fun update(contacto: Contacto){
        val updateContactoAsyncTask = UpdateContactoAsyncTask(contactoDao).execute(contacto)
    }

    fun delete(contacto: Contacto){
        val deleteContactoAsyncTask = DeleteContactoAsyncTask(contactoDao).execute(contacto)
    }

    fun deleteAllContactos(){
        val deleteAllContactosAsyncTask = DeleteAllContactosAsyncTask(contactoDao).execute()
    }

    fun getAllContactos():LiveData<List<Contacto>>{
        return  allContactos
    }

    companion object {
        private class InsertContactoAsyncTask(contactoDao: ContactoDao) : AsyncTask<Contacto,Unit,Unit>(){
            val contactoDao = contactoDao
            override fun doInBackground(vararg params: Contacto?) {
                contactoDao.insert(params[0]!!)
            }
        }

        private class UpdateContactoAsyncTask(contactoDao: ContactoDao) : AsyncTask<Contacto,Unit,Unit>(){
            val contactoDao = contactoDao

            override fun doInBackground(vararg params: Contacto?) {
                contactoDao.update(params[0]!!)
            }
        }

        private class DeleteContactoAsyncTask(contactoDao: ContactoDao) : AsyncTask<Contacto,Unit,Unit>(){
            val contactoDao = contactoDao

            override fun doInBackground(vararg params: Contacto?) {
                contactoDao.delete(params[0]!!)
            }
        }

        private class DeleteAllContactosAsyncTask(contactoDao: ContactoDao) : AsyncTask<Unit,Unit,Unit>(){
            val contactoDao = contactoDao

            override fun doInBackground(vararg params: Unit?) {
                contactoDao.deleteAllContactos()
            }
        }
    }
}
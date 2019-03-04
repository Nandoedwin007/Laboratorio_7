package com.example.laboratorio_7.data

import android.content.Context
import android.os.AsyncTask
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

//Basado en el código del repositorio https://github.com/berkeatac/Notes-App
@Database(entities = [Contacto::class], version = 1)
abstract class ContactoDatabase:RoomDatabase() {
    abstract fun contactoDao():ContactoDao

    companion object {
        private var instance:ContactoDatabase? = null

        fun getInstance(context: Context):ContactoDatabase? {
            if (instance == null){
                synchronized(ContactoDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactoDatabase::class.java, "contacto_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }
        fun destroyInstance(){
            instance = null
        }
        private val roomCallback = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db:ContactoDatabase?) : AsyncTask<Unit,Unit,Unit>(){
        private val contactoDao = db?.contactoDao()
        override fun doInBackground(vararg params: Unit?) {
            contactoDao?.insert(Contacto("nombre 1","telefono 1","correo 1",1))
            contactoDao?.insert(Contacto("nombre 2","telefono 2","correo 2",2))
            contactoDao?.insert(Contacto("nombre 3","telefono 3","correo 3",3))
        }
    }

}
package com.example.laboratorio_7.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactoDao {

    @Insert
    fun insert(contacto: Contacto)

    @Update
    fun update(contacto: Contacto)

    @Delete
    fun delete(contacto: Contacto)

    @Query("DELETE FROM contacto_table")
    fun deleteAllContactos()

    @Query("SELECT * FROM contacto_table ORDER BY priority DESC")
    fun getAllContactos():LiveData<List<Contacto>>
}
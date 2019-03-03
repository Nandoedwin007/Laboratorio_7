package com.example.laboratorio_7.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contacto_table")
data class Contacto(var nombre:String, var telefono:String, var correo:String) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}
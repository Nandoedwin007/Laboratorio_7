package com.example.laboratorio_7.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Clase de datos de contacto, es la base para los objetos de tipo Contacto
@Entity(tableName = "contacto_table")
data class Contacto(
    var nombre:String,
    var telefono:String,
    var correo:String,
    var priority:Int) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}
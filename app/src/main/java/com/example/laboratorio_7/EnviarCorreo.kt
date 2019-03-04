package com.example.laboratorio_7

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_enviar_correo.*

class EnviarCorreo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_correo)

        //Esta variable contiene los extras del bundle, esto se utiliza para recibir el correo electrónico de destinatario
        val b:Bundle = intent.extras
        val destinatario = b.getString("Destinatario")

        //Al definir este context podemos llamar a los elementos de la clase MyApplication de una forma más sencilla
        //val context:MyApplication = applicationContext as MyApplication

        //Datos estáticos que se utilizan para enviar el correo
        val MiNombre:String = "Edwin Coronado"
        val MiNumero:String = "11223344"

        val tvNombreDe = findViewById<TextView>(R.id.tvDe)
        val tvNombrePara = findViewById<TextView>(R.id.tvPara)
        val tvMensaje = findViewById<TextView>(R.id.tvMensaje)

        //String utilizada para enviar el correo
        val mensaje:String = "Mi nombre es "+MiNombre+", y mi telefóno es "+MiNumero

        //Aquí llenamos los TextView con los datos del mensaje que se está componiendo
        tvNombreDe.text = MiNombre
        tvNombrePara.text = destinatario
        tvMensaje.text = mensaje


        //Definición del botón Enviar
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        //Funcion que abre la activity de MostrarMenu
        btnEnviar.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View): Unit {
                // Creamos el Intent para poder enviar el correo y configuramos los parámetros necesarios
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.setData(Uri.parse("mailto:"))
                emailIntent.setType("text/plain")
                emailIntent.putExtra(Intent.EXTRA_EMAIL,destinatario)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Saludos desde mi Applicación")
                emailIntent.putExtra(Intent.EXTRA_TEXT,"Mi nombre es "+MiNombre+", y mi telefóno es "+MiNumero)

                startActivity(Intent.createChooser(emailIntent, "Send email..."))
                //Terminamos la activity de enviar correo y regresamos a la de ver contacto
                finish()
                //Se guarda en la bitácora que se ha envíado el correo, pero no es necesario
                Log.i("Accion","Se ha enviado el correo")
                //Una vez envíado  el correo se notifica con un SnackBar
                //Snackbar.make(this,"Enviado desde " +MiNombre+" hasta "+destinatario,Snackbar.LENGTH_SHORT).show()
            }
        })


    }
}

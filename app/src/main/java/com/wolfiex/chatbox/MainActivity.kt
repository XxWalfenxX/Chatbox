package com.wolfiex.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Iniciar Sesión
        val btnLogin = findViewById<Button>(R.id.btn_login_google)
        btnLogin.setOnClickListener {
            val intento1 = Intent(this, MainActivity2::class.java)
           startActivity(intento1)
        }
    }
}
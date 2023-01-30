package com.wolfiex.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class RegisterUser : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        auth = Firebase.auth

        val db = Firebase.firestore
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = settings
        // [END set_firestore_settings]

        val btnRegister: Button = findViewById(R.id.btn_register_email)
        val user: EditText = findViewById(R.id.texoNombreRegister)
        val email: EditText = findViewById(R.id.textEmailRegister)
        val passwd: EditText = findViewById(R.id.textRegisterEmailPasswd)
        val passwd2: EditText = findViewById(R.id.textRegisterEmailPasswd2)

        btnRegister.setOnClickListener {
            if (!email.text.toString().equals("") && !passwd.text.toString().equals("") && !passwd2.text.toString().equals("")) {
                if (passwd.text.toString().equals(passwd2.text.toString())){
                    val datosUser = hashMapOf(
                        "name" to user.text.toString(),
                        "estado" to "Hola, estoy usando Chatbox"
                    )
                    db.collection("users").document(email.text.toString())
                        .set(datosUser)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                    createAccount(email.text.toString(), passwd.text.toString())
                }
            }
        }

    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intento1 = Intent(this, MainActivity2::class.java)
            intento1.putExtra("userFirebase", user)
            startActivity(intento1)
        }
    }
    companion object {
        const val TAG = "EmailPassword"
    }
}
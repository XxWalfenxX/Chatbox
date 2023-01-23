package com.wolfiex.chatbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        val btnLogin: Button = findViewById(R.id.btn_login_email)
        val email: EditText = findViewById(R.id.textEmailRegister)
        val passwd: EditText = findViewById(R.id.textRegisterEmailPasswd)
        val gotoRegister: Button = findViewById(R.id.btn_goto_register)

        btnLogin.setOnClickListener {
            if (!email.text.toString().equals("") && !passwd.text.toString().equals("")) {
                signIn(email.text.toString(), passwd.text.toString())
            }
        }

        gotoRegister.setOnClickListener {
            val intento1 = Intent(this, RegisterUser::class.java)
            startActivity(intento1)
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intento1 = Intent(this, MainActivity2::class.java)
            intento1.putExtra("userFirebase", user)
            startActivity(intento1)
        }
    }

    private fun reload() {
        val intento1 = Intent(this, MainActivity2::class.java)
        startActivity(intento1)
    }

    companion object {
        const val TAG = "EmailPassword"
    }
}
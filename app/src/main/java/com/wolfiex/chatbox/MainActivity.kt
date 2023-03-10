package com.wolfiex.chatbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    /**
     * Inicialización de la activity con el layout y las referencias necesarias
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        val btnLogin: Button = findViewById(R.id.btn_login_email)
        val email: EditText = findViewById(R.id.textEmailRegister)
        val passwd: EditText = findViewById(R.id.textRegisterEmailPasswd)
        val gotoRegister: Button = findViewById(R.id.btn_goto_register)
        val google : Button =  findViewById(R.id.btn_login_google)


        btnLogin.setOnClickListener {
            if (!email.text.toString().equals("") && !passwd.text.toString().equals("")) {
                signIn(email.text.toString(), passwd.text.toString())
            }
        }

        gotoRegister.setOnClickListener {
            val intento1 = Intent(this, RegisterUser::class.java)
            startActivity(intento1)
        }

        google.setOnClickListener {
            signIn()
        }

    }

    /**
     * Método que se ejecuta al iniciar la actividad
     *
     */
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    /**
     * Recibe el estado del Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    /**
     * Autentificación con Google
     *
     * @param idToken
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG2, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG2, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    /**
     * Inicio de sesión con email
     *
     * @param email correo
     * @param password contraseña
     */
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

    /**
     * Inicio de sesión con Google
     *
     */
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * Actualizar la interfaz de usuario
     *
     * @param user usuario de Firebase
     */
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intento1 = Intent(this, MainActivity2::class.java)
            intento1.putExtra("userFirebase", user)
            startActivity(intento1)
        }
    }

    /**
     *  Cambia la actividad si hay un usuario con sesión activa
     *
     */
    private fun reload() {
        val intento1 = Intent(this, MainActivity2::class.java)
        startActivity(intento1)
    }

    companion object {
        const val TAG = "EmailPassword"
        private const val TAG2 = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}
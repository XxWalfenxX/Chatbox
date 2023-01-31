package com.wolfiex.chatbox

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.wolfiex.chatbox.databinding.ActivityMain2Binding
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Firebase.firestore
        storage = Firebase.storage


        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        setSupportActionBar(binding.appBarMain.toolbarBarraNavegacion)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // Cargar items barra lateral
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_chat, R.id.nav_contacts, R.id.nav_ajustes, R.id.nav_logoff
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if (user != null) {
            val db = Firebase.firestore
            val nav_view: NavigationView = findViewById(R.id.nav_view)
            val userText: TextView = nav_view.getHeaderView(0).findViewById(R.id.textoNombreUsuario)
            val emailText: TextView = nav_view.getHeaderView(0).findViewById(R.id.textoMailUser)
            val estatText: TextView =
                nav_view.getHeaderView(0).findViewById(R.id.textoEstadoUsuario)
            var hastest: Map<String, Any>

            val docRef = db.collection("users").document(user.email.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document.data != null) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        hastest = document.data as Map<String, Any>
                        estatText.setText(hastest.get("estado").toString())
                        userText.setText(hastest.get("name").toString())
                    } else {
                        val userData = hashMapOf(
                            "estado" to "Hola, estoy usando chatbox",
                            "name" to user.displayName
                        )
                        db.collection("users").document(user.email.toString())
                            .set(userData)
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                        estatText.text = userData["estado"].toString()
                        userText.text = userData["name"].toString()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
            
            emailText.setText(user.email.toString())

        }
    }

    private fun getCurrentUser() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            //val TextoUsuario: TextView = findViewById(R.id.textoNombreUsuario)


        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {

        private val TAG = "DocSnippets"

        private val EXECUTOR = ThreadPoolExecutor(
            2, 4,
            60, TimeUnit.SECONDS, LinkedBlockingQueue()
        )
    }
}
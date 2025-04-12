package com.app.pokeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.app.pokeapp.ui.login.LoginActivity
import com.app.pokeapp.util.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * Clase que lanza un splash con la imagen de una pokebola
 * Controla el acceso directo al MainActivity o al login
 */
class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nextActivity = if (SessionManager.isLoggedIn(this)) {
            MainActivity::class.java // si tiene un login activo, pasa al MainActivity
        } else {
            LoginActivity::class.java // pasa acá si no tiene una sesión activa
        }

        startActivity(Intent(this, nextActivity))
        finish() // Cerramos esta actividad para que no se quede en el back stack
    }
}

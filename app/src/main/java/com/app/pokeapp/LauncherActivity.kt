package com.app.pokeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.app.pokeapp.ui.login.LoginActivity
import com.app.pokeapp.util.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nextActivity = if (SessionManager.isLoggedIn(this)) {
            MainActivity::class.java
        } else {
            LoginActivity::class.java
        }

        startActivity(Intent(this, nextActivity))
        finish() // Cerramos esta actividad para que no se quede en el back stack
    }
}

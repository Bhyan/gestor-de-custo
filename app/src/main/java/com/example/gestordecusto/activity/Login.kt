package com.example.gestordecusto.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordecusto.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<View>(R.id.loginBtn) as Button
        val cadastro = findViewById<View>(R.id.cadastrarBtn) as Button

        login.setOnClickListener {
            login()
        }
        cadastro.setOnClickListener {
            cadastro()
        }
    }

    private fun login() {
        val email = (findViewById<View>(R.id.emailInput) as EditText).text.toString()
        val senha = (findViewById<View>(R.id.senhaInput) as EditText).text.toString()
        val mAuto = FirebaseAuth.getInstance()

        if (email.isEmpty()) {
            Toast.makeText(this@Login, "Informe o e-mail", Toast.LENGTH_LONG).show()
        }
        if (senha.isEmpty()) {
            Toast.makeText(this@Login, "Informe a senha", Toast.LENGTH_LONG).show()
        }
        if (!email.isEmpty() && !senha.isEmpty()) {
            mAuto.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, CadastroPerfil::class.java))
                    } else {
                        Toast.makeText(this, "E-mail ou senha incorreto.", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }

    private fun cadastro() {
        startActivity(Intent(this, Cadastro::class.java))
    }
}
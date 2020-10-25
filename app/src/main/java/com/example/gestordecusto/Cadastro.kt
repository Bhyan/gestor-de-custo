package com.example.gestordecusto

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Cadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val cadastro = findViewById<View>(R.id.cadastrarBtn) as Button
        val cancelar = findViewById<View>(R.id.cancelarBtn) as Button

        cadastro.setOnClickListener {
            cadastrar()
        }

        cancelar.setOnClickListener {
            finish()
        }
    }

    private fun cadastrar() {
        val nome = (findViewById<View>(R.id.nomeInput) as EditText).text.toString()
        val email = (findViewById<View>(R.id.emailInput) as EditText).text.toString()
        val senha = (findViewById<View>(R.id.senhaInput) as EditText).text.toString()

        if (nome.isEmpty()) {
            Toast.makeText(this@Cadastro, "Informe o nome", Toast.LENGTH_LONG).show()
        }
        if (email.isEmpty()) {
            Toast.makeText(this@Cadastro, "Informe o e-mail", Toast.LENGTH_LONG).show()
        }
        if (senha.isEmpty()) {
            Toast.makeText(this@Cadastro, "Informe a senha", Toast.LENGTH_LONG).show()
        }

        if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@Cadastro, "Cadastrado com sucesso.", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@Cadastro, "Erro ao cadastrar.", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
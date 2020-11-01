package com.example.gestordecusto.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordecusto.R
import com.example.gestordecusto.helper.UsuarioDAO
import com.example.gestordecusto.model.Usuario
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
        val confirmarSenha =
            (findViewById<View>(R.id.confirmarSenhaInput) as EditText).text.toString()
        val cpf = (findViewById<View>(R.id.cpfInput) as EditText).text.toString()

        when {
            nome.isEmpty() -> {
                Toast.makeText(this@Cadastro, "Informe o nome", Toast.LENGTH_LONG).show()
            }
            email.isEmpty() -> {
                Toast.makeText(this@Cadastro, "Informe o e-mail", Toast.LENGTH_LONG).show()
            }
            senha.isEmpty() -> {
                Toast.makeText(this@Cadastro, "Informe a senha", Toast.LENGTH_LONG).show()
            }
            senha != confirmarSenha -> {
                Toast.makeText(
                    this@Cadastro,
                    "A confirmação de senha deve ser igual",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@Cadastro,
                                "Cadastrado com sucesso.",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            val db = UsuarioDAO(applicationContext)
                            val usuario =
                                Usuario(null, nome, cpf, email, senha, null, null, null, null)

                            db.salvar(usuario)

                            finish()
                        } else {
                            Toast.makeText(this@Cadastro, "Erro ao cadastrar.", Toast.LENGTH_LONG)
                                .show()

                        }
                    }
            }
        }
    }
}
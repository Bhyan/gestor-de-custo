package com.example.gestordecusto.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gestordecusto.R
import com.example.gestordecusto.helper.UsuarioDAO
import com.example.gestordecusto.model.Usuario

class CadastroPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_perfil)

        val inserir = findViewById<View>(R.id.inserirBtn) as Button
        val pular = findViewById<View>(R.id.pularBtn) as Button

        inserir.setOnClickListener {
            finalizarCadastro()
        }

        pular.setOnClickListener {
            finish()
        }
    }

    fun finalizarCadastro() {
        val dataNascimento = (findViewById<View>(R.id.dataInput) as EditText).text.toString()
        val profissao = (findViewById<View>(R.id.profissaoInput) as EditText).text.toString()
        val telefone = (findViewById<View>(R.id.telefoneInput) as EditText).text.toString()
        val endereco = (findViewById<View>(R.id.enderecoInput) as EditText).text.toString()
        val cep = (findViewById<View>(R.id.cepInput) as EditText).text.toString()

        when {
            dataNascimento.isEmpty() -> {
                Toast.makeText(
                    this@CadastroPerfil,
                    "Informe a data de nascimento",
                    Toast.LENGTH_LONG
                ).show()
            }
            profissao.isEmpty() -> {
                Toast.makeText(this@CadastroPerfil, "Informe a profissão", Toast.LENGTH_LONG).show()
            }
            telefone.isEmpty() -> {
                Toast.makeText(this@CadastroPerfil, "Informe o telefone", Toast.LENGTH_LONG).show()
            }
            endereco.isEmpty() -> {
                Toast.makeText(this@CadastroPerfil, "Informe o endereço", Toast.LENGTH_LONG).show()
            }
            cep.isEmpty() -> {
                Toast.makeText(this@CadastroPerfil, "Informe o cep", Toast.LENGTH_LONG).show()
            }
        }

        val pref = getSharedPreferences("informacoes", 0)
        val db = UsuarioDAO(applicationContext)
        val usuario = db.findByEmail(pref.getString("email", null).toString())
        val usuarioAtt =
            Usuario(
                usuario.id,
                usuario.nome,
                usuario.cpf,
                usuario.email,
                usuario.senha,
                profissao,
                telefone,
                endereco,
                cep
            )

        if (db.atualizar(usuarioAtt)) {
            startActivity(Intent(this, Main::class.java))
        } else {
            Toast.makeText(this, "Erro ao adicionar os dados.", Toast.LENGTH_LONG)
                .show()
        }
    }
}
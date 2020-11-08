package com.example.gestordecusto.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gestordecusto.R
import com.example.gestordecusto.helper.UsuarioDAO
import com.example.gestordecusto.model.UsuarioModel
import com.google.android.material.navigation.NavigationView

class CadastroPerfilActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_perfil)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        val inserir = findViewById<View>(R.id.inserirBtn) as Button
        val pular = findViewById<View>(R.id.pularBtn) as Button

        inserir.setOnClickListener {
            finalizarCadastro()
        }

        pular.setOnClickListener {
            finish()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_sair -> {
                Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_perfil -> {
                startActivity(Intent(this, CadastroPerfilActivity::class.java))
            }
            R.id.nav_empresas -> {
                startActivity(Intent(this, SimulacaoActivity::class.java))
            }
            R.id.nav_informacao -> {
                startActivity(Intent(this, InformacaoActivity::class.java))
            }
            R.id.nav_atividades -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    fun finalizarCadastro() {
        val dataNascimento = (findViewById<View>(R.id.dataInput) as EditText).text.toString()
        val profissao = (findViewById<View>(R.id.profissaoInput) as EditText).text.toString()
        val telefone = (findViewById<View>(R.id.telefoneInput) as EditText).text.toString()
        val endereco = (findViewById<View>(R.id.enderecoInput) as EditText).text.toString()
        val cep = (findViewById<View>(R.id.cepInput) as EditText).text.toString()
        val data = (findViewById<View>(R.id.dataInput) as EditText).text.toString()

        when {
            dataNascimento.isEmpty() -> {
                Toast.makeText(
                    this@CadastroPerfilActivity,
                    "Informe a data de nascimento",
                    Toast.LENGTH_LONG
                ).show()
            }
            profissao.isEmpty() -> {
                Toast.makeText(this@CadastroPerfilActivity, "Informe a profissão", Toast.LENGTH_LONG).show()
            }
            telefone.isEmpty() -> {
                Toast.makeText(this@CadastroPerfilActivity, "Informe o telefone", Toast.LENGTH_LONG).show()
            }
            endereco.isEmpty() -> {
                Toast.makeText(this@CadastroPerfilActivity, "Informe o endereço", Toast.LENGTH_LONG).show()
            }
            cep.isEmpty() -> {
                Toast.makeText(this@CadastroPerfilActivity, "Informe o cep", Toast.LENGTH_LONG).show()
            }
            else -> {
                val pref = getSharedPreferences("informacoes", 0)
                val db = UsuarioDAO(applicationContext)
                val usuario = db.findByEmail(pref.getString("email", null).toString())
                val usuarioAtt =
                    UsuarioModel(
                        usuario.id,
                        usuario.nome,
                        usuario.cpf,
                        usuario.email,
                        usuario.senha,
                        profissao,
                        limparString(telefone),
                        endereco,
                        limparString(cep),
                        limparString(data)
                    )

                if (db.atualizar(usuarioAtt)) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Erro ao adicionar os dados.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun limparString(string: String): String {
        return string.replace(" ", "")
            .replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace("/", "")
            .replace(".", "")
            .replace("+55", "")
    }
}
package com.example.gestordecusto.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gestordecusto.R
import com.example.gestordecusto.model.TipoAtividade
import com.example.gestordecusto.model.TipoSimulacao
import com.google.android.material.navigation.NavigationView

class SimulacaoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulacao)

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

        val salvar = findViewById<View>(R.id.salvarBtn) as Button
        val cancelar = findViewById<View>(R.id.cancelarBtn) as Button

        val edicao = preencherValores()

        salvar.setOnClickListener {
            if (edicao) {
                salvarSimulacao()
            } else {
                novaSimulacao()
            }
        }
        cancelar.setOnClickListener {
            finish()
        }

        val valorSujerido = (findViewById<View>(R.id.valorSujeridoInput) as EditText)
        val lucro = (findViewById<View>(R.id.lucroInput) as EditText)
        valorSujerido.setKeyListener(null)

        lucro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (validarCampos()) {
                    val custoMateria =
                        (findViewById<View>(R.id.custoMateriaInput) as EditText).text.toString()
                    val custoMaoObra =
                        (findViewById<View>(R.id.custoMaoObraInput) as EditText).text.toString()
                    val custoDiverso =
                        (findViewById<View>(R.id.custoDiversoInput) as EditText).text.toString()
                    val lucroValor = lucro.text.toString().replace("%", "").replace("_", "")
                    val semImposto =
                        (findViewById<View>(R.id.tipoSimulacaoSemImposto) as RadioButton).isChecked
                    val produto =
                        (findViewById<View>(R.id.tipoAtividadeProduto) as RadioButton).isChecked
                    var imposto = 1.0

                    if (!semImposto) {
                        if (produto) {
                            imposto += 0.18
                        } else {
                            imposto += 0.05
                        }
                    }
                    
                    if (lucroValor == ""){
                        lucroValor = "0"
                    }

                    val sujerido =
                        (custoDiverso.toDouble() + custoMateria.toDouble() + custoMaoObra.toDouble()) * (100 + lucroValor.toDouble() / 100) * imposto

                    valorSujerido.setText(sujerido.toString())
                }
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_perfil -> {
                startActivity(Intent(this, CadastroPerfilActivity::class.java))
            }
            R.id.nav_empresas -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.nav_informacao -> {
                startActivity(Intent(this, InformacaoActivity::class.java))
            }
            R.id.nav_simulacao -> {
                startActivity(Intent(this, SimulacaoActivity::class.java))
            }
            R.id.nav_sair -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun salvarSimulacao() {
        if (validarCampos()) {
            intent.putExtra("id", intent.getStringExtra("id"))
            intent.putExtra("idLista", intent.getStringExtra("idLista"))
            intent.putExtra(
                "nomeProduto",
                (findViewById<View>(R.id.nomeProdutoInput) as EditText).text.toString()
            )
            intent.putExtra(
                "custoMateria",
                (findViewById<View>(R.id.custoMateriaInput) as EditText).text.toString()
            )
            intent.putExtra(
                "custoMaoObra",
                (findViewById<View>(R.id.custoMaoObraInput) as EditText).text.toString()
            )
            intent.putExtra(
                "custoDiverso",
                (findViewById<View>(R.id.custoDiversoInput) as EditText).text.toString()
            )
            intent.putExtra(
                "lucro", (findViewById<View>(R.id.lucroInput) as EditText).text.toString()
                    .replace("%", "")
                    .replace("_", "")
            )
            intent.putExtra(
                "valorSujerido",
                (findViewById<View>(R.id.valorSujeridoInput) as EditText).text.toString()
            )

            val tipoAtividadeProduto =
                (findViewById<View>(R.id.tipoAtividadeProduto) as RadioButton).isChecked
            val tipoSimulacaoSemImposto =
                (findViewById<View>(R.id.tipoSimulacaoSemImposto) as RadioButton).isChecked
            var tipoAtividade: TipoAtividade? = null
            var tipoSimulacao: TipoSimulacao? = null

            tipoAtividade =
                if (tipoAtividadeProduto) TipoAtividade.PRODUTO else TipoAtividade.SERVICO
            tipoSimulacao =
                if (tipoSimulacaoSemImposto) TipoSimulacao.SEM_IMPOSTO else TipoSimulacao.COM_IMPOSTO

            intent.putExtra("tipoAtividade", tipoAtividade.toString())
            intent.putExtra("tipoSimulacao", tipoSimulacao.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "Informe os campos.", Toast.LENGTH_LONG).show()
        }
    }

    private fun novaSimulacao() {
        if (validarCampos()) {
            intent.putExtra(
                "nomeProduto",
                (findViewById<View>(R.id.nomeProdutoInput) as EditText).text.toString()
            )
            intent.putExtra(
                "custoMateria",
                (findViewById<View>(R.id.custoMateriaInput) as EditText).text.toString()
            )
            intent.putExtra(
                "custoMaoObra",
                (findViewById<View>(R.id.custoMaoObraInput) as EditText).text.toString()
            )
            intent.putExtra(
                "custoDiverso",
                (findViewById<View>(R.id.custoDiversoInput) as EditText).text.toString()
            )
            intent.putExtra(
                "lucro", (findViewById<View>(R.id.lucroInput) as EditText).text.toString()
                    .replace("%", "")
                    .replace("_", "")
            )
            intent.putExtra(
                "valorSujerido",
                (findViewById<View>(R.id.valorSujeridoInput) as EditText).text.toString()
            )

            val tipoAtividadeProduto =
                (findViewById<View>(R.id.tipoAtividadeProduto) as RadioButton).isChecked
            val tipoSimulacaoSemImposto =
                (findViewById<View>(R.id.tipoSimulacaoSemImposto) as RadioButton).isChecked
            var tipoAtividade: String? = null
            var tipoSimulacao: String? = null

            tipoAtividade =
                if (tipoAtividadeProduto) TipoAtividade.PRODUTO.getDescricao() else TipoAtividade.SERVICO.getDescricao()
            tipoSimulacao =
                if (tipoSimulacaoSemImposto) TipoSimulacao.SEM_IMPOSTO.getDescricao() else TipoSimulacao.COM_IMPOSTO.getDescricao()

            intent.putExtra("tipoAtividade", tipoAtividade)
            intent.putExtra("tipoSimulacao", tipoSimulacao)

            setResult(5, intent)
            finish()
        } else {
            Toast.makeText(this, "Informe os campos.", Toast.LENGTH_LONG).show()
        }
    }

    private fun preencherValores(): Boolean {
        (findViewById<View>(R.id.nomeProdutoInput) as EditText).setText(intent.getStringExtra("nomeProduto"))
        (findViewById<View>(R.id.custoMateriaInput) as EditText).setText(intent.getStringExtra("custoMateria"))
        (findViewById<View>(R.id.custoMaoObraInput) as EditText).setText(intent.getStringExtra("custoMaoObra"))
        (findViewById<View>(R.id.custoDiversoInput) as EditText).setText(intent.getStringExtra("custoDiversos"))
        (findViewById<View>(R.id.lucroInput) as EditText).setText(intent.getStringExtra("lucro"))
        (findViewById<View>(R.id.valorSujeridoInput) as EditText).setText(intent.getStringExtra("valorSujerido"))

        val tipoAtividade = intent.getStringExtra("tipoAtividade")
        if (tipoAtividade == TipoAtividade.SERVICO.toString()) {
            (findViewById<View>(R.id.tipoAtividadeServico) as RadioButton).isChecked = true
        } else {
            (findViewById<View>(R.id.tipoAtividadeProduto) as RadioButton).isChecked = true
        }

        val tipoSimulacao = intent.getStringExtra("tipoSimulacao")
        if (tipoSimulacao == TipoSimulacao.COM_IMPOSTO.toString()) {
            (findViewById<View>(R.id.tipoSimulacaoComImposto) as RadioButton).isChecked = true
        } else {
            (findViewById<View>(R.id.tipoSimulacaoSemImposto) as RadioButton).isChecked = true
        }

        return intent.getStringExtra("lucro") != null
    }

    private fun validarCampos(): Boolean {
        val nomeProdutoInput =
            (findViewById<View>(R.id.nomeProdutoInput) as EditText).text.toString()
        val custoMateriaInput =
            (findViewById<View>(R.id.custoMateriaInput) as EditText).text.toString()
        val custoMaoObraInput =
            (findViewById<View>(R.id.custoMaoObraInput) as EditText).text.toString()
        val custoDiversoInput =
            (findViewById<View>(R.id.custoDiversoInput) as EditText).text.toString()
        val lucroInput = (findViewById<View>(R.id.lucroInput) as EditText).text.toString()

        return nomeProdutoInput != "" && custoMateriaInput != "" && custoMaoObraInput != "" && custoDiversoInput != "" && lucroInput != ""
    }
}

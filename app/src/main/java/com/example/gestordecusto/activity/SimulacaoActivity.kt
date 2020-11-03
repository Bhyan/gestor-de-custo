package com.example.gestordecusto.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.gestordecusto.R
import com.example.gestordecusto.model.TipoAtividade
import com.example.gestordecusto.model.TipoSimulacao

class SimulacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulacao)

        val salvar = findViewById<View>(R.id.salvarBtn) as Button
        val cancelar = findViewById<View>(R.id.cancelarBtn) as Button

        preencherValores()

        salvar.setOnClickListener {
            if (salvarSimulação()) {
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
                val custoMateria =
                    (findViewById<View>(R.id.custoMateriaInput) as EditText).text.toString()
                val custoMaoObra =
                    (findViewById<View>(R.id.custoMaoObraInput) as EditText).text.toString()
                val custoDiverso =
                    (findViewById<View>(R.id.custoDiversoInput) as EditText).text.toString()
                val lucroValor = lucro.text.toString().replace("%", "").replace("_", "")

                val sujerido =
                    (custoDiverso.toDouble() + custoMateria.toDouble() + custoMaoObra.toDouble()) * (1 + lucroValor.toInt() / 100)

                valorSujerido.setText(sujerido.toString())
            }
        })
    }

    private fun salvarSimulação(): Boolean {
        val nomeProduto = findViewById<View>(R.id.nomeProdutoInput)
        val custoMateria = findViewById<View>(R.id.custoMateriaInput)
        val custoMaoObra = findViewById<View>(R.id.custoMaoObraInput)
        val custoDiverso = findViewById<View>(R.id.custoDiversoInput)
        val lucro = findViewById<View>(R.id.lucroInput)

        return (nomeProduto != null || custoMateria != null || custoMaoObra != null ||
                custoDiverso != null || lucro != null)
    }

    private fun salvarSimulacao() {
        intent.putExtra("id", intent.getStringExtra("id"))
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
        val revenda = (findViewById<View>(R.id.revenda) as CheckBox).isChecked
        var tipoAtividade: TipoAtividade? = null
        var tipoSimulacao: TipoSimulacao? = null

        tipoAtividade =
            if (tipoAtividadeProduto) TipoAtividade.PRODUTO else TipoAtividade.SERVICO
        tipoSimulacao =
            if (tipoSimulacaoSemImposto) TipoSimulacao.SEM_IMPOSTO else TipoSimulacao.COM_IMPOSTO

        intent.putExtra("tipoAtividade", tipoAtividade.toString())
        intent.putExtra("tipoSimulacao", tipoSimulacao.toString())

        var revendaValor = if (revenda) 1 else 0

        intent.putExtra("revenda", revendaValor)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun novaSimulacao() {
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
        val revenda = (findViewById<View>(R.id.revenda) as CheckBox).isChecked
        var tipoAtividade: TipoAtividade? = null
        var tipoSimulacao: TipoSimulacao? = null

        tipoAtividade =
            if (tipoAtividadeProduto) TipoAtividade.PRODUTO else TipoAtividade.SERVICO
        tipoSimulacao =
            if (tipoSimulacaoSemImposto) TipoSimulacao.SEM_IMPOSTO else TipoSimulacao.COM_IMPOSTO

        intent.putExtra("tipoAtividade", tipoAtividade)
        intent.putExtra("tipoSimulacao", tipoSimulacao)

        intent.putExtra("revenda", revenda)

        setResult(5, intent)
        finish()
    }

    private fun preencherValores() {
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

        (findViewById<View>(R.id.revenda) as CheckBox).isChecked = intent.getBooleanExtra("revenda", false)
    }
}
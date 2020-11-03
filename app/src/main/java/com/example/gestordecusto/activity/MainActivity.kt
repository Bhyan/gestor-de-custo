package com.example.gestordecusto.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordecusto.R
import com.example.gestordecusto.adapter.AdapterSimulacao
import com.example.gestordecusto.helper.SimulacaoDAO
import com.example.gestordecusto.model.SimulacaoModel
import com.example.gestordecusto.model.TipoAtividade
import com.example.gestordecusto.model.TipoSimulacao

class MainActivity : AppCompatActivity() {

    var listaSimulacao: ArrayList<SimulacaoModel> = ArrayList()
    lateinit var adapterSimulacao: AdapterSimulacao
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val db = SimulacaoDAO(applicationContext)
        listaSimulacao = db.listar()
        adapterSimulacao = AdapterSimulacao(listaSimulacao, this::onSimulacaoClick, this::onLongClick)

        val recycleView: RecyclerView = findViewById(R.id.recyclerView)

        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycleView.layoutManager = layoutManager
        recycleView.setHasFixedSize(true)
        recycleView.adapter = adapterSimulacao
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onSimulacaoClick(simulacao: SimulacaoModel, id: Int){
        val intent = Intent(this, SimulacaoActivity::class.java)

        intent.putExtra("id", id.toString())
        intent.putExtra("nomeProduto", simulacao.nomeProduto)
        intent.putExtra("custoMateria", simulacao.custoMateria.toString())
        intent.putExtra("custoMaoObra", simulacao.custoMaoObra.toString())
        intent.putExtra("custoDiversos", simulacao.custoDiversos.toString())
        intent.putExtra("lucro", simulacao.lucro.toString())
        intent.putExtra("tipoAtividade", simulacao.tipoAtividade.toString())
        intent.putExtra("tipoSimulacao", simulacao.tipoSimulacao.toString())
        intent.putExtra("valorSujerido", simulacao.valorSujerido.toString())
        intent.putExtra("revenda", simulacao.revenda)

        startActivityForResult(intent, 1)
    }

    fun onLongClick(id: Int){
        listaSimulacao.removeAt(id)
        adapterSimulacao.notifyItemRemoved(id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        val db = SimulacaoDAO(applicationContext)

        if (requestCode == 10 && resultCode == 5) {
            val nomeProduto = data?.getStringExtra("nomeProduto").toString()
            val custoMateria = data?.getStringExtra("custoMateria").toString()
            val custoMaoObra = data?.getStringExtra("custoMaoObra").toString()
            val custoDiverso = data?.getStringExtra("custoDiverso").toString()
            val lucro = data?.getStringExtra("lucro").toString()
            val valorSujerido = data?.getStringExtra("valorSujerido").toString()
            val tipoAtividade = data?.getStringExtra("tipoAtividade").toString()
            val tipoSimulacao = data?.getStringExtra("tipoSimulacao").toString()
            val revenda = data?.getBooleanExtra("revenda", false)
            val simulacao = SimulacaoModel(
                null,
                nomeProduto,
                custoMateria.toDouble(),
                custoMaoObra.toDouble(),
                custoDiverso.toDouble(),
                lucro.toDouble(),
                TipoAtividade.valueOf(tipoAtividade),
                TipoSimulacao.valueOf(tipoSimulacao),
                valorSujerido.toDouble(),
                revenda
            )

            db.salvar(simulacao)
            adapterSimulacao.notifyItemInserted(db.listar().lastIndex)
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val id = data?.getStringExtra("id")
            val mensagem = data?.getStringExtra("nomeProduto")

            val nomeProduto = data?.getStringExtra("nomeProduto").toString()
            val custoMateria = data?.getStringExtra("custoMateria").toString()
            val custoMaoObra = data?.getStringExtra("custoMaoObra").toString()
            val custoDiverso = data?.getStringExtra("custoDiverso").toString()
            val lucro = data?.getStringExtra("lucro").toString()
            val valorSujerido = data?.getStringExtra("valorSujerido").toString()
            val tipoAtividade = data?.getStringExtra("tipoAtividade").toString()
            val tipoSimulacao = data?.getStringExtra("tipoSimulacao").toString()
            val revenda = data?.getBooleanExtra("revenda", false)
            val simulacaoEdit = SimulacaoModel(
                id?.toLong(),
                nomeProduto,
                custoMateria.toDouble(),
                custoMaoObra.toDouble(),
                custoDiverso.toDouble(),
                lucro.toDouble(),
                TipoAtividade.valueOf(tipoAtividade),
                TipoSimulacao.valueOf(tipoSimulacao),
                valorSujerido.toDouble(),
                revenda
            )

            db.atualizar(simulacaoEdit)

            val simulacao = listaSimulacao.get(id!!.toInt())
            simulacao.nomeProduto = mensagem.toString()
            adapterSimulacao.notifyItemChanged(listaSimulacao.indexOf(simulacao))
        }
    }
}

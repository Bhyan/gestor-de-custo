package com.example.gestordecusto.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gestordecusto.R
import com.google.android.material.navigation.NavigationView

class InformacaoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacao)

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
        val tipoDeImposto = findViewById<View>(R.id.tipoDeImposto) as RadioButton
        val beneficio = findViewById<View>(R.id.beneficios) as RadioButton
        tipoDeImposto.setOnClickListener{
            startActivity(Intent(this, TipoDeImpostoInformacaoActivity::class.java))
        }
        beneficio.setOnClickListener{
            startActivity(Intent(this, BeneficioInformacaoActivity::class.java))
        }

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
}
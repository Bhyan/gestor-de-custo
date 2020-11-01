package com.example.gestordecusto.repository

import com.example.gestordecusto.model.Usuario

interface UsuarioRepository {
    fun salvar(usuario: Usuario): Boolean
    fun atualizar(usuario: Usuario): Boolean
    fun findByEmail(email: String): Usuario
}
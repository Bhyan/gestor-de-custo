package com.example.gestordecusto.repository

import com.example.gestordecusto.model.UsuarioModel

interface UsuarioRepository {
    fun salvar(usuarioModel: UsuarioModel): Boolean
    fun atualizar(usuarioModel: UsuarioModel): Boolean
    fun findByEmail(email: String): UsuarioModel
}
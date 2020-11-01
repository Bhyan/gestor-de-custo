package com.example.gestordecusto.repository

import com.example.gestordecusto.model.Simulacao

interface SimulacaoRepository {
    fun salvar(simulacao: Simulacao): Boolean
    fun atualizar(simulacao: Simulacao): Boolean
    fun deletar(simulacao: Simulacao): Boolean
    fun listar(): ArrayList<Simulacao>
}
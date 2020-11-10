package com.example.gestordecusto.repository

import com.example.gestordecusto.model.SimulacaoModel

interface SimulacaoRepository {
    fun salvar(simulacaoModel: SimulacaoModel): Boolean
    fun atualizar(simulacaoModel: SimulacaoModel): Boolean
    fun deletar(id: Long?): Boolean
    fun listar(): ArrayList<SimulacaoModel>
}
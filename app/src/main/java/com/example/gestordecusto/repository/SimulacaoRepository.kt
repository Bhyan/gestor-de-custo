package com.example.gestordecusto.repository

import com.example.gestordecusto.model.SimulacaoModel

interface SimulacaoRepository {
    fun salvar(simulacaoModel: SimulacaoModel): Boolean
    fun atualizar(simulacaoModel: SimulacaoModel): Boolean
    fun deletar(simulacaoModel: SimulacaoModel): Boolean
    fun listar(): ArrayList<SimulacaoModel>
}
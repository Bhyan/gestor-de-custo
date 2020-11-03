package com.example.gestordecusto.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.gestordecusto.model.SimulacaoModel
import com.example.gestordecusto.model.TipoAtividade
import com.example.gestordecusto.model.TipoSimulacao
import com.example.gestordecusto.repository.SimulacaoRepository

class SimulacaoDAO(context: Context): SimulacaoRepository {

    private val helper: GestorCustoSqlHelper = GestorCustoSqlHelper(context)

    override fun salvar(simulacao: SimulacaoModel): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NOME_PRODUTO_SIMULACAO, simulacao.nomeProduto)
            put(COLUMN_CUSTO_MATERIA_SIMULACAO, simulacao.custoMateria)
            put(COLUMN_CUSTO_MAO_OBRA_SIMULACAO, simulacao.custoMaoObra)
            put(COLUMN_CUSTO_DIVERSOS_SIMULACAO, simulacao.custoDiversos)
            put(COLUMN_LUCRO_SIMULACAO, simulacao.lucro)
            put(COLUMN_TIPO_ATIVIDADE_SIMULACAO, simulacao.tipoAtividade.toString())
            put(COLUMN_TIPO_SIMULACAO_SIMULACAO, simulacao.tipoSimulacao.toString())
            put(COLUMN_REVENDA_SIMULACAO, simulacao.revenda)
            put(COLUMN_VALOR_SUJERIDO_SIMULACAO, simulacao.valorSujerido)
        }

        try{
            db.insert(TABLE_SIMULACAO_NAME, null, cv)
            return true
        }catch (error: Exception){
            Log.i("INFO INSERT", error.message.toString())
            return false
        }
    }

    override fun atualizar(simulacao: SimulacaoModel): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NOME_PRODUTO_SIMULACAO, simulacao.nomeProduto)
            put(COLUMN_CUSTO_MATERIA_SIMULACAO, simulacao.custoMateria)
            put(COLUMN_CUSTO_MAO_OBRA_SIMULACAO, simulacao.custoMaoObra)
            put(COLUMN_CUSTO_DIVERSOS_SIMULACAO, simulacao.custoDiversos)
            put(COLUMN_LUCRO_SIMULACAO, simulacao.lucro)
            put(COLUMN_TIPO_ATIVIDADE_SIMULACAO, simulacao.tipoAtividade.toString())
            put(COLUMN_TIPO_SIMULACAO_SIMULACAO, simulacao.tipoSimulacao.toString())
            put(COLUMN_REVENDA_SIMULACAO, simulacao.revenda)
            put(COLUMN_VALOR_SUJERIDO_SIMULACAO, simulacao.valorSujerido)
        }
        try{
            db.update(TABLE_SIMULACAO_NAME, cv, "id = " + simulacao.id, null)
            return true
        }catch (error: Exception){
            Log.i("INFO UPDATE", error.message.toString())
            return false
        }

    }

    override fun deletar(simulacao: SimulacaoModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun listar(): ArrayList<SimulacaoModel> {
        var simulacoes = ArrayList<SimulacaoModel>()
        val sql = "SELECT * FROM $TABLE_SIMULACAO_NAME"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, arrayOf())

        while(cursor.moveToNext()){
            val simulacao = montarSimulacao(cursor)
            simulacoes.add(simulacao)
        }

        return simulacoes
    }

    private fun montarSimulacao(cursor: Cursor): SimulacaoModel{
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val nomeProduto = cursor.getString(cursor.getColumnIndex(COLUMN_NOME_PRODUTO_SIMULACAO))
        val custoMaterial = cursor.getDouble(cursor.getColumnIndex(COLUMN_CUSTO_MATERIA_SIMULACAO))
        val custoMaoObra = cursor.getDouble(cursor.getColumnIndex(COLUMN_CUSTO_MAO_OBRA_SIMULACAO))
        val custoDiversos = cursor.getDouble(cursor.getColumnIndex(COLUMN_CUSTO_DIVERSOS_SIMULACAO))
        val lucro = cursor.getDouble(cursor.getColumnIndex(COLUMN_LUCRO_SIMULACAO))
        val tipoAtividade = TipoAtividade.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ATIVIDADE_SIMULACAO)))
        val tipoSimulacao = TipoSimulacao.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_SIMULACAO_SIMULACAO)))
        val revenda = cursor.getLong(cursor.getColumnIndex(COLUMN_REVENDA_SIMULACAO))
        val valorSujerido = cursor.getDouble(cursor.getColumnIndex(COLUMN_VALOR_SUJERIDO_SIMULACAO))
        var revendaBoolean = revenda == 1L

        return SimulacaoModel(id, nomeProduto, custoMaterial, custoMaoObra, custoDiversos, lucro,
            tipoAtividade, tipoSimulacao, valorSujerido, revendaBoolean)
    }
}
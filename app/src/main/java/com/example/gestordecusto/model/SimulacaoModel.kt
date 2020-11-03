package com.example.gestordecusto.model

class SimulacaoModel(
   var id: Long? = null,
   var nomeProduto: String,
   var custoMateria: Double,
   var custoMaoObra: Double,
   var custoDiversos: Double,
   var lucro: Double,
   var tipoAtividade: TipoAtividade,
   var tipoSimulacao: TipoSimulacao,
   var valorSujerido: Double,
   var revenda: Boolean?
) {}
package com.example.gestordecusto.model

class Simulacao(
   var nomeProduto: String,
   var custoMateria: Double,
   var custoMaoObra: Double,
   var custoDiversos: Double,
   var lucro: Double,
   var tipoAtividade: TipoAtividade,
   var tipoSimulacao: TipoSimulacao,
   var revenda: Boolean
) {}
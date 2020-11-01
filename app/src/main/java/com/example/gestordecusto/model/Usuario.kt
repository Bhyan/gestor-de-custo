package com.example.gestordecusto.model

data class Usuario(
    var id: Long? = null,
    var nome: String,
    var cpf: String,
    var email: String,
    var senha: String,
    var profissao: String? = null,
    var telefone: String? = null,
    var endereco: String? = null,
    var cep: String? = null
) {}
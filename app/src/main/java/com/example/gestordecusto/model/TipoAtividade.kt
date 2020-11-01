package com.example.gestordecusto.model

enum class TipoAtividade {
    PRODUTO {
        override fun getDescricao(): String {
            return "Produto"
        }
    },
    SERVICO {
        override fun getDescricao(): String {
            return "Serviço"
        }
    };

    abstract fun getDescricao(): String
}
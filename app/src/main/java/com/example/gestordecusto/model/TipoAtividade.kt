package com.example.gestordecusto.model

enum class TipoAtividade {
    PRODUTO {
        override fun getDescricao(): String {
            return "PRODUTO"
        }
    },
    SERVICO {
        override fun getDescricao(): String {
            return "SERVICO"
        }
    };

    abstract fun getDescricao(): String
}
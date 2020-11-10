package com.example.gestordecusto.model

enum class TipoSimulacao {
    SEM_IMPOSTO {
        override fun getDescricao(): String {
            return "SEM_IMPOSTO"
        }
    },
    COM_IMPOSTO {
        override fun getDescricao(): String {
            return "COM_IMPOSTO"
        }
    };

    abstract fun getDescricao(): String
}
package com.example.gestordecusto.model

enum class TipoSimulacao {
    SEM_IMPOSTO {
        override fun getDescricao(): String {
            return "Sem imposto"
        }
    },
    COM_IMPOSTO {
        override fun getDescricao(): String {
            return "Com imposto"
        }
    };

    abstract fun getDescricao(): String
}
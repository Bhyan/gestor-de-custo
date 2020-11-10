package com.example.gestordecusto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestordecusto.R
import com.example.gestordecusto.model.SimulacaoModel

class AdapterSimulacao(
    var listaSimulacao: ArrayList<SimulacaoModel>, val callback: (SimulacaoModel, Int) -> Unit,
    val longClick: (SimulacaoModel, Int) -> Unit
) : RecyclerView.Adapter<AdapterSimulacao.MyViewHolde>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSimulacao.MyViewHolde {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.adapter_simulacao, parent, false)
        val vh = MyViewHolde(item)

        vh.itemView.setOnClickListener {
            val simulacao = listaSimulacao[vh.adapterPosition]
            callback(simulacao, vh.adapterPosition)
        }

        vh.itemView.setOnLongClickListener{
            val simulacao = listaSimulacao[vh.adapterPosition]
            val posicao : Int = vh.adapterPosition
            longClick(simulacao, posicao)
            true
        }

        return vh
    }

    override fun onBindViewHolder(holder: AdapterSimulacao.MyViewHolde, position: Int) {
        val nome = listaSimulacao[position].nomeProduto
        holder.nomeSimulacao.text = nome
    }

    override fun getItemCount(): Int {
        return listaSimulacao.size
    }

    class MyViewHolde(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nomeSimulacao = itemView.findViewById<TextView>(R.id.nomeSimulacao)
    }
}
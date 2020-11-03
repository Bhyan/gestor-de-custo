package com.example.gestordecusto.ui.Profile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gestordecusto.R
import com.example.gestordecusto.activity.Main
import com.example.gestordecusto.helper.UsuarioDAO
import com.example.gestordecusto.model.Usuario


class ProfileFragment : Fragment() {
    private lateinit var ProfileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        root.findViewById<Button>(R.id.inserirBtn).setOnClickListener{
            finalizarCadastro()
        }
        root.findViewById<Button>(R.id.inserirBtn).setOnClickListener{

        }

        return root
    }

    fun finalizarCadastro() {
        ProfileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val dataNascimento = (getView()?.findViewById<View>(R.id.dataInput) as EditText).text.toString()
        val profissao = (getView()?.findViewById<View>(R.id.profissaoInput) as EditText).text.toString()
        val telefone = (getView()?.findViewById<View>(R.id.telefoneInput) as EditText).text.toString()
        val endereco = (getView()?.findViewById<View>(R.id.enderecoInput) as EditText).text.toString()
        val cep = (getView()?.findViewById<View>(R.id.cepInput) as EditText).text.toString()

        when {
            dataNascimento.isEmpty() -> {
                Toast.makeText(
                        this.context,
                        "Informe a data de nascimento",
                        Toast.LENGTH_LONG
                ).show()
            }
            profissao.isEmpty() -> {
                Toast.makeText(this.context, "Informe a profissão", Toast.LENGTH_LONG).show()
            }
            telefone.isEmpty() -> {
                Toast.makeText(this.context, "Informe o telefone", Toast.LENGTH_LONG).show()
            }
            endereco.isEmpty() -> {
                Toast.makeText(this.context, "Informe o endereço", Toast.LENGTH_LONG).show()
            }
            cep.isEmpty() -> {
                Toast.makeText(this.context, "Informe o cep", Toast.LENGTH_LONG).show()
            }
        }

        val pref = this.getActivity()?.getSharedPreferences("informacoes", 0)
        val db = this.context?.let { UsuarioDAO(it) }
        val usuario = db?.findByEmail(pref?.getString("email", null).toString())
        val usuarioAtt =
                usuario?.senha?.let {
                    Usuario(
                            usuario?.id,
                            usuario?.nome,
                            usuario?.cpf,
                            usuario?.email,
                            it,
                            profissao,
                            telefone,
                            endereco,
                            cep
                    )
                }

        if (usuarioAtt?.let { db.atualizar(it) }!!) {
            val intent = Intent(activity, Main::class.java)
            Toast.makeText(getActivity(),"Deu certo!",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
        }
    }
}
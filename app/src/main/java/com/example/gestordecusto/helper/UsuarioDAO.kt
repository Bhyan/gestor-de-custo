package com.example.gestordecusto.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.gestordecusto.exception.EmailException
import com.example.gestordecusto.model.UsuarioModel
import com.example.gestordecusto.repository.UsuarioRepository

class UsuarioDAO(context: Context): UsuarioRepository {

    private val helper: GestorCustoSqlHelper = GestorCustoSqlHelper(context)

    override fun salvar(usuarioModel: UsuarioModel): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NOME_USUARIO, usuarioModel.nome)
            put(COLUMN_CPF_USUARIO, usuarioModel.cpf)
            put(COLUMN_EMAIL_USUARIO, usuarioModel.email)
            put(COLUMN_SENHA_USUARIO, usuarioModel.senha)
        }

        try{
            db.insert(TABLE_USUARIO_NAME, null, cv)
            return true
        }catch (error: Exception){
            Log.i("INFO INSERT", error.message.toString())
            return false
        }
    }

    override fun atualizar(usuarioModel: UsuarioModel): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NOME_USUARIO, usuarioModel.nome)
            put(COLUMN_CPF_USUARIO, usuarioModel.cpf)
            put(COLUMN_EMAIL_USUARIO, usuarioModel.email)
            put(COLUMN_SENHA_USUARIO, usuarioModel.senha)
            put(COLUMN_PROFISSAO_USUARIO, usuarioModel.profissao)
            put(COLUMN_TELEFONE_USUARIO, usuarioModel.telefone)
            put(COLUMN_ENDERECO_USUARIO, usuarioModel.endereco)
            put(COLUMN_CEP_USUARIO, usuarioModel.cep)
            put(COLUMN_DATA_NASCIMENTO_USUARIO, usuarioModel.dataNascimento)
        }

        try{
            db.update(TABLE_USUARIO_NAME, cv, "id = " + usuarioModel.id, null)
            return true
        }catch (error: Exception){
            Log.i("INFO UPDATE", error.message.toString())
            return false
        }
    }

    override fun findByEmail(email: String): UsuarioModel {
        val db = helper.readableDatabase
        val sql = "SELECT * FROM $TABLE_USUARIO_NAME WHERE $COLUMN_EMAIL_USUARIO = ?"

        val cursor = db.rawQuery(sql, arrayOf(email))

        if (cursor.moveToFirst()){
            return montarUsuarioCompleto(cursor)
        }
        else{
            throw EmailException("Email não encontrado")
        }
    }

    private fun montarUsuarioCompleto(cursor: Cursor): UsuarioModel {
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val nome = cursor.getString(cursor.getColumnIndex(COLUMN_NOME_USUARIO))
        val cpf = cursor.getString(cursor.getColumnIndex(COLUMN_CPF_USUARIO))
        val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USUARIO))
        val senha = cursor.getString(cursor.getColumnIndex(COLUMN_SENHA_USUARIO))
        val profissao = cursor.getString(cursor.getColumnIndex(COLUMN_PROFISSAO_USUARIO))
        val telefone = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE_USUARIO))
        val endereco = cursor.getString(cursor.getColumnIndex(COLUMN_ENDERECO_USUARIO))
        val cep = cursor.getString(cursor.getColumnIndex(COLUMN_CEP_USUARIO))
        val dataNascimento = cursor.getString(cursor.getColumnIndex(COLUMN_DATA_NASCIMENTO_USUARIO))

        return UsuarioModel(id, nome, cpf, email, senha, profissao, telefone, endereco, cep, dataNascimento)
    }
}
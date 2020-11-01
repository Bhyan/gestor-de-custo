package com.example.gestordecusto.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.gestordecusto.exception.EmailException
import com.example.gestordecusto.model.Usuario
import com.example.gestordecusto.repository.UsuarioRepository

class UsuarioDAO(context: Context): UsuarioRepository {

    private val helper: GestorCustoSqlHelper = GestorCustoSqlHelper(context)

    override fun salvar(usuario: Usuario): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NOME_USUARIO, usuario.nome)
            put(COLUMN_CPF_USUARIO, usuario.cpf)
            put(COLUMN_EMAIL_USUARIO, usuario.email)
            put(COLUMN_SENHA_USUARIO, usuario.senha)
        }

        try{
            db.insert(TABLE_USUARIO_NAME, null, cv)
            return true
        }catch (error: Exception){
            Log.i("INFO INSERT", error.message.toString())
            return false
        }
    }

    override fun atualizar(usuario: Usuario): Boolean {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(COLUMN_NOME_USUARIO, usuario.nome)
            put(COLUMN_CPF_USUARIO, usuario.cpf)
            put(COLUMN_EMAIL_USUARIO, usuario.email)
            put(COLUMN_SENHA_USUARIO, usuario.senha)
            put(COLUMN_PROFISSAO_USUARIO, usuario.profissao)
            put(COLUMN_TELEFONE_USUARIO, usuario.telefone)
            put(COLUMN_ENDERECO_USUARIO, usuario.endereco)
            put(COLUMN_CEP_USUARIO, usuario.cep)
        }

        try{
            db.update(TABLE_USUARIO_NAME, cv, "id = " + usuario.id, null)
            return true
        }catch (error: Exception){
            Log.i("INFO UPDATE", error.message.toString())
            return false
        }
    }

    override fun findByEmail(email: String): Usuario {
        val db = helper.readableDatabase
        val sql = "SELECT * FROM $TABLE_USUARIO_NAME WHERE $COLUMN_EMAIL_USUARIO = ?"

        val cursor = db.rawQuery(sql, arrayOf(email))

        if (cursor.moveToFirst()){
            return montarUsuario(cursor)
        }
        else{
            throw EmailException("Email não encontrado")
        }
    }

    private fun montarUsuario(cursor: Cursor): Usuario {
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val nome = cursor.getString(cursor.getColumnIndex(COLUMN_NOME_USUARIO))
        val cpf = cursor.getString(cursor.getColumnIndex(COLUMN_CPF_USUARIO))
        val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USUARIO))
        val senha = cursor.getString(cursor.getColumnIndex(COLUMN_SENHA_USUARIO))

        return Usuario(id, nome, cpf, email, senha)
    }

    private fun montarUsuarioCompleto(cursor: Cursor): Usuario {
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val nome = cursor.getString(cursor.getColumnIndex(COLUMN_NOME_USUARIO))
        val cpf = cursor.getString(cursor.getColumnIndex(COLUMN_CPF_USUARIO))
        val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_USUARIO))
        val senha = cursor.getString(cursor.getColumnIndex(COLUMN_SENHA_USUARIO))
        val profissao = cursor.getString(cursor.getColumnIndex(COLUMN_PROFISSAO_USUARIO))
        val telefone = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONE_USUARIO))
        val endereco = cursor.getString(cursor.getColumnIndex(COLUMN_ENDERECO_USUARIO))
        val cep = cursor.getString(cursor.getColumnIndex(COLUMN_CEP_USUARIO))

        return Usuario(id, nome, cpf, email, senha, profissao, telefone, endereco, cep)
    }
}
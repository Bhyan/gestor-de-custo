package com.example.gestordecusto.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GestorCustoSqlHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        try{
            db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_USUARIO_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " $COLUMN_NOME_USUARIO TEXT, $COLUMN_CPF_USUARIO TEXT, $COLUMN_EMAIL_USUARIO TEXT, $COLUMN_SENHA_USUARIO TEXT, " +
                    " $COLUMN_PROFISSAO_USUARIO TEXT, $COLUMN_TELEFONE_USUARIO TEXT, $COLUMN_ENDERECO_USUARIO TEXT, $COLUMN_CEP_USUARIO TEXT, " +
                    " $COLUMN_DATA_NASCIMENTO_USUARIO TEXT)")

            db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_SIMULACAO_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " $COLUMN_NOME_PRODUTO_SIMULACAO TEXT, $COLUMN_CUSTO_MATERIA_SIMULACAO REAL, $COLUMN_CUSTO_MAO_OBRA_SIMULACAO REAL, " +
                    " $COLUMN_CUSTO_DIVERSOS_SIMULACAO REAL, $COLUMN_LUCRO_SIMULACAO REAL, $COLUMN_TIPO_ATIVIDADE_SIMULACAO TEXT, " +
                    " $COLUMN_VALOR_SUJERIDO_SIMULACAO  REAL, $COLUMN_TIPO_SIMULACAO_SIMULACAO TEXT, $COLUMN_REVENDA_SIMULACAO INTEGER)")
        }catch (error: Exception){
            Log.i("INFO DB", error.message.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS  $TABLE_USUARIO_NAME");
        db?.execSQL("DROP TABLE IF EXISTS  $TABLE_SIMULACAO_NAME");
        onCreate(db);
    }

}
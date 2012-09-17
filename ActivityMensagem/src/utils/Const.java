package utils;

import android.database.sqlite.SQLiteDatabase;
import curso.conn.WebService;
import curso.model.Configuracao;

public class Const {
	
	//public static final String URL = "http://192.168.0.2:8081/JSONTeste/";
	public static final String URL = "http://192.168.56.1:8081/JSONTeste/";
	
	public static WebService webService = null;
	
	public static Configuracao config = null;
	
	public static SQLiteDatabase db = null;
	
	public static final String METODO_CADASTRO_USUARIO =  "CadastrarUsuario.do";
	public static final String METODO_CADASTRO_PERGUNTA = "CadastrarPergunta.do";
	public static final String METODO_LOGIN_USUARIO =     "LoginUsuario.do";
	public static final String METODO_LISTA_PERGUNTA =    "ListaPergunta.do"; 
	public static final String METODO_LISTA_RESPOSTA =    "ListaResposta.do"; 
	public static final String METODO_CADASTRO_RESPOSTA = "CadastrarResposta.do";
	public static final String METODO_FINALIZA_PERGUNTA = "FinalizarPergunta.do";
	public static final String METODO_DADOS_USUARIO = 	  "DadosUsuario.do";
	
}
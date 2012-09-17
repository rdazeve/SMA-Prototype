package curso.android.DAO;

/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Const;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import curso.model.Pergunta;
import curso.model.Resposta;

public final class RespostaDAO {

	public static void initialize(Context context) {
		// Open a SQLite Database
		Const.db = context.openOrCreateDatabase("crm.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);

		createTable(Const.db, "resposta");

	}

	public static void finalizar() {
		Const.db.close();
	}

	private static void createTable(SQLiteDatabase database, String tableName) {
		try {
			database.beginTransaction();

			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "asw_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "ask_id INTEGER," 
					+ "user_id INTEGER,"
					+ "asw_resposta TEXT," 
					+ "user_name TEXT);";
			database.execSQL(tableSql);

			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public static void insert(Resposta asw) {
		try {
			Const.db.beginTransaction();

			Long ask = asw.getAsk_id();
			Long user = asw.getUser_id();
			String resposta = asw.getAsw_resposta();
			String nome = asw.getUser_name();
			String insert = "INSERT INTO resposta (ask_id, user_id, asw_resposta, user_name) VALUES (?, ?, ?, ?);";
			Const.db.execSQL(insert, new Object[] { ask, user, resposta, nome });

			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static void insertLista(JSONArray jArray) {
		Resposta asw = null;
		JSONObject json = null;
		try {
			for (int i = 0; i < jArray.length(); i++) {
				json = jArray.getJSONObject(i);
				asw = new Resposta();
				asw.setAsk_id(json.getLong("id"));
				asw.setAsk_id(json.getLong("pergunta"));
				asw.setUser_id(json.getLong("usuario"));
				asw.setUser_name(json.getString("user_name"));
				asw.setAsw_resposta(json.getString("resposta"));
				insert(asw);
				json = null;
				asw = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static List<Resposta> readAll() {
		Cursor cursor = null;
		try {
			List<Resposta> all = new ArrayList<Resposta>();

			cursor = Const.db.rawQuery("SELECT * FROM resposta", null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("asw_id");
				int askIndex = cursor.getColumnIndex("ask_id");
				int userIndex = cursor.getColumnIndex("user_id");
				int respostaIndex = cursor.getColumnIndex("asw_resposta");
				int nameIndex = cursor.getColumnIndex("user_name");
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					Long ask = Long.valueOf(cursor.getString(askIndex));
					Long user = Long.valueOf(cursor.getString(userIndex));
					String pergunta = cursor.getString(respostaIndex);
					String user_name = cursor.getString(nameIndex);

					Resposta asw = new Resposta();
					asw.setAsw_id(id);
					asw.setAsk_id(ask);
					asw.setUser_id(user);
					asw.setAsw_resposta(pergunta);
					asw.setUser_name(user_name);

					all.add(asw);

					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}

			return all;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static Resposta getPerguntaById(Resposta p) {
		Cursor cursor = null;
		try {

			cursor = Const.db.rawQuery(
					"SELECT * FROM resposta WHERE asw_id = " + p.getAsw_id(),
					null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("asw_id");
				int askIndex = cursor.getColumnIndex("ask_id");
				int userIndex = cursor.getColumnIndex("user_id");
				int respostaIndex = cursor.getColumnIndex("asw_resposta");
				int nameIndex = cursor.getColumnIndex("user_name");
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					Long ask = Long.valueOf(cursor.getString(askIndex));
					Long user = Long.valueOf(cursor.getString(userIndex));
					String resposta = cursor.getString(respostaIndex);
					String user_name = cursor.getString(nameIndex);

					Resposta answer = new Resposta();
					answer.setAsw_id(id);
					answer.setAsk_id(ask);
					answer.setUser_id(user);
					answer.setAsw_resposta(resposta);
					answer.setUser_name(user_name);

					return answer;

				} while (!cursor.isAfterLast());
			}

			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void delete(Resposta asw) {
		try {
			Const.db.beginTransaction();

			String delete = "DELETE FROM pergunta WHERE asw_id = '" + asw.getAsw_id() + "'";
			Const.db.execSQL(delete);

			Const.db.setTransactionSuccessful();
			
		} finally {
			Const.db.endTransaction();
		}
	}

	public static void deleteAll() {
		try {
			Const.db.beginTransaction();

			String delete = "DELETE FROM resposta";
			Const.db.execSQL(delete);

			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static List<Resposta> getRespostaPergunta(Pergunta p) {
		Cursor cursor = null;
		try {
			List<Resposta> questions = new ArrayList<Resposta>();

			cursor = Const.db.rawQuery("SELECT * FROM resposta WHERE ask_id = " + p.getAsk_id(), null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("asw_id");
				int askIndex = cursor.getColumnIndex("ask_id");
				int userIndex = cursor.getColumnIndex("user_id");
				int respostaIndex = cursor.getColumnIndex("asw_resposta");
				int nameIndex = cursor.getColumnIndex("user_name");
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					Long ask = Long.valueOf(cursor.getString(askIndex));
					Long user = Long.valueOf(cursor.getInt(userIndex));
					String resposta = cursor.getString(respostaIndex);
					String user_name = cursor.getString(nameIndex);

					Resposta answer = new Resposta();
					answer.setAsw_id(id);
					answer.setAsk_id(ask);
					answer.setUser_id(user);
					answer.setAsw_resposta(resposta);
					answer.setUser_name(user_name);

					questions.add(answer);

					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}

			return questions;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void atualizaRespostas() {
		try {
			deleteAll();
			String retorno = Const.webService.doPost(Const.METODO_LISTA_RESPOSTA, new ArrayList<NameValuePair>());
			JSONArray jArray = new JSONArray(retorno);
			RespostaDAO.insertLista(jArray);
 		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Const;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import curso.model.Pergunta;

public final class PerguntaDAO {

	public static void initialize(Context context) {
		// Open a SQLite Database
		Const.db = context.openOrCreateDatabase("crm.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		createTable(Const.db, "pergunta");
	}
	
	public static void finalizar() {
		Const.db.close();
	}

	private static void createTable(SQLiteDatabase database, String tableName) {
		try {
			// begin the transaction
			database.beginTransaction();

			// Create a table
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "ask_id INTEGER PRIMARY KEY,"
					+ "user_id INTEGER," 
					+ "ask_pergunta TEXT," 
					+ "user_name TEXT," 
					+ "status INTEGER);";
			database.execSQL(tableSql);

			// this makes sure transaction is committed
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public static void insert(Pergunta ask) {
		try {

			Const.db.beginTransaction();
			// insert this row
			
			Long id = ask.getAsk_id();
			Long user = ask.getUser_id();
			String pergunta = ask.getAsk_pergunta();
			String user_name = ask.getUser_name();
			int status = ask.isStatus()? 1 : 0;
			String insert = "INSERT INTO pergunta (ask_id, user_id, ask_pergunta, user_name, status) VALUES (?, ?, ?, ?, ?);";
			Const.db.execSQL(insert, new Object[] { id, user, pergunta, user_name, status });

			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static List<Pergunta> readAll() {
		Cursor cursor = null;
		try {
			List<Pergunta> all = new ArrayList<Pergunta>();

			cursor = Const.db.rawQuery("SELECT * FROM pergunta WHERE user_id <> " + Const.config.idUser, null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("ask_id");
				int userIndex = cursor.getColumnIndex("user_id");
				int perguntaIndex = cursor.getColumnIndex("ask_pergunta");
				int nomeIndex = cursor.getColumnIndex("user_name");
				int statusIndex = cursor. getColumnIndex("status");
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					Long user = Long.valueOf(cursor.getString(userIndex));
					String pergunta = cursor.getString(perguntaIndex);
					String user_name = cursor.getString(nomeIndex);
					String status = cursor.getString(statusIndex);

					Pergunta ask = new Pergunta();
					ask.setAsk_id(id);
					ask.setUser_id(user);
					ask.setAsk_pergunta(pergunta);
					ask.setUser_name(user_name);
					ask.setStatus(Integer.valueOf(status) == 1);

					all.add(ask);

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

	public static Pergunta getPerguntaById(Pergunta p) {
		Cursor cursor = null;
		try {

			cursor = Const.db.rawQuery("SELECT * FROM pergunta WHERE ask_id = " + p.getAsk_id(), null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("ask_id");
				int userIndex = cursor.getColumnIndex("user_id");
				int perguntaIndex = cursor.getColumnIndex("ask_pergunta");
				int nomeIndex = cursor.getColumnIndex("user_nome");
				
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					Long user = Long.valueOf(cursor.getString(userIndex));
					String pergunta = cursor.getString(perguntaIndex);
					String user_name = cursor.getString(nomeIndex);

					Pergunta ask = new Pergunta();
					ask.setAsk_id(id);
					ask.setUser_id(user);
					ask.setAsk_pergunta(pergunta);
					ask.setUser_name(user_name);

					return ask;

				} while (!cursor.isAfterLast());
			}

			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void delete(Pergunta ask) {
		try {
			Const.db.beginTransaction();

			String delete = "DELETE FROM pergunta WHERE ask_id='"
					+ ask.getAsk_id() + "'";
			Const.db.execSQL(delete);

			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}
	
	public static void deleteAll() {
		try {
			Const.db.beginTransaction();
			
			String delete = "DELETE FROM pergunta";
			Const.db.execSQL(delete);
			
			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static List<Pergunta> getPerguntasUsuario() {
		Cursor cursor = null;
		try {
			List<Pergunta> questions = new ArrayList<Pergunta>();

			cursor = Const.db.rawQuery("SELECT * FROM pergunta WHERE user_id =" + Const.config.idUser, null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("ask_id");
				int userIndex = cursor.getColumnIndex("user_id");
				int perguntaIndex = cursor.getColumnIndex("ask_pergunta");
				int nameIndex = cursor.getColumnIndex("user_name");
				int statusIndex = cursor.getColumnIndex("status");
				
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					Long user = Long.valueOf(cursor.getString(userIndex));
					String pergunta = cursor.getString(perguntaIndex);
					String user_name = cursor.getString(nameIndex);
					String status = cursor.getString(statusIndex);

					Pergunta ask = new Pergunta();
					ask.setAsk_id(id);
					ask.setUser_id(user);
					ask.setAsk_pergunta(pergunta);
					ask.setUser_name(user_name);
					ask.setStatus(Integer.valueOf(status) == 1);

					questions.add(ask);

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

	public static void insertLista(JSONArray jArray) {
		Pergunta ask = null;
		JSONObject json = null;
		try {
			for (int i = 0; i < jArray.length(); i++) {
				json = jArray.getJSONObject(i);
				ask = new Pergunta();
				ask.setAsk_id(json.getLong("id"));
				ask.setUser_id(json.getLong("user"));
				ask.setAsk_pergunta(json.getString("pergunta"));
				ask.setUser_name(json.getString("user_name"));
				ask.setStatus(json.getString("status").equals("true"));
				insert(ask);
				json = null;
				ask = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void atualizaPerguntas() {
		try {
			deleteAll();
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("idUser", String.valueOf(Const.config.idUser)));
			String retorno = Const.webService.doPost(Const.METODO_LISTA_PERGUNTA, param);
			JSONArray jArray = new JSONArray(retorno);
			System.out.println(jArray);
			PerguntaDAO.insertLista(jArray);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void update(Pergunta ask) {
		try {

			Const.db.beginTransaction();
			// insert this row
			
			ContentValues values = new ContentValues();
			values.put("status", ask.isStatus()? 1 : 0);
			Const.db.update("pergunta", values, "ask_id=" + ask.getAsk_id(), null);

			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}
}
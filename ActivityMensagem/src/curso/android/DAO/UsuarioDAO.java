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

import utils.Const;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import curso.model.Usuario;

public final class UsuarioDAO {

	public static void initialize(Context context) {
		Const.db = context.openOrCreateDatabase("crm.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		createTable(Const.db, "usuario");
	}

	private static void createTable(SQLiteDatabase database, String tableName) {
		try {
			database.beginTransaction();
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "user_nome TEXT," 
					+ "user_senha TEXT);";
			database.execSQL(tableSql);

			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public static void insert(Usuario user) {
		try {
			Const.db.beginTransaction();
			String nome = user.getUser_nome();
			String senha = user.getUser_senha();
			String insert = "INSERT INTO usuario (user_nome, user_senha) VALUES (?, ?);";
			Const.db.execSQL(insert, new Object[] { nome, senha });
			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static List<Usuario> readAll() {
		Cursor cursor = null;
		try {
			List<Usuario> all = new ArrayList<Usuario>();

			cursor = Const.db.rawQuery("SELECT * FROM usuario", null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("user_id");
				int nomeIndex = cursor.getColumnIndex("user_nome");
				int senhaIndex = cursor.getColumnIndex("user_senha");
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					String nome = cursor.getString(nomeIndex);
					String senha = cursor.getString(senhaIndex);

					Usuario user = new Usuario();
					user.setUser_id(id);
					user.setUser_nome(nome);
					user.setUser_senha(senha);

					all.add(user);

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
	
	public static Usuario getUserById (Long l) {
		Cursor cursor = null;
		try {

			cursor = Const.db.rawQuery("SELECT * FROM usuario WHERE user_id = " + l, null);

			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("user_id");
				int nomeIndex = cursor.getColumnIndex("user_nome");
				int senhaIndex = cursor.getColumnIndex("user_senha");
				cursor.moveToFirst();
				do {
					Long id = Long.valueOf(cursor.getInt(idIndex));
					String nome = cursor.getString(nomeIndex);
					String senha = cursor.getString(senhaIndex);

					Usuario user = new Usuario();
					user.setUser_id(id);
					user.setUser_nome(nome);
					user.setUser_senha(senha);

					return user;
					
				} while (!cursor.isAfterLast());
			}

			return null;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void delete(Usuario user) {
		try {
			Const.db.beginTransaction();

			String delete = "DELETE FROM usuario WHERE user_id='"
					+ user.getUser_id() + "'";
			Const.db.execSQL(delete);

			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}
	
	public static Long login(Usuario user) {
		Cursor cursor = null;
		Long flag = -1L;
		
		try {	
			cursor = Const.db.rawQuery("SELECT * FROM usuario", null);
			if (cursor.getCount() > 0) {
				int idIndex = cursor.getColumnIndex("user_id");
				int nomeIndex = cursor.getColumnIndex("user_nome");
				int senhaIndex = cursor.getColumnIndex("user_senha");
				cursor.moveToFirst();
				do {
					String nome = cursor.getString(nomeIndex);
					String senha = cursor.getString(senhaIndex);
					if (user.getUser_nome().equals(nome) && user.getUser_senha().equals(senha)) {
						flag = Long.parseLong(cursor.getString(idIndex));
						break;
					}
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
			return flag;

		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}

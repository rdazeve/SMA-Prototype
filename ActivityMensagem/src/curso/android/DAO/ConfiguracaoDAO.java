package curso.android.DAO;

/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

import utils.Const;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import curso.model.Configuracao;

public final class ConfiguracaoDAO {

	public static void initialize(Context context) {
		Const.db = context.openOrCreateDatabase("crm.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		createTable(Const.db, "config");
	}
	
	public static void finalizarBD() {
		Const.db.close();
	}

	private static void createTable(SQLiteDatabase database, String tableName) {
		try {
			database.beginTransaction();
			String tableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
					+ "idrs INTEGER," + "user_id INTEGER," + "user_nome TEXT)";
			database.execSQL(tableSql);

			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
		}
	}

	public static void update() {
		try {
			Const.db.beginTransaction();
			Long id = Const.config.getIdUser();
			String nome = Const.config.getUserName();
			String update = "UPDATE config SET user_id=?, user_nome=?";
			Const.db.execSQL(update, new Object[] { id, nome });
			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static void insert() {
		try {
			Const.db.beginTransaction();
			int idrs = Const.config.getIdrs();
			Long id = Const.config.getIdUser();
			String nome = Const.config.getUserName();
			String insert = "INSERT INTO config (idrs, user_id, user_nome) VALUES (?, ?, ?)";
			Const.db.execSQL(insert, new Object[] { idrs, id, nome });
			Const.db.setTransactionSuccessful();
		} finally {
			Const.db.endTransaction();
		}
	}

	public static Configuracao getConfig() {
		Cursor cursor = null;
		try {
			Configuracao config = new Configuracao();
			cursor = Const.db.rawQuery("SELECT * FROM config", null);
			if (cursor.getCount() > 0) {
				int idrsIndex = cursor.getColumnIndex("idrs");
				int idIndex = cursor.getColumnIndex("user_id");
				int nomeIndex = cursor.getColumnIndex("user_nome");
				
				cursor.moveToFirst();
				int idrs = cursor.getInt(idrsIndex);
				Long id = cursor.getLong(idIndex);
				String nome = cursor.getString(nomeIndex);

				config.setIdrs(idrs);
				config.setIdUser(id);
				config.setUserName(nome);

			}

			return config;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void delete() {
		try {
			Const.db.beginTransaction();
			String delete = "DELETE FROM config";
			Const.db.execSQL(delete);
			Const.db.setTransactionSuccessful();

		} finally {
			Const.db.endTransaction();
		}
	}

	public static void salva() {
		if (Const.config.getIdrs() == 0) {
			Const.config.setIdrs(1);
			insert();
		} else {
			update();
		}
	}

}

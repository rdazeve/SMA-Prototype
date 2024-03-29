/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package curso.model;

import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * @author openmobster@gmail.com
 * 
 */
public class ViewHelper {
	@SuppressWarnings("rawtypes")
	public static View findViewById(Activity activity, String viewId) {
		try {
			String idClass = activity.getPackageName() + ".R$id";
			Class clazz = Class.forName(idClass);
			Field field = clazz.getField(viewId);

			return activity.findViewById(field.getInt(clazz));
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int findViewId(Activity activity, String variable) {
		try {
			String idClass = activity.getPackageName() + ".R$id";
			Class clazz = Class.forName(idClass);
			Field field = clazz.getField(variable);

			return field.getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int findLayoutId(Activity activity, String variable) {
		try {
			String idClass = activity.getPackageName() + ".R$layout";
			Class clazz = Class.forName(idClass);
			Field field = clazz.getField(variable);

			return field.getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	public static AlertDialog getOkModal(Activity currentActivity,
			String title, String message) {
		AlertDialog okModal = null;

		okModal = new AlertDialog.Builder(currentActivity).setTitle(title)
				.setMessage(message).setCancelable(false).create();
		okModal.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int status) {
						dialog.dismiss();
					}
				});

		return okModal;
	}
}
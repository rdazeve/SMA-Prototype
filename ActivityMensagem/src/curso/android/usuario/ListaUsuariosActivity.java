package curso.android.usuario;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import curso.android.DAO.UsuarioDAO;
import curso.model.Usuario;
import curso.model.ViewHelper;

public class ListaUsuariosActivity extends Activity {

	public ListaUsuariosActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the initial mock up data in the sqlite database
		UsuarioDAO.initialize(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();
			this.loadUsuarios();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	@SuppressWarnings("rawtypes")
	private void loadUsuarios() {
		try {
			// render the main screen
			String layoutClass = this.getPackageName() + ".R$layout";
			String listausuario = "listausuario";
			Class clazz = Class.forName(layoutClass);
			Field field = clazz.getField(listausuario);
			int screenId = field.getInt(clazz);
			this.setContentView(screenId);

			ListView list = (ListView) ViewHelper.findViewById(this, "list");

			ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();

			// Read the tickets for display
			List<Usuario> usuarios = UsuarioDAO.readAll();
			int size = usuarios.size();
			for (int i = 0; i < size; i++) {
				Usuario user = usuarios.get(i);
				HashMap<String, String> local = new HashMap<String, String>();
				local.put("id", user.getUser_id().toString());
				local.put("nome", user.getUser_nome());
				local.put("senha", user.getUser_senha());
				listData.add(local);
			}

			// SimpleAdapter
			int row = ViewHelper.findLayoutId(this, "user_row");
			int[] cells = new int[] { ViewHelper.findViewId(this, "user_id"),
					ViewHelper.findViewId(this, "user_nome"),
					ViewHelper.findViewId(this, "user_senha") };
			String[] header = new String[] { "id", "nome", "senha" };

			SimpleAdapter listAdapter = new SimpleAdapter(this, listData, row,
					header, cells);
			list.setAdapter(listAdapter);
			OnItemClickListener showUsuarios = new ShowUsuarios(usuarios);
			list.setOnItemClickListener(showUsuarios);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	private class ShowUsuarios implements OnItemClickListener {
		private List<Usuario> usuarios;

		private ShowUsuarios(List<Usuario> usuarios) {
			this.usuarios = usuarios;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			final Usuario user = usuarios.get(position);

			// Create an Alert Dialog box
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ListaUsuariosActivity.this);
			builder = builder.setMessage("Deseja deletar esse registro?");
			builder = builder.setCancelable(true);
			builder = builder.setTitle("Excluir");

			builder.setPositiveButton("Excluir",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							// Delete the ticket
							UsuarioDAO.delete(user);
							// Reload
							ListaUsuariosActivity.this.loadUsuarios();
						}
					}).setNeutralButton("Fechar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});

			AlertDialog alert = builder.create();
			alert.show();
		}
	}

}

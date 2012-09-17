package curso.android.pergunta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import utils.Const;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import curso.android.DAO.PerguntaDAO;
import curso.android.resposta.ListaRespostaActivity;
import curso.android.usuario.PerfilUsuarioActivity;
import curso.model.Pergunta;
import curso.model.ViewHelper;

public class ListaPerguntaActivity extends Activity {

	private boolean tipo;

	public ListaPerguntaActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle data = getIntent().getExtras();
		tipo = data.getBoolean("tipo");
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();
			this.loadPerguntas();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	@SuppressWarnings("rawtypes")
	private void loadPerguntas() {
		try {
			// render the main screen
			String layoutClass = this.getPackageName() + ".R$layout";
			String listapergunta = "listapergunta";
			Class clazz = Class.forName(layoutClass);
			Field field = clazz.getField(listapergunta);
			int screenId = field.getInt(clazz);
			this.setContentView(screenId);

			ListView list = (ListView) ViewHelper.findViewById(this, "list2");

			ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();

			// Read the tickets for display
			List<Pergunta> perguntas;
			PerguntaDAO.initialize(this);
			if (tipo) {
				perguntas = PerguntaDAO.getPerguntasUsuario();
			} else {
				perguntas = PerguntaDAO.readAll();
			}
			PerguntaDAO.finalizar();
			int size = perguntas.size();
			if (size == 0) {
				Toast.makeText(this, "Não há perguntas cadastradas!", Toast.LENGTH_LONG).show();
			} else {
				for (int i = 0; i < size; i++) {
					Pergunta ask = perguntas.get(i);
					HashMap<String, String> local = new HashMap<String, String>();
					local.put("id", ask.getAsk_id().toString());
					local.put("user", ask.getUser_name());
					local.put("pergunta", ask.getAsk_pergunta());
					local.put("status", ask.isStatus() ? "A" : "F");
					listData.add(local);
				}

				// SimpleAdapter
				int row = ViewHelper.findLayoutId(this, "ask_row");
				int[] cells = new int[] {
						ViewHelper.findViewId(this, "ask_id"),
						ViewHelper.findViewId(this, "user_id"),
						ViewHelper.findViewId(this, "ask_pergunta"),
						ViewHelper.findViewId(this, "status") };
				String[] header = new String[] { "id", "user", "pergunta", "status" };

				SimpleAdapter listAdapter = new SimpleAdapter(this, listData, row, header, cells);
				list.setAdapter(listAdapter);
				OnItemClickListener showPerguntas = new ShowPerguntas(perguntas);
				list.setOnItemClickListener(showPerguntas);
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	private class ShowPerguntas implements OnItemClickListener {
		private List<Pergunta> perguntas;

		private ShowPerguntas(List<Pergunta> perguntas) {
			this.perguntas = perguntas;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			final Pergunta ask = perguntas.get(position);

			AlertDialog.Builder builder = new AlertDialog.Builder(ListaPerguntaActivity.this);
			builder = builder.setCancelable(true);

			if (tipo) {

				builder = builder.setMessage("Pergunta");

				if (ask.isStatus()) {
					builder.setPositiveButton("Respostas",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
									Intent i = new Intent( getApplicationContext(), ListaRespostaActivity.class);
									i.putExtra("pergunta", ask);
									startActivity(i);
								}
							})
							.setNeutralButton("Finalizar",
									new DialogInterface.OnClickListener() {
										public void onClick( DialogInterface dialog, int id) {
											Intent i = new Intent( getApplicationContext(), FinalizaPerguntaActivity.class);
											i.putExtra("pergunta", ask);
											startActivity(i);
										}
									})
							.setNegativeButton("Cancelar",
									new DialogInterface.OnClickListener() {
										public void onClick( DialogInterface dialog, int id) {
											dialog.dismiss();
										}
									});
				} else {
					builder.setPositiveButton("Respostas",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
									Intent i = new Intent( getApplicationContext(), ListaRespostaActivity.class);
									i.putExtra("pergunta", ask);
									startActivity(i);
								}
							}).setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
				}

			} else {
				builder = builder.setMessage(ask.getAsk_pergunta());
				builder.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								List<NameValuePair> params = new ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair("ask_id", String.valueOf(ask.getAsk_id())));
								params.add(new BasicNameValuePair("user_id", String.valueOf(Const.config.idUser)));
								params.add(new BasicNameValuePair("user_name", Const.config.userName));
								params.add(new BasicNameValuePair( "asw_resposta", "S"));
								Const.webService.doPost(Const.METODO_CADASTRO_RESPOSTA, params);
								apagaQuestao(ask);
							}
						}).setNeutralButton("Não",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								List<NameValuePair> params = new ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair("ask_id", String.valueOf(ask.getAsk_id())));
								params.add(new BasicNameValuePair("user_id", String.valueOf(Const.config.idUser)));
								params.add(new BasicNameValuePair("user_name", Const.config.userName));
								params.add(new BasicNameValuePair( "asw_resposta", "N"));
								Const.webService.doPost( Const.METODO_CADASTRO_RESPOSTA, params);
								apagaQuestao(ask);
							}
						});
			}
			
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	public void apagaQuestao(Pergunta ask) {
		PerguntaDAO.initialize(getApplicationContext());
		PerguntaDAO.delete(ask);
		PerguntaDAO.finalizar();
		loadPerguntas();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, PerfilUsuarioActivity.class);
		startActivity(i);
		finish();
	}
}

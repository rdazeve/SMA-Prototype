package curso.android.resposta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import curso.android.DAO.RespostaDAO;
import curso.android.usuario.TelaUsuarioActivity;
import curso.model.Pergunta;
import curso.model.Resposta;
import curso.model.ViewHelper;

public class ListaRespostaActivity extends Activity {

	private Pergunta p;

	public ListaRespostaActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle data = getIntent().getExtras();
		p = data.getParcelable("pergunta");
		RespostaDAO.initialize(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();
			this.loadRespostas(p);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	@SuppressWarnings("rawtypes")
	private void loadRespostas(Pergunta ask) {
		try {
			// render the main screen
			String layoutClass = this.getPackageName() + ".R$layout";
			String listaresposta = "listaresposta";
			Class clazz = Class.forName(layoutClass);
			Field field = clazz.getField(listaresposta);
			int screenId = field.getInt(clazz);
			this.setContentView(screenId);

			ListView list = (ListView) ViewHelper.findViewById(this, "list3");

			ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();

			// Read the tickets for display
			List<Resposta> resposta = RespostaDAO.getRespostaPergunta(ask);
			int size = resposta.size();
			if (size == 0) {
				Toast.makeText(this, "Não há respostas para esta pergunta!",
						Toast.LENGTH_LONG).show();
			} else {
				for (int i = 0; i < size; i++) {
					Resposta asw = resposta.get(i);
					HashMap<String, String> local = new HashMap<String, String>();
					local.put("id", String.valueOf(asw.getAsw_id()));
					local.put("user", asw.getUser_name());
					local.put("resposta", asw.getAsw_resposta());
					listData.add(local);
				}

				// SimpleAdapter
				int row = ViewHelper.findLayoutId(this, "asw_row");
				int[] cells = new int[] {
						ViewHelper.findViewId(this, "asw_id"),
						ViewHelper.findViewId(this, "user_id"),
						ViewHelper.findViewId(this, "asw_resposta") };
				String[] header = new String[] { "id", "user", "resposta" };

				SimpleAdapter listAdapter = new SimpleAdapter(this, listData,
						row, header, cells);
				list.setAdapter(listAdapter);
				OnItemClickListener showPerguntas = new ShowRespostas(resposta);
				list.setOnItemClickListener(showPerguntas);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	private class ShowRespostas implements OnItemClickListener {
		private List<Resposta> resposta;

		private ShowRespostas(List<Resposta> resposta) {
			this.resposta = resposta;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			final Resposta ask = resposta.get(position);

			Intent i = new Intent(getApplicationContext(),
					TelaUsuarioActivity.class);
			i.putExtra("user_id", ask.getUser_id());
			startActivity(i);

		}
	}
}

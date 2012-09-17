package curso.android.pergunta;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import utils.Const;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import curso.android.R;
import curso.android.DAO.PerguntaDAO;
import curso.model.Pergunta;

public class FinalizaPerguntaActivity extends Activity implements
		OnClickListener {

	Button btnFinalizaSim;
	Button btnFinalizaNao;
	Pergunta p;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finalizapergunta);
		
		Bundle data = getIntent().getExtras();
		p = data.getParcelable("pergunta");

		btnFinalizaSim = (Button) findViewById(R.id.buttonFinalizaSim);
		btnFinalizaSim.setOnClickListener(this);

		btnFinalizaNao = (Button) findViewById(R.id.buttonFinalizaNao);
		btnFinalizaNao.setOnClickListener(this);

	}

	public void onClick(View v) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ask_id", String.valueOf(p.getAsk_id())));

		if (v == btnFinalizaSim) {
			params.add(new BasicNameValuePair("veredito", "S"));
		} else if (v == btnFinalizaNao) {
			params.add(new BasicNameValuePair("veredito", "N"));
		}
		
		PerguntaDAO.initialize(this);
		p.setStatus(false);
		PerguntaDAO.update(p);
		PerguntaDAO.finalizar();
		Const.webService.doPost(Const.METODO_FINALIZA_PERGUNTA, params);

		Toast.makeText(this, "Pergunta Finalizada!", Toast.LENGTH_LONG).show();
		
		Intent i = new Intent(this, ListaPerguntaActivity.class);
		i.putExtra("tipo", true);
		startActivity(i);
		finish();

	}
}

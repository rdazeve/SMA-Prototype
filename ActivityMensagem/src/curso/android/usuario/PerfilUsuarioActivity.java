package curso.android.usuario;

import utils.Const;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import curso.android.PrototipoActivity;
import curso.android.R;
import curso.android.DAO.ConfiguracaoDAO;
import curso.android.DAO.PerguntaDAO;
import curso.android.DAO.RespostaDAO;
import curso.android.pergunta.ListaPerguntaActivity;
import curso.android.pergunta.PerguntaActivity;

public class PerfilUsuarioActivity extends Activity implements OnClickListener {

	TextView lblPerfilNomeUsuario;
	Button btnFazerPergunta = null;
	Button btnMinhasPerguntas = null;
	Button btnResponderPergunta = null;
	Button btnDeslogar = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfil);

		btnFazerPergunta = (Button) findViewById(R.id.btnPerfilPergunta);
		btnFazerPergunta.setOnClickListener(this);
		
		btnMinhasPerguntas = (Button) findViewById(R.id.btnMinhasPerguntas);
		btnMinhasPerguntas.setOnClickListener(this);
		
		btnResponderPergunta = (Button) findViewById(R.id.btnResponderPergunta);
		btnResponderPergunta.setOnClickListener(this);
		
		btnDeslogar = (Button) findViewById(R.id.btnDeslogar);
		btnDeslogar.setOnClickListener(this);

		lblPerfilNomeUsuario = (TextView) findViewById(R.id.textViewNomeUsuario);
		lblPerfilNomeUsuario.setText(Const.config.getUserName());

	}

	@Override
	public void onClick(View v) {
		Intent i;
		if (v == btnFazerPergunta) {
			i = new Intent(this, PerguntaActivity.class);
			startActivityForResult(i, 10);
			finish();

		} else if (v == btnMinhasPerguntas) {
			PerguntaDAO.initialize(this);
			PerguntaDAO.atualizaPerguntas();
			PerguntaDAO.finalizar();
			RespostaDAO.initialize(this);
			RespostaDAO.atualizaRespostas();
			RespostaDAO.finalizar();
			
			i = new Intent(this, ListaPerguntaActivity.class);
			i.putExtra("tipo", true);
			startActivity(i);
			finish();

		} else if (v == btnResponderPergunta) {
			PerguntaDAO.initialize(this);
			PerguntaDAO.atualizaPerguntas();
			PerguntaDAO.finalizar();
			i = new Intent(this, ListaPerguntaActivity.class);
			i.putExtra("tipo", false);
			startActivity(i);
			finish();

		} else if (v == btnDeslogar) {
			ConfiguracaoDAO.initialize(this);
			ConfiguracaoDAO.delete();
			ConfiguracaoDAO.finalizarBD();

			i = new Intent(this, PrototipoActivity.class);
			startActivity(i);
			finish();
		}
	}

}
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
import android.widget.EditText;
import android.widget.Toast;
import curso.android.R;
import curso.android.usuario.PerfilUsuarioActivity;

public class PerguntaActivity extends Activity implements OnClickListener {

	Button btnOkPergunta;
	EditText editTextPergunta;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pergunta);
		
		btnOkPergunta = (Button) findViewById(R.id.btnOkPergunta);
		btnOkPergunta.setOnClickListener(this);
		
		editTextPergunta = (EditText) findViewById(R.id.editTextPergunta);
	}

	public void onClick(View v) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id", "" + Const.config.idUser));
		params.add(new BasicNameValuePair("pergunta", editTextPergunta.getText().toString()));
		params.add(new BasicNameValuePair("user_name", Const.config.userName));
		
		String retorno = Const.webService.doPost(Const.METODO_CADASTRO_PERGUNTA, params);
		
		if (retorno.equals("01")) {
			Toast.makeText(this, "Pergunta Adicionada", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, PerfilUsuarioActivity.class);
			startActivity(i);
			finish();
			
		} else {
			Toast.makeText(this, "Não foi possível adicionar pergunta. Tente novamente.", Toast.LENGTH_LONG).show();
			
		}
		
	}

}
	
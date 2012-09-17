package curso.android.usuario;

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
import curso.android.PrototipoActivity;
import curso.android.R;
import curso.android.DAO.ConfiguracaoDAO;

public class LoginUsuarioActivity extends Activity implements OnClickListener {

	Button btnOkLogin;
	EditText editTextLoginUsername;
	EditText editTextLoginSenha;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btnOkLogin = (Button) findViewById(R.id.btnOkLogin);
		btnOkLogin.setOnClickListener(this);

		editTextLoginUsername = (EditText) findViewById(R.id.editTextLoginUsername);
		editTextLoginSenha = (EditText) findViewById(R.id.editTextLoginSenha);

	}

	public void onClick(View v) {
		if (editTextLoginUsername.getText().length() == 0 || editTextLoginSenha.getText().length() == 0) {
			Toast.makeText(LoginUsuarioActivity.this, "Preencha todos os campos.", Toast.LENGTH_LONG).show();

		} else {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nome", editTextLoginUsername.getText().toString()));
			params.add(new BasicNameValuePair("senha", editTextLoginSenha.getText().toString()));

			Long id = Long.parseLong(Const.webService.doPost(Const.METODO_LOGIN_USUARIO, params));
			
			if (id < 0) {
				Toast.makeText(LoginUsuarioActivity.this,"Username ou senha incorreta.", Toast.LENGTH_LONG).show();

			} else if (id == 0) {
				Toast.makeText(LoginUsuarioActivity.this,"Erro ao validar.", Toast.LENGTH_LONG).show();

			} else {
				Const.config.idUser = id;
				Const.config.userName = editTextLoginUsername.getText().toString();
				
				ConfiguracaoDAO.initialize(this);
				ConfiguracaoDAO.salva();
				ConfiguracaoDAO.finalizarBD();
				
				Intent i = new Intent(this, PerfilUsuarioActivity.class);
				startActivity(i);
				finish();
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, PrototipoActivity.class);
		startActivity(i);
		finish();
	}
	
}

package curso.android;

import utils.Const;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import curso.android.DAO.ConfiguracaoDAO;
import curso.android.usuario.CadastroUsuarioActivity;
import curso.android.usuario.LoginUsuarioActivity;
import curso.android.usuario.PerfilUsuarioActivity;
import curso.conn.WebService;

public class PrototipoActivity extends Activity implements OnClickListener {

	private Button btnCadastro;
	private Button btnLogin;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Const.webService = new WebService(Const.URL);

		ConfiguracaoDAO.initialize(this);
		Const.config = ConfiguracaoDAO.getConfig();
		ConfiguracaoDAO.finalizarBD();
		
		if (Const.config.idUser != 0) {
			Intent i = new Intent(this, PerfilUsuarioActivity.class);
			startActivity(i);
			finish();
			
		} else {
			btnCadastro = (Button) findViewById(R.id.btnCadastro);
			btnCadastro.setOnClickListener(this);
			
			btnLogin = (Button) findViewById(R.id.btnLogin);
			btnLogin.setOnClickListener(this);
		}
	}

	public void onClick(View v) {
		Intent i;
		if (v == btnCadastro) {
			i = new Intent(this, CadastroUsuarioActivity.class);
			startActivityForResult(i, 15);
		} else if (v == btnLogin) {
			i = new Intent(this, LoginUsuarioActivity.class);
			startActivity(i);
			finish();
		} 
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 110) {
			Toast.makeText(this, "Usuário Adicionado", Toast.LENGTH_LONG).show();
		}
	}
	
}
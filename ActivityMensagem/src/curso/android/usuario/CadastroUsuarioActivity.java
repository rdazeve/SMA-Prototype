package curso.android.usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import utils.Const;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import curso.android.R;

public class CadastroUsuarioActivity extends Activity implements
		OnClickListener {

	private Button btnOkCadastro;
	private EditText editTextNome;
	private EditText editTextSenha;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);

		btnOkCadastro = (Button) findViewById(R.id.btnOkCadastro);
		btnOkCadastro.setOnClickListener(this);

		editTextNome = (EditText) findViewById(R.id.editTextNomeCadastro);
		editTextSenha = (EditText) findViewById(R.id.editTextSenhaCadastro);

	}

	public void onClick(View v) {
		if (editTextNome.getText().length() == 0 || editTextSenha.getText().length() == 0) {
			Toast.makeText(CadastroUsuarioActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
			
		} else {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("nome", editTextNome.getText().toString()));
			params.add(new BasicNameValuePair("senha", editTextSenha.getText().toString()));
			
			String str = Const.webService.doPost(Const.METODO_CADASTRO_USUARIO, params);
			
			if (str.equals("01")) {
				Toast.makeText(CadastroUsuarioActivity.this, "Usuario Cadastrado", Toast.LENGTH_LONG).show();
				setResult(110);
				finish();
				
			} else {
				Toast.makeText(CadastroUsuarioActivity.this, "Nao Foi possivel cadastrar", Toast.LENGTH_LONG).show();
				
			}
			
		}
	}
}

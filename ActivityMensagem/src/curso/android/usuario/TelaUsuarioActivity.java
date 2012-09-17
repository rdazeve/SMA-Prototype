package curso.android.usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Const;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import curso.android.R;

public class TelaUsuarioActivity extends Activity implements
		OnClickListener {

	TextView lblUsuarioSelecionado;
	TextView lblReputacao;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);
		
		Bundle data = getIntent().getExtras();
		long user_id = data.getLong("user_id");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id", String.valueOf(user_id)));
		
		String retorno = Const.webService.doPost(Const.METODO_DADOS_USUARIO, params);
		
		JSONObject json;
		try {
			json = new JSONObject(retorno);
			
			lblUsuarioSelecionado = (TextView) findViewById(R.id.textViewUsuarioSelecionado);
			lblUsuarioSelecionado.setText(json.getString("usuario"));
			
			lblReputacao = (TextView) findViewById(R.id.textViewReputacao);
			lblReputacao.setText(json.getString("reputacao"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}

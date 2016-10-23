package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nexbird.nexpet.R;
import com.nexbird.nexpet.helper.SQLiteHandler;
import com.nexbird.nexpet.helper.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gabriel on 07/09/2016.
 */
public class InformacaoActivity extends Activity {

    private EditText txtEndereco, txtNumero, txtComplemento, txtCEP, txtBairro, txtTelefone, txtCelular;
    private Spinner sp_logradouro;
    private Button btnSalvar;
    private List<String> logradouros;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtNumero = (EditText) findViewById(R.id.txtNumero);
        txtComplemento = (EditText) findViewById(R.id.txtComplemento);
        txtCEP = (EditText) findViewById(R.id.txtCEP);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtCelular = (EditText) findViewById(R.id.txtCelular);
        sp_logradouro = (Spinner) findViewById(R.id.sp_Logradouro);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        logradouros = new ArrayList<String>();
        logradouros.add("Rua");
        logradouros.add("Avenida");
        logradouros.add("Beco");
        logradouros.add("Viela");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, logradouros);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_logradouro.setAdapter(adaptador);

        Bundle params = getIntent().getExtras();

        boolean alteracao = params.getBoolean("alteracao");

        if (alteracao) {
            recuperaInfo();
        }
        btnSalvar.setVisibility(View.VISIBLE);
    }

    public void recuperaInfo() {
        HashMap<String, String> user = db.getUserDetails();

        String endereco = user.get("endereco");
        String numero = user.get("numero");
        String complemento = user.get("complemento");
        String cep = user.get("cep");
        String bairro = user.get("bairro");
        String telefone = user.get("telefoneUm");
        String celular = user.get("telefoneDois");
        String logradouro = "";

        if (endereco.isEmpty()) {

        } else if (endereco.contains("Rua")) {
            logradouro = "Rua";
            String[] temp = endereco.split("Rua");
            txtEndereco.setText(temp[1]);
            sp_logradouro.setTag(logradouro);

        } else if (endereco.contains("Avenida")) {
            logradouro = "Avenida";
            String[] temp = endereco.split("Avenida");
            txtEndereco.setText(temp[1]);
            sp_logradouro.setTag(logradouro);

        } else if (endereco.contains("Beco")) {
            logradouro = "Beco";
            String[] temp = endereco.split("Beco");
            txtEndereco.setText(temp[1]);
            sp_logradouro.setTag(logradouro);

        } else if (endereco.contains("Viela")) {
            logradouro = "Viela";
            String[] temp = endereco.split("Viela");
            txtEndereco.setText(temp[1]);
            sp_logradouro.setTag(logradouro);
        }

        txtNumero.setText(numero);
        txtComplemento.setText(complemento);
        txtCEP.setText(cep);
        txtBairro.setText(bairro);
        txtTelefone.setText(telefone);
        txtCelular.setText(celular);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

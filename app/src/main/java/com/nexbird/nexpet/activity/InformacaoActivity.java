package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nexbird.nexpet.R;
import com.nexbird.nexpet.app.AppConfig;
import com.nexbird.nexpet.app.AppController;
import com.nexbird.nexpet.helper.SQLiteHandler;
import com.nexbird.nexpet.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 07/09/2016.
 */
public class InformacaoActivity extends Activity {

    private static final String TAG = InformacaoActivity.class.getSimpleName();
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

    private void prepareAlterarInfoData(final String senhaAntiga, final String senhaNova) {
        String tag_string_req = "req_DataHora";

        pDialog.setMessage("Verificando ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_VERIFICA_DATAHORA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Resposta do DataHora: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        boolean status = jObj.getBoolean("msg");

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = String.valueOf(jObj.get("error_msg"));
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Log.e(TAG, "Resposta do JSON: " + e);
                    Toast.makeText(getApplicationContext(), "Json erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erro ao verificar: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nomePetshop", "");
                params.put("dataMarcada", "");
                // params.put("horaMarcada", horaMarcada + ":00");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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

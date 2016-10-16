package com.nexbird.nexpet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 28/09/2016.
 */

public class DetalheagActivity extends AppCompatActivity {
    private static final String TAG = CadastroActivity.class.getSimpleName();
    private String unique_index;
    private String dataAgendada;
    private String nomePetshop;
    private String endereco;
    private String nomeAnimal;
    private String servico;
    private String precoFinal;
    private String confirmado;
    private TextView txtUnique, txtData, txtEndereco, txtNomePetshop, txtNomeAnimal, txtServico, txtPreco;
    private Button btnDesmarcar;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalheag);

        txtUnique = (TextView) findViewById(R.id.txtUnique);
        txtData = (TextView) findViewById(R.id.txtData);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        txtNomePetshop = (TextView) findViewById(R.id.txtPetshop);
        txtNomeAnimal = (TextView) findViewById(R.id.txtAnimal);
        txtServico = (TextView) findViewById(R.id.txtServico);
        txtPreco = (TextView) findViewById(R.id.txtPreco);
        btnDesmarcar = (Button) findViewById(R.id.btnDesmarcar);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        Bundle params = getIntent().getExtras();

        unique_index = params.getString("unique_index");
        dataAgendada = params.getString("dataAgendada");
        nomePetshop = params.getString("nomePetshop");
        //   endereco = params.getString("endereco");
        nomeAnimal = params.getString("nomeAnimal");
        servico = params.getString("servico");
        precoFinal = params.getString("precoFinal");
        confirmado = params.getString("confirmado");

        Log.e("Teste: ", dataAgendada);

        txtUnique.setText(unique_index);
        txtData.setText(dataAgendada);
        txtNomePetshop.setText(nomePetshop);
        //  txtEndereco.setText(endereco);
        txtNomeAnimal.setText(nomeAnimal);
        txtServico.setText(servico);
        txtPreco.setText(precoFinal);
        btnDesmarcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule(unique_index);
            }
        });
    }

    private void deleteSchedule(final String uidScheduled) {

        String tag_string_req = "req_login";
        final String uid = db.getUserDetails().get("uid");
        pDialog.setMessage("Entrando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DELETAR_AGENDAMENTO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta do login: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String errorMsg = jObj.getString("msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        finish();

                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("uidUser", uid);
                params.put("uidScheduled", uidScheduled);

                return params;
            }

        };
        String teste = strReq.toString();
        String teste2 = tag_string_req;
        Log.e("-----------------", teste);
        Log.e("**********", teste2);

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

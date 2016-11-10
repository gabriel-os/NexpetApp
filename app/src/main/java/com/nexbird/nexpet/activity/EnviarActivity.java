package com.nexbird.nexpet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nexbird.nexpet.R;
import com.nexbird.nexpet.app.AppConfig;
import com.nexbird.nexpet.app.AppController;
import com.nexbird.nexpet.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 30/09/2016.
 */

public class EnviarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AgendadoActivity.class.getSimpleName();
    private Button btnEmail, btnPDF, btnGaleria, btnVoltar;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private String dataAgendada, nomeAnimal, nomePetshop, servico, servicoAd, preco, formaPag, endereco;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        db = new SQLiteHandler(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnEmail = (Button) findViewById(R.id.btnEmail);
        //  btnPDF = (Button) findViewById(R.id.btnPDF);
        //btnGaleria = (Button) findViewById(R.id.btnGaleria);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        Bundle params = getIntent().getExtras();

/*        dataAgendada = params.getString("dataAgendada");
        nomePetshop = params.getString("nomePetshop");
        nomeAnimal = params.getString("nomeAnimal");
        nomePetshop = params.getString("nomePetshop");
        servico = params.getString("servico");
        servicoAd = params.getString("servicoAd");
        preco = params.getString("preco");
        formaPag = params.getString("formaPag");
        endereco = params.getString("endereco");*/

        btnEmail.setOnClickListener(this);
//        btnPDF.setOnClickListener(this);
        // btnGaleria.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
    }

    private void prepareAgendarData() {
        HashMap<String, String> user = db.getUserDetails();
        final String email = user.get("email");
        Log.e("aaaaaaaaaaaaaaaa", email);

        String tag_string_req = "req_agendado";
        pDialog.setMessage("Enviando para o email ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ENVIAR_EMAIL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Resposta da inserção: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "Resposta do JSON: " + jObj);

                    if (!error) {
                        boolean result = jObj.getBoolean("msg");

                        if (result) {
                            Toast.makeText(getApplicationContext(), "Serviço agendado com sucesso!!", Toast.LENGTH_LONG);

                            Intent i = new Intent(getApplicationContext(), EnviarActivity.class);
                            finish();
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro. Tente novamente!", Toast.LENGTH_LONG);
                        }

                    } else {
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
                Log.e(TAG, "Erro ao agendar o serviço: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("nomeAnimal", nomeAnimal);
                params.put("nomeAnimal", nomeAnimal);
                params.put("local", nomePetshop);
                params.put("servico", servico);
                params.put("preco", preco);
                params.put("servicoAdicional", servicoAd);
                params.put("dataHora", dataAgendada);

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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmail:
                Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
                finish();
                startActivity(i);

                break;
            /*case R.id.btnPDF:

                break;
            case R.id.btnGaleria:

                break;*/
            case R.id.btnVoltar:
                Intent j = new Intent(getApplicationContext(), PrincipalActivity.class);
                finish();
                startActivity(j);
                break;
        }
    }
}

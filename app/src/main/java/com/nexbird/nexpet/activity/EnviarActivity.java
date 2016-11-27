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
    private String id, dataAgendada, nomeAnimal, nomePetshop, servico, servicoAd, preco, formaPag, endereco;

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

        id = params.getString("id");
        dataAgendada = params.getString("data");
        nomePetshop = params.getString("nomePetshop");
        nomeAnimal = params.getString("nomeAnimal");
        nomePetshop = params.getString("nomePetshop");
        servico = params.getString("servico");
        servicoAd = params.getString("servicoAd");
        preco = params.getString("preco");
        formaPag = params.getString("formaPag");
        endereco = params.getString("endereco");

        btnEmail.setOnClickListener(this);
//        btnPDF.setOnClickListener(this);
        // btnGaleria.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

    }

    private void prepareEnviarData() {
        HashMap<String, String> user = db.getUserDetails();
        final String email = user.get("email");

        String tag_string_req = "req_envia";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ENVIAR_EMAIL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta do envio: " + response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {

                        String mensagem = jObj.getString("msg");
                        Toast.makeText(getApplicationContext(),
                                mensagem, Toast.LENGTH_LONG)
                                .show();

                        Intent i = new Intent(EnviarActivity.this,
                                LoginActivity.class);
                        startActivity(i);


                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erro no envio: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                Log.e("Teste", id);
                params.put("email", email);
                Log.e("Teste", email);
                params.put("nomeAnimal", nomeAnimal);
                Log.e("Teste", nomeAnimal);
                params.put("local", nomePetshop);
                Log.e("Teste", nomePetshop);
                params.put("servico", servico);
                Log.e("Teste", servico);
                params.put("preco", preco);
                Log.e("Teste", preco);
                params.put("servicoAdicional", servicoAd);
                Log.e("Teste", servicoAd);
                params.put("dataHora", dataAgendada);
                Log.e("Teste", dataAgendada);
                return params;
            }

        };

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
                prepareEnviarData();
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

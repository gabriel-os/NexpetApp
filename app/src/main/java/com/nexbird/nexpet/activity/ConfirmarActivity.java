package com.nexbird.nexpet.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
 * Created by Gabriel on 06/08/2016.
 */

public class ConfirmarActivity extends AppCompatActivity {
    private static final String TAG = ConfirmarActivity.class.getSimpleName();
    View.OnClickListener click = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAgendar:

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Deseja realmente agendar o serviço?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            prepareAgendarData();
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    AlertDialog alerta = builder.create();
                    alerta.show();
                    break;
                case R.id.btnBack:
                    break;
            }
        }
    };
    private TextView lblPetshop, lblServico, lblDataHora, lblPet, lblServicoAdicional, lblValor, lblPS, lblDH, lblS, lblP;
    private Button btnAgendar, btnBack;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;
    private String dataAgendada, nomeAnimal, nomePetshop, servico, servicoAd, preco, formaPag, endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        Bundle params = getIntent().getExtras();

        dataAgendada = params.getString("data") + " " + params.getString("hora");
        nomePetshop = params.getString("nomePetshop");
        nomeAnimal = params.getString("nomeAnimal");
        nomePetshop = params.getString("nomePetshop");
        servico = params.getString("servico");
        servicoAd = params.getString("servicoAd");
        preco = params.getString("preco");
        formaPag = params.getString("formaPag");
        endereco = params.getString("endereco");

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        lblPetshop = (TextView) findViewById(R.id.lblPetshop);
        lblServico = (TextView) findViewById(R.id.lblServico);
        lblDataHora = (TextView) findViewById(R.id.lblDataHora);
        lblServicoAdicional = (TextView)findViewById(R.id.lblServicoAdicional);
        lblValor = (TextView)findViewById(R.id.lblValor);
        lblPet = (TextView) findViewById(R.id.lblPet);
        lblPS = (TextView) findViewById(R.id.lblPS);
        lblDH = (TextView) findViewById(R.id.lblDH);
        lblS = (TextView) findViewById(R.id.lblS);
        lblP = (TextView) findViewById(R.id.lblP);

        lblPetshop.setText(nomePetshop);
        lblServico.setText(servico);
        lblDataHora.setText(dataAgendada);
        lblPet.setText(nomeAnimal);
        lblServicoAdicional.setText(servicoAd);
        lblValor.setText(preco);

        btnAgendar = (Button) findViewById(R.id.btnAgendar);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnAgendar.setOnClickListener(click);
        btnBack.setOnClickListener(click);

    }

    private void prepareAgendarData() {
        HashMap<String, String> user = db.getUserDetails();
        final String uid = user.get("uid");
        Log.e("aaaaaaaaaaaaaaaa", uid);

        String tag_string_req = "req_agendado";
        pDialog.setMessage("Agendando ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ARMAZENA_AGENDAMENTO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Resposta da inserção: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "Resposta do JSON: " + jObj);

                    if (!error) {
                        String result = jObj.getString("msg");

                        if (!result.isEmpty()) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);

                            Bundle params = new Bundle();
                            params.putString("nomeAnimal", nomeAnimal);
                            params.putString("nomePetshop", nomePetshop);
                            params.putString("endereco", endereco);
                            params.putString("servico", servico);
                            params.putString("servicoAd", "");
                            params.putString("preco", preco);
                            params.putString("formaPag", formaPag);
                            params.putString("data", dataAgendada);

                            Intent i = new Intent(getApplicationContext(), EnviarActivity.class);

                            i.putExtras(params);

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

                params.put("dataAgendada", dataAgendada);
                Log.e("Data agendada: ", dataAgendada);
                params.put("nomeAnimal", nomeAnimal);
                Log.e("Data agendada: ", nomeAnimal);
                params.put("nomePetshop", nomePetshop);
                Log.e("Data agendada: ", nomePetshop);
                params.put("unique_index", uid);
                Log.e("Data agendada: ", uid);
                params.put("servico", servico);
                Log.e("Data agendada: ", servico);
                params.put("precoFinal", preco);
                Log.e("Data agendada: ", preco);
                params.put("servicoAdicional", servicoAd);
                Log.e("Data agendada: ", servicoAd);
                params.put("formaPagamento", formaPag);
                Log.e("Data agendada: ", formaPag);
                params.put("pagou", String.valueOf(false));
                Log.e("Data agendada: ", String.valueOf(false));

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

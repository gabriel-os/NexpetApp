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
    private static final String TAG = AgendadoActivity.class.getSimpleName();
    private TextView lblPetshop, lblServico, lblDataHora, lblPet, lblPS, lblDH, lblS, lblP;
    private Button btnAgendar, btnBack;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;
    private String dataAgendada, idPet, nomePetshop, servico;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        if (!session.isLoggedIn()) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }

        Bundle params = getIntent().getExtras();

        dataAgendada = params.getString("data") + " " + params.getString("hora");
        nomePetshop = params.getString("nome");
        nomePetshop = params.getString("nomePetshop");
        servico = params.getString("servico");
        idPet = "1";

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        lblPetshop = (TextView) findViewById(R.id.lblPetshop);
        lblServico = (TextView) findViewById(R.id.lblServico);
        lblDataHora = (TextView) findViewById(R.id.lblDataHora);
        lblPet = (TextView) findViewById(R.id.lblPet);
        lblPS = (TextView) findViewById(R.id.lblPS);
        lblDH = (TextView) findViewById(R.id.lblDH);
        lblS = (TextView) findViewById(R.id.lblS);
        lblP = (TextView) findViewById(R.id.lblP);

        lblPetshop.setText(nomePetshop);
        lblServico.setText(servico);
        lblDataHora.setText(dataAgendada);
        lblPet.setText(idPet);

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
                        boolean result = jObj.getBoolean("msg");

                        if (result) {
                            Toast.makeText(getApplicationContext(), "Serviço agendado com sucesso!!", Toast.LENGTH_LONG);

                            Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
                            finish();
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro. Tente novamente!", Toast.LENGTH_LONG);
                        }

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
                Log.e(TAG, "Erro ao agendar o serviço: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                String temp = dataAgendada.replace("/", "-");
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("dataAgendada", temp);
                Log.e("Data agendada: ", temp);
                params.put("nomeAnimal", "Kim");
                params.put("nomePetshop", nomePetshop);
                params.put("unique_index", uid);
                params.put("servico", servico);
                params.put("precoFinal", "50.00");
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

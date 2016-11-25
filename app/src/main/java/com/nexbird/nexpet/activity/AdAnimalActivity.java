package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 03/11/2016.
 */

public class AdAnimalActivity extends Activity {

    private static final String TAG = AlteraSenhaActivity.class.getSimpleName();
    private EditText txtAnimal, txtCaracteristica;
    private Spinner sp_tipo, sp_raca, sp_porte;
    private RadioGroup rb_sexo;
    private ProgressDialog pDialog;
    private SQLiteHandler db;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cada);

        db = new SQLiteHandler(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        /*
        txtAnimal = (EditText) findViewById(R.id.txtAnimal);
        txtCaracteristica = (EditText) findViewById(R.id.txtCaracteristica);
        sp_tipo = (Spinner) findViewById(R.id.sp_tipo);
        sp_raca = (Spinner) findViewById(R.id.sp_raca);
        sp_porte = (Spinner) findViewById(R.id.sp_porte);
        rb_sexo = (RadioGroup) findViewById(R.id.rb_sexo);
        */

    }

    private void prepareAddAnimalData(final String nomeAnimal, final String tipo, final String raca,
                                      final String porte, final String sexo, final String caracteristica) {

        String tag_string_req = "req_RegisterAnimal";

        pDialog.setMessage("Registrando animal ...");
        showDialog();

        final String uid = db.getUserDetails().get("uid");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTRAR_ANIMAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Resposta do registro de animal: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        boolean status = jObj.getBoolean("msg");

                       /* if (!status) {
                            lblDisp.setText("Disponível");
                            lblDisp.setTextColor(Color.GREEN);
                            Log.d("Teste se foi:", " FOI");
                            btnContinuar.setEnabled(true);
                            btnVerificar.setEnabled(false);
                        } else {
                            lblDisp.setText("Indisponível");
                            Log.d("Teste se foi:", " NÃO FOI");
                            lblDisp.setTextColor(Color.RED);
                            btnContinuar.setEnabled(false);
                        }*/
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
                params.put("nome", nomeAnimal);
                params.put("sexo", sexo);
                params.put("raca", raca);
                params.put("porte", porte);
                params.put("tipo", tipo);
                params.put("caracteristica", caracteristica);
                params.put("uid", uid);
                params.put("quantidade", String.valueOf(1));
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

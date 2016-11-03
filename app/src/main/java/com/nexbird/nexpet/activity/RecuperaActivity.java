package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Gabriel on 14/06/2016.
 */
public class RecuperaActivity extends Activity {
    private static final String TAG = RecuperaActivity.class.getSimpleName();
    private Button btnRecupera;
    private EditText inputEmail;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        btnRecupera = (Button) findViewById(R.id.btnRecuperar);
        inputEmail = (EditText) findViewById(R.id.txtEmail);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnRecupera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();


                // Check for empty data in the form
                if (!email.isEmpty()) {
                    // login user
                    recuperaSenha(email);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Por favor, digite suas informações", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }


    private void recuperaSenha(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_recup";

        pDialog.setMessage("Aguarde...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RECUPERA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta da recuperação: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        String mensagem = jObj.getString("msg");
                        Toast.makeText(getApplicationContext(),
                               mensagem, Toast.LENGTH_LONG)
                                .show();

                        Intent i = new Intent(RecuperaActivity.this,
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
                Log.e(TAG, "Erro na recuperação: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }

        };
        String teste = strReq.toString();
        String teste2 = tag_string_req;
        Log.e("-----------------",teste);
        Log.e("**********", teste2);
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




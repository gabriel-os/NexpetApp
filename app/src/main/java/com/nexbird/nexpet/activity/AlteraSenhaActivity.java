package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 03/11/2016.
 */

public class AlteraSenhaActivity extends Activity {

    private static final String TAG = AlteraSenhaActivity.class.getSimpleName();
    private EditText txtSenhaAntiga, txtSenhaNova, txtSenhaConf;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private Button btnAltera;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinirsenha);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        btnAltera = (Button) findViewById(R.id.btnAltera);
        txtSenhaAntiga = (EditText) findViewById(R.id.txtSenhaAntiga);
        txtSenhaNova = (EditText) findViewById(R.id.txtSenhaNova);
        txtSenhaConf = (EditText) findViewById(R.id.txtConfirmaSenha);

        btnAltera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSenhaConf.getText().toString().equals(txtSenhaNova.getText().toString())) {
                    String temp = db.getUserDetails().get("uid");
                    prepareAlterarSenhaData(temp, txtSenhaNova.getText().toString());
                }

            }
        });

    }

    private void prepareAlterarSenhaData(final String uid, final String pass) {

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
                        String status = jObj.getString("msg");

                        Toast.makeText(getApplicationContext(),
                                status, Toast.LENGTH_LONG).show();
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
                params.put("uid", uid);
                params.put("pass", pass);
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

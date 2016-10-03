package com.nexbird.nexpet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
 * Created by Gabriel on 12/08/2016.
 */
public class CadastroCompletoActivity extends AppCompatActivity {
    private static final String TAG = CadastroCompletoActivity.class.getSimpleName();
    private TextView txtTelefone, txtEndereco;
    private Button btnTerm;
    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;
    private String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        db = new SQLiteHandler(getApplicationContext());

        txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
       // btnTerm = (Button) findViewById(R.id.btnTerm);

        btnTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = txtTelefone.getText().toString().trim();
                String endereco = txtEndereco.getText().toString().trim();
                if (!endereco.isEmpty() || !telefone.isEmpty()) {
                    HashMap<String, String> user = db.getUserDetails();
                    final String uid = user.get("uid");
                    final String name = user.get("name");
                    final String email = user.get("email");
                    final String created_at = user.get("created_at");

                    query = "update users set endereco = '" + endereco + "', telefone = '" + telefone + "' where unique_id = '" + uid + "';";
                    prepareCompleta(query, name, email, uid, endereco, telefone, created_at);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Por favor, digite suas informações!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void prepareCompleta(final String query, final String name, final String email, final String uid, final String endereco, final String telefone, final String created_at) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Concluindo cadastro ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ALTERA_DADOS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta da alteração: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        session.setFullRegistred(true);
                        // User successfully stored in MySQL
                        // Now store the user in sqlite

                        // Inserting row in users table
                        db.updateUsers(name, email, uid, created_at, endereco, telefone, "", "", "", "", "");

                        Toast.makeText(getApplicationContext(), "Cadastro concluído com sucesso!!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                CadastroCompletoActivity.this,
                                PrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Resposta do registro: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("query", query);
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

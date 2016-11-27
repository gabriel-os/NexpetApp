package com.nexbird.nexpet.activity;
/**
 * Created by Gabriel on 13/06/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
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

public class LoginActivity extends Activity {
    private static final String TAG = CadastroActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private TextView btnRecuperar;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtNome);
        btnLogin = (Button) findViewById(R.id.btnEntrar);
        btnLinkToRegister = (Button) findViewById(R.id.btnCadastrar);
        btnRecuperar = (TextView) findViewById(R.id.btnRecuperar);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
            startActivity(intent);
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();


                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Por favor, digite suas informações", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        CadastroActivity.class);
                startActivity(i);
            }
        });

        btnRecuperar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RecuperaActivity.class);
                startActivity(i);
            }
        });

    }

    private void checkLogin(final String email, final String password) {

        String tag_string_req = "req_login";

        pDialog.setMessage("Entrando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta do login: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.e(TAG, "Resposta do JSON: " + jObj);
                    if (!error) {

                        session.setLogin(true);
                        session.setFullRegistred(false);
                        session.setPetRegistred(false);

                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String endereco = user.getString("endereco");
                        String cep = user.getString("cep");
                        String bairro = user.getString("bairro");
                        String complemento = user.getString("complemento");
                        String telefone = user.getString("telefone");
                        String celular = user.getString("celular");
                        String[] tempEndereco = endereco.split(", ");
                        String created_at = user.getString("created_at");

                        db.addUser(uid, name, email, telefone, celular, tempEndereco[0], tempEndereco[1], cep, bairro, complemento, created_at);
//"cadAnimal":false,"animal":{"cont":1,"0":{"id":"44","nome":"Kim","sexo":"Macho","raca":"Maltes","porte":"Pequeno","caracteristica":"Agressivo"}}}
                        if (!jObj.getBoolean("cadAnimal")) {
                            JSONObject animal = jObj.getJSONObject("animal");
                            int cont = animal.getInt("cont");
                            for (int i = 0; i < cont; i++) {
                                JSONObject temp = animal.getJSONObject(String.valueOf(i));
                                String id = temp.getString("id");
                                String nome = temp.getString("nome");
                                String sexo = temp.getString("sexo");
                                String raca = temp.getString("raca");
                                String porte = temp.getString("porte");
                                // String tipo = temp.getString("tipo");
                                String caracteristica = temp.getString("caracteristica");
                                db.addPet(id, nome, porte, sexo, raca, "Cachorro", caracteristica);

                            }
                        }

                        Intent intent = new Intent(LoginActivity.this,
                                PrincipalActivity.class);
                        startActivity(intent);

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
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

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

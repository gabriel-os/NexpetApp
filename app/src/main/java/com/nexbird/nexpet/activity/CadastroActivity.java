package com.nexbird.nexpet.activity;
/**
 * Created by Gabriel on 13/06/2016.
 */

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
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

public class CadastroActivity extends ActivityGroup implements View.OnClickListener {
    private static final String TAG = CadastroActivity.class.getSimpleName();
    private Button btnContinuar, btnPular, btnVoltar, btnTeste;
    private TextView txtEndereco, txtNumero, txtCEP, txtComplemento, txtBairro, txtTelefone, txtCelular, txtNome, txtEmail, txtSenha, txtConfirmaSenha;
    private Spinner sp_logradouro;
    private TabHost host;
    private int[] layouts;
    /* private Button btnRegister;
     private Button btnLinkToLogin;
     private EditText inputFullName;
     private EditText inputEmail;
     private EditText inputPassword;
     private EditText inputConfirmPassword;*/
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

       /* host = (TabHost) findViewById(R.id.tabHost);*/
        btnContinuar = (Button) findViewById(R.id.btnContinuar);
        btnPular = (Button) findViewById(R.id.btnPular);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);


        /*inputFullName = (EditText) findViewById(R.id.txtNome);
        inputEmail = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtSenha);
        inputConfirmPassword = (EditText) findViewById(R.id.txtConfirma);
        btnLinkToLogin = (Button) findViewById(R.id.btnVoltar);*/

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(CadastroActivity.this,
                    PrincipalActivity.class);
            startActivity(intent);
            finish();
        }
        btnContinuar.setOnClickListener(this);
        btnPular.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        layouts = new int[]{
                R.layout.activity_welcome1,
                R.layout.activity_welcome2,
                R.layout.activity_welcome3,
                R.layout.activity_welcome4};

        // Register Button Click event
       /* btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();


                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, password, confirmPassword);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Por favor, digite suas informações!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });*/

        // Link to Login Screen----------------------------------------------------------
       /* btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });*/

        host = (TabHost) findViewById(R.id.tabHost);
        host.setup(this.getLocalActivityManager());

        TabHost.TabSpec ts1 = host.newTabSpec("tela1");
        ts1.setIndicator("Etapa 1");
        ts1.setContent(new Intent(this, BasicoActivity.class));
        host.addTab(ts1);

        TabHost.TabSpec ts2 = host.newTabSpec("tela2");
        ts2.setIndicator("Etapa 2");
        ts2.setContent(new Intent(this, InformacaoActivity.class));
        host.addTab(ts2);

        TabHost.TabSpec ts3 = host.newTabSpec("tela3");
        ts3.setIndicator("Etapa 3");
        ts3.setContent(new Intent(this, CadanimalActivity.class));
        host.addTab(ts3);

        Log.e("Teste tab: ", String.valueOf(host.getCurrentTab()));


    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */


    private int getItem(int i) {
        return host.getCurrentTab() + i;
    }

    private void registerUser(final String name, final String email, final String password,
                              final String confimPassoword, final String telefone,
                              final String celular, final String endereco, final String complemento,
                              final String cep, final String bairro) {

        if (password != confimPassoword) {

            // Tag used to cancel the request
            String tag_string_req = "req_register";

            pDialog.setMessage("Registrando ...");
            showDialog();

            StringRequest strReq = new StringRequest(Method.POST,
                    AppConfig.URL_REGISTER, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Resposta do registro: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            String uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("user");
                            String name = user.getString("name");
                            String email = user.getString("email");
                            String created_at = user
                                    .getString("criado_em");
                           //String endereco = null, telefone = null;

                            // Inserting row in users table
                            db.addUser(name, email, uid, endereco, telefone, created_at);

                            Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!!", Toast.LENGTH_LONG).show();

                            // Launch login activity
                            Intent intent = new Intent(
                                    CadastroActivity.this,
                                    LoginActivity.class);
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
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("telefone", telefone);
                    params.put("celular", celular);
                    params.put("endereco", endereco);
                    params.put("complemento", complemento);
                    params.put("cep", cep);
                    params.put("bairro", bairro);
                    //params.put("foto", foto);


                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        } else if (password != confimPassoword) {
            Toast.makeText(getApplicationContext(), "As senhas não correspondem!", Toast.LENGTH_LONG).show();
        }
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
            case R.id.btnContinuar:
                int current = getItem(+1);
                Log.e("Teste botão: ", String.valueOf(current));
                if (current == 1) {
                    btnPular.setEnabled(false);
                    btnVoltar.setText("Voltar");
                    host.setCurrentTab(current);
                } else if (current < layouts.length) {
                    // move to next screen
                    btnVoltar.setText("Voltar etapa");
                    host.setCurrentTab(current);
                } else {
                    btnVoltar.setText("Voltar etapa");
                    btnContinuar.setText("Cadastrar");
                    btnPular.setEnabled(false);
                    btnContinuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registerUser(txtNome.getText().toString(),
                                    txtEmail.getText().toString(),
                                    txtSenha.getText().toString(),
                                    txtConfirmaSenha.getText().toString(),
                                    txtTelefone.getText().toString(),
                                    txtCelular.getText().toString(),
                                    txtEndereco.getText().toString(),
                                    txtComplemento.getText().toString(),
                                    txtCEP.getText().toString(),
                                    txtBairro.getText().toString());
                            Log.e("Teste: ", "Foi!!");
                        }
                    });
                }
                break;
            case R.id.btnVoltar:
                current = getItem(-1);
                if (current == 1) {
                    btnPular.setEnabled(false);
                    btnVoltar.setText("Voltar");
                    host.setCurrentTab(current);
                } else if (current < layouts.length) {
                    // move to next screen
                    btnVoltar.setText("Voltar etapa");
                    host.setCurrentTab(current);
                } else {
                    btnVoltar.setText("Voltar etapa");
                    btnContinuar.setText("Cadastrar");
                    btnPular.setEnabled(false);
                }
                break;
            case R.id.btnPular:
                current = getItem(-1);
                if (current == 1) {
                    btnPular.setEnabled(false);
                    btnVoltar.setText("Voltar");
                } else if (current < layouts.length) {
                    // move to next screen
                    btnVoltar.setText("Voltar etapa");
                    host.setCurrentTab(current);
                } else {
                    btnVoltar.setText("Voltar etapa");
                    btnContinuar.setText("Cadastrar");
                    btnPular.setEnabled(false);
                }
                break;

        }

    }

}
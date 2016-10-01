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
import android.widget.EditText;
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
    private Button btnContinuar, btnVoltar, btnTeste;
    private TextView txtEndereco, txtNumero, txtCEP, txtComplemento, txtBairro, txtTelefone, txtCelular, txtNome, txtEmail, txtSenha, txtConfirmaSenha;
    private Spinner sp_logradouro;
    private TabHost host;
    private int[] layouts;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        host = (TabHost) findViewById(R.id.tabHost);
        btnContinuar = (Button) findViewById(R.id.btnContinuar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);


        txtNome = (EditText) findViewById(R.id.txtNomeUsuario);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConfirmaSenha = (EditText) findViewById(R.id.txtConfirmaSenha);

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
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTag(host.getCurrentTab());
            }
        });
        btnVoltar.setOnClickListener(this);

        layouts = new int[]{
                R.layout.activity_basico,
                R.layout.activity_informacao,
                R.layout.activity_cadanimal};

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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

    private int getItem(int i) {
        return host.getCurrentTab() + i;
    }

    private void registerUser(final String name, final String email, final String password,
                              final String confimPassoword/*, final String telefone,
                              final String celular, final String endereco, final String complemento,
                              final String cep, final String bairro*/) {

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
                            //db.addUser(name, email, uid, null, null, created_at);

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
                   /* params.put("telefone", telefone);
                    params.put("celular", celular);
                    params.put("endereco", endereco);
                    params.put("complemento", complemento);
                    params.put("cep", cep);
                    params.put("bairro", bairro);*/
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

    public void setCurrentTab(int index) {

        if (index == host.getCurrentTab()) {
        } else {
            host.setCurrentTab(index);
            Log.e("Teste de tela: ", String.valueOf(host.getCurrentTab()));
        }
    }

    public void setTag(int index) {
        if (index == 0) {
            btnVoltar.setText("Voltar");
            btnContinuar.setText("Próximo");
        } else if (index == 1) {
            btnVoltar.setText("Voltar etapa");
            btnContinuar.setText("Próximo");
        } else if (index == 2) {
            btnVoltar.setText("Voltar etapa");
            btnContinuar.setText("Cadastrar");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnContinuar:


                Log.e("Teste variavel: ", "");
                int current = getItem(+1);
                setCurrentTab(current);
                if (btnContinuar.getText().equals("Cadastrar") && host.getCurrentTab() == 2) {
                    btnContinuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registerUser(String.valueOf(txtNome.getText()), String.valueOf(txtEmail.getText()), String.valueOf(txtSenha.getText()), String.valueOf(txtConfirmaSenha.getText()));
                        }
                    });
                } else {
                    btnContinuar.setOnClickListener(this);

                }
                break;
            case R.id.btnVoltar:
                current = getItem(-1);
                setCurrentTab(current);
                btnContinuar.setOnClickListener(this);
                break;
            case R.id.tabHost:

                break;
        }


    }
}




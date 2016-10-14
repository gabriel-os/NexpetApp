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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nexbird.nexpet.R;
import com.nexbird.nexpet.adapter.AdaptadorAnimal;
import com.nexbird.nexpet.adapter.Animal;
import com.nexbird.nexpet.app.AppConfig;
import com.nexbird.nexpet.app.AppController;
import com.nexbird.nexpet.helper.SQLiteHandler;
import com.nexbird.nexpet.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastroActivity extends ActivityGroup implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = CadastroActivity.class.getSimpleName();
    private EditText txtEndereco, txtNumero, txtCEP, txtComplemento, txtBairro, txtTelefone, txtCelular, txtNome, txtEmail, txtSenha, txtConfirmaSenha;
    private EditText txtAnimal, txtCaracteristica;
    private RadioGroup rbGroup;
    private Spinner sp_raca, sp_porte;
    private Button btnVoltar, btnContinuar;
    private TabHost host;
    private Spinner sp_Logradouro, sp_Quantidade;
    private RadioGroup rbSexo;
    private int[] layouts;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private List<String> logradouros, numero;
    private List<Animal> listaAnimal = new ArrayList<>();
    private AdaptadorAnimal mAdapter;
    private LinearLayout linear1, linear2, linear3, linear4, linear5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        host = (TabHost) findViewById(R.id.tabHost2);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        linear5 = (LinearLayout) findViewById(R.id.linear5);

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnContinuar = (Button) findViewById(R.id.btnContinuar);

        txtNome = (EditText) findViewById(R.id.txtNomeUsuario);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConfirmaSenha = (EditText) findViewById(R.id.txtConfirmaSenha);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtNumero = (EditText) findViewById(R.id.txtNumero);
        txtComplemento = (EditText) findViewById(R.id.txtComplemento);
        txtCEP = (EditText) findViewById(R.id.txtCEP);
        txtBairro = (EditText) findViewById(R.id.txtBairro);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtCelular = (EditText) findViewById(R.id.txtCelular);
        rbSexo = (RadioGroup) findViewById(R.id.rgSexo);

        btnContinuar.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        layouts = new int[]{
                R.layout.activity_basico,
                R.layout.activity_informacao,
                R.layout.activity_cadanimal};

        host.setup();

        TabHost.TabSpec spec1 = host.newTabSpec("TAB1");
        spec1.setIndicator("Etapa 1");
        spec1.setContent(R.id.layout1);
        host.addTab(spec1);

        TabHost.TabSpec spec2 = host.newTabSpec("TAB2");
        spec2.setIndicator("Etapa 2");
        spec2.setContent(R.id.layout2);
        host.addTab(spec2);

        TabHost.TabSpec spec3 = host.newTabSpec("TAB3");
        spec3.setIndicator("Etapa 3");
        spec3.setContent(R.id.layout3);
        host.addTab(spec3);

        sp_Logradouro = (Spinner) findViewById(R.id.sp_Logradouro);

        logradouros = new ArrayList<String>();
        logradouros.add("Rua");
        logradouros.add("Avenida");
        logradouros.add("Beco");
        logradouros.add("Viela");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, logradouros);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_Logradouro.setAdapter(adaptador);

        sp_Quantidade = (Spinner) findViewById(R.id.sp_quantidade);

        List<String> numero = new ArrayList<String>();
        numero.add("0");
        numero.add("1");
        numero.add("2");
        numero.add("3");
        numero.add("4");
        numero.add("5");

        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numero);

        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_Quantidade.setAdapter(adaptador2);

        sp_Quantidade.setOnItemSelectedListener(this);

        rbSexo.check(R.id.rbMasc);
    }

    private int getItem(int i) {
        return host.getCurrentTab() + i;
    }

    private void registerUser(final String name, final String email, final String password,
                              final String confimPassoword, final String sexo, final String telefone,
                              final String celular, final String endereco, final String complemento,
                              final String cep, final String bairro) {

        if (password != confimPassoword) {

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
                            int quantidade = Integer.parseInt(sp_Quantidade.getSelectedItem().toString());

                            String uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("user");
                            String name = user.getString("name");
                            String email = user.getString("email");
                            String created_at = user.getString("criado_em");

                            if (quantidade == 0) {
                                Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(
                                        CadastroActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String nome = "";
                                String sexo = "";
                                String raca = "";
                                String porte = "";
                                String caracteristica = "";

                                for (int i = 0; i < quantidade; i++) {
                                    Animal ag = listaAnimal.get(i);

                                    nome += ag.getNome() + ",";
                                    Log.e("Nome animal: ", nome);
                                    sexo += ag.getSexo() + ",";
                                    Log.e("Sexo animal: ", sexo);
                                    raca += ag.getRaca() + ",";
                                    Log.e("Raca animal: ", raca);
                                    porte += ag.getPorte() + ",";
                                    Log.e("Porte animal: ", porte);
                                    caracteristica += ag.getCaracteristica() + ",";
                                }

                                // registerAnimal(nome, sexo, raca, porte, caracteristica, uid, quantidade);
                            }

                            Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(
                                    CadastroActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("sexo", sexo);
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

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        } else if (password != confimPassoword) {
            Toast.makeText(getApplicationContext(), "As senhas não correspondem!", Toast.LENGTH_LONG).show();
        }
    }

    private void registerAnimal(final String nome, final String sexo, final String raca,
                                final String porte, final String caracteristica, final String uid,
                                final int quantidade) {

        String tag_string_req = "req_registerAnimal";

        pDialog.setMessage("Registrando animais ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTRAR_ANIMAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta do registro: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("criado_em");
                        //String endereco = null, telefone = null;

                        //db.addUser(name, email, uid, null, null, created_at);

                        Toast.makeText(getApplicationContext(), "Usuário criado com sucesso!!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(
                                CadastroActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

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
                Map<String, String> params = new HashMap<String, String>();
                params.put("nome", nome);
                params.put("sexo", sexo);
                params.put("raca", raca);
                params.put("porte", porte);
                params.put("caracteristica", caracteristica);
                //params.put("foto", foto);
                params.put("uid", uid);
                params.put("quantidade", String.valueOf(quantidade));

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

                int current = getItem(+1);
                setCurrentTab(current);
                setTag(current);

                if (btnContinuar.getText().equals("Cadastrar") && host.getCurrentTab() == 2) {

                    btnContinuar.setOnClickListener(new View.OnClickListener() {

                        @Override

                        public void onClick(View v) {

                            int num = rbSexo.getCheckedRadioButtonId();
                            String sexo = "", nomeUsuario = "", email = "", senha = "", confSenha = "", endereco = "", numero = "", complemento = "", cep = "",
                                    bairro = "", telefone = "", celular = "";

                            nomeUsuario = String.valueOf(txtNome.getText()).trim();
                            email = String.valueOf(txtEmail.getText()).trim();
                            senha = String.valueOf(txtSenha.getText());
                            confSenha = String.valueOf(txtConfirmaSenha.getText());
                            endereco = sp_Logradouro.getSelectedItem().toString() + " " + String.valueOf(txtEndereco.getText()).trim();
                            numero = String.valueOf(txtNumero.getText());
                            complemento = String.valueOf(txtComplemento.getText());
                            cep = String.valueOf(txtCEP.getText());
                            bairro = String.valueOf(txtBairro.getText());
                            telefone = String.valueOf(txtTelefone.getText());
                            celular = String.valueOf(txtCelular.getText());


                            if (nomeUsuario.isEmpty() || email.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
                                Toast.makeText(getApplicationContext(),
                                        "Há informações faltando, verifique seus dados", Toast.LENGTH_LONG).show();
                            }

                            if (num == R.id.rbMasc) {
                                sexo = "Masculino";
                            } else if (num == R.id.rbFem) {
                                sexo = "Feminino";
                            }
                            registerUser(nomeUsuario, email, senha, confSenha, sexo, telefone, celular, endereco + ", " + numero, complemento, cep, bairro);
                        }

                    });

                } else {
                    btnContinuar.setOnClickListener(this);
                }
                break;

            case R.id.btnVoltar:
                current = getItem(-1);
                setCurrentTab(current);
                setTag(current);
                btnContinuar.setOnClickListener(this);
                break;
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int temp = Integer.parseInt(parent.getSelectedItem().toString());

        Log.e("Teste Spinner:", String.valueOf(temp)); //Teste de variavél

        switch (temp) {
            case 0:
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.GONE);
                linear3.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
                break;
            case 1:
                linear2.setVisibility(View.GONE);
                linear3.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
                break;
            case 2:
                linear3.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
                break;
            case 3:
                linear4.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
                break;
            case 4:
                linear5.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
/*

LinearLayout one = (LinearLayout) findViewById(R.id.one);
one.setVisibility(View.GONE);

I suggest that you use GONE insteady of INVISIBLE in the
onclick event because with View.GONE the place for the
layout will not be visible and the application will not
appear to have unused space in it unlike the View.INVISIBLE
that will leave the gap that is intended for the the layout

 */
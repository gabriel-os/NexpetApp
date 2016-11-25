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
    private Spinner sp_raca, sp_porte, sp_Logradouro, sp_Quantidade, sp_tipo;
    private Button btnVoltar, btnContinuar;
    private TabHost host;
    private RadioGroup rbSexo;
    private int[] layouts;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private List<String> racaGato = new ArrayList<String>();
    private List<String> racaCao = new ArrayList<String>();
    private List<String> logradouros, numero, tipo, raca;
    private List<Animal> listaAnimal = new ArrayList<>();
    private AdaptadorAnimal mAdapter;
    private LinearLayout linear1, linear2, linear3, linear4, linear5;
    private String uid;
    private ArrayAdapter<String> adaptadorRaca;
    private String sexo = "", nomeUsuario = "", email = "", senha = "", confSenha = "", endereco = "", numeroEndereco = "", complemento = "", cep = "",
            bairro = "", telefone = "", celular = "";
    private int[] txtAnimalView = {R.id.txtAnimal1, R.id.txtAnimal2, R.id.txtAnimal3, R.id.txtAnimal4, R.id.txtAnimal5};
    private int[] rbGroupView = {R.id.rbGruop1, R.id.rbGruop2, R.id.rbGruop1, R.id.rbGruop3, R.id.rbGruop4, R.id.rbGruop5};
    private int[] rbMasculinoView = {R.id.rbMasculino1, R.id.rbMasculino2, R.id.rbMasculino3, R.id.rbMasculino4, R.id.rbMasculino5};
    private int[] rbFemininoView = {R.id.rbFeminino1, R.id.rbFeminino2, R.id.rbFeminino3, R.id.rbFeminino4, R.id.rbFeminino5};
    private int[] sp_racaView = {R.id.sp_raca1, R.id.sp_raca2, R.id.sp_raca3, R.id.sp_raca4, R.id.sp_raca5};
    private int[] sp_porteView = {R.id.sp_porte1, R.id.sp_porte2, R.id.sp_porte3, R.id.sp_porte4, R.id.sp_porte5};
    private int[] sp_tipoView = {R.id.sp_tipo1, R.id.sp_tipo2, R.id.sp_tipo3, R.id.sp_tipo4, R.id.sp_tipo5};
    private int[] txtCaracteristicaView = {R.id.txtCaracteristica1, R.id.txtCaracteristica2, R.id.txtCaracteristica3, R.id.txtCaracteristica4, R.id.txtCaracteristica5};
    private int temp;


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

        ArrayAdapter<String> adaptadorQuantidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numero);

        adaptadorQuantidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_Quantidade.setAdapter(adaptadorQuantidade);

        sp_Quantidade.setOnItemSelectedListener(this);

        racaCao.add("Raça");
        racaCao.add("Maltes");
        racaCao.add("Galgo Afegão");
        racaCao.add("Cocker Spaniel");
        racaCao.add("Chow Chow");
        racaCao.add("Shitzu");
        racaCao.add("Poodle");

        racaGato.add("Raça");
        racaGato.add("Siamês");
        racaGato.add("Persa");
        racaGato.add("Ragdoll");
        racaGato.add("Abissínio");
        racaGato.add("Birmanês");
        racaGato.add("Sphynx");

        List<String> porte = new ArrayList<String>();
        porte.add("Porte");
        porte.add("Pequeno");
        porte.add("Médio");
        porte.add("Grande");
        porte.add("Gigante");

        List<String> tipo = new ArrayList<String>();
        tipo.add("Tipo");
        tipo.add("Gato");
        tipo.add("Cachorro");

        raca = new ArrayList<String>();
        raca.add("");

        adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, raca);
        ArrayAdapter<String> adaptadorPorte = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, porte);
        ArrayAdapter<String> adaptadorTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);

        // adaptadorRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptadorPorte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int temp = 0;

        while (temp < 5) {
            sp_porte = (Spinner) findViewById(sp_porteView[temp]);
            sp_porte.setAdapter(adaptadorPorte);

            sp_tipo = (Spinner) findViewById(sp_tipoView[temp]);
            sp_tipo.setAdapter(adaptadorTipo);
            sp_tipo.setOnItemSelectedListener(this);

            sp_raca = (Spinner) findViewById(sp_racaView[temp]);
            sp_raca.setAdapter(adaptadorRaca);

            temp++;
        }

        rbSexo.check(R.id.rbMasc);
        sp_tipo.setOnItemSelectedListener(this);

        linear1.setVisibility(View.GONE);
        linear2.setVisibility(View.GONE);
        linear3.setVisibility(View.GONE);
        linear4.setVisibility(View.GONE);
        linear5.setVisibility(View.GONE);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTag(host.getCurrentTab());
            }
        });
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

            pDialog.setMessage("Registrando usuário ...");
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

                            uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("user");
                            String name = user.getString("name");
                            String email = user.getString("email");
                            String created_at = user.getString("criado_em");

                            if (quantidade == 0) {
                                Intent intent = new Intent(
                                        CadastroActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String[] temp = getValues(quantidade);
                                registerAnimal(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], uid, quantidade);
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
                                final String porte, final String tipo, final String caracteristica, final String uid,
                                final int quantidade) {

        String tag_string_req = "req_registerAnimal";

        pDialog.setMessage("Registrando animal(is) ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTRAR_ANIMAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Resposta do registro: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int cont = jObj.getInt("cont");
                    boolean error = jObj.getBoolean("error");

                    Log.e("Teste de cont: ", String.valueOf(cont));
                    Log.e(TAG, "Resposta do JSON: " + jObj);

                    if (!error) {

                        JSONObject rsTemp = jObj.getJSONObject("response");
                        for (int i = 0; cont > i; i++) {
                            String temp = "";
                            JSONObject tempRow = rsTemp.getJSONObject(String.valueOf(i));
                            String id = tempRow.getString("id");
                            String nome = tempRow.getString("nome");
                            String sexo = tempRow.getString("sexo");
                            String tipo = tempRow.getString("tipo");
                            String raca = tempRow.getString("raca");
                            String porte = tempRow.getString("porte");
                            String caracteristica = tempRow.getString("caracteristica");
                        }

                    } else {
                        String errorMsg = String.valueOf(jObj.get("error_msg"));
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
                /*try {
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
                }*/ catch (JSONException e) {
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
                params.put("tipo", tipo);
                params.put("caracteristica", caracteristica);
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

    public String[] getValues(int quantidade) {

        String[] values = {"", "", "", "", "", ""};

        String temp = ",";

        for (int i = 0; i < quantidade; i++) {

            if (i - quantidade == 1 || i - quantidade == -1) {
                temp = "";
            }
            txtAnimal = (EditText) findViewById(txtAnimalView[i]);
            rbGroup = (RadioGroup) findViewById(rbGroupView[i]);
            sp_raca = (Spinner) findViewById(sp_racaView[i]);
            sp_porte = (Spinner) findViewById(sp_porteView[i]);
            sp_tipo = (Spinner) findViewById(sp_tipoView[i]);
            txtCaracteristica = (EditText) findViewById(txtCaracteristicaView[i]);

            values[0] += String.valueOf(txtAnimal.getText()).trim() + temp;

            values[4] += String.valueOf(sp_tipo.getSelectedItem() + temp);

            if (values[4].equals("Gato")) {
                values[3] += "Único";
            } else {
                values[3] += String.valueOf(sp_porte.getSelectedItem()) + temp;
            }

            if (rbGroup.getCheckedRadioButtonId() == rbMasculinoView[i]) {
                values[1] += "Macho" + temp;
            } else if (rbGroup.getCheckedRadioButtonId() == rbFemininoView[i]) {
                values[1] += "Fêmea" + temp;
            }

            values[2] += String.valueOf(sp_raca.getSelectedItem()) + temp;

            values[5] += String.valueOf(txtCaracteristica.getText()).trim() + temp;

        }
        Log.e("Teste de função: ", values[0]);
        return values;
    }

    public boolean testeEtapaUm(String nome, String email, String senha, String confirmaSenha, String sexo) {

        boolean retorno = false;

        if (nome.isEmpty() && email.isEmpty() && senha.isEmpty() && confirmaSenha.isEmpty() && sexo.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que há informações faltando!\nObs:Você não pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno = true;
        } else if (nome.length() < 6) {
            Toast.makeText(getApplicationContext(), "Ops!\nPrecisamos do seu nome completo :D",
                    Toast.LENGTH_LONG).show();
            retorno = true;
        } else if (!email.contains("@") || !email.contains(".") || email.length() < 5) {
            Toast.makeText(getApplicationContext(), "Ops!\nHá algo errado com o email digitado\nQue tal verificar? :D",
                    Toast.LENGTH_LONG).show();
            retorno = true;
        } else if (senha.isEmpty() || senha.length() < 6) {
            Toast.makeText(getApplicationContext(), "Ops!\nDigite uma senha válida e/ou com no minímo 6 caracteres :D",
                    Toast.LENGTH_LONG).show();
            retorno = true;
        } else if (confirmaSenha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que esqueceu de confimar a senha! :D",
                    Toast.LENGTH_LONG).show();
            retorno = true;
        } else if (!senha.equals(confirmaSenha)) {
            Toast.makeText(getApplicationContext(), "Ops!\nAs sennhas não correspondem :D",
                    Toast.LENGTH_LONG).show();
            retorno = true;
        }
        return retorno;
    }

    public boolean testeEtapaDois(String endereco, String numero, String complemento, String cep, String bairro, String telefone, String celular) {

        final boolean[] retorno = {false};

        if (endereco.isEmpty() && numero.isEmpty() && complemento.isEmpty() && cep.isEmpty() && bairro.isEmpty() && telefone.isEmpty() && celular.isEmpty()) {
        } else if (endereco.isEmpty() && numero.isEmpty() && complemento.isEmpty() && cep.isEmpty() && bairro.isEmpty() && telefone.isEmpty() && celular.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que há informações faltando!\nObs:Você pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno[0] = true;
        } else if (endereco.length() < 4) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que o endereço digitado não é válido!\nObs:Você pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno[0] = true;
        } else if (cep.length() < 8) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que o CEP digitado não é válido!\nObs:Você pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno[0] = true;
        } else if (bairro.length() < 2) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que o bairro digitado não é válido!\nObs:Você pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno[0] = true;
        } else if (telefone.length() < 10) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que o telefone digitado não é válido!\nVerifique se inseriu o DDD\nObs:Você pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno[0] = true;
        } else if (celular.length() < 11) {
            Toast.makeText(getApplicationContext(), "Ops!\nParece que o celular digitado não é válido!\nVerifique se inseriu o DDD\nObs:Você pode pular essa etapa",
                    Toast.LENGTH_LONG).show();
            retorno[0] = true;
        }

        return retorno[0];
    }

    public boolean testeEtapaTres(int quantidade) {

        boolean retorno = true;

        for (int i = 0; i < quantidade; i++) {
            String nomeAnimal = (findViewById(txtAnimalView[i])).toString();
            rbGroup = (RadioGroup) findViewById(rbGroupView[i]);
            String sexo = "";
            String porte = (findViewById(sp_porteView[i])).toString();
            String raca = (findViewById(sp_racaView[i])).toString();
            String tipo = (findViewById(sp_tipoView[i])).toString();
            String caracteristica = (findViewById(txtCaracteristicaView[i])).toString();

            if (rbGroup.getCheckedRadioButtonId() == rbMasculinoView[i]) {
                sexo += "Masculino";
            } else if (rbGroup.getCheckedRadioButtonId() == rbFemininoView[i]) {
                sexo += "Feminino";
            }

            if (quantidade == 0) {

            } else if (nomeAnimal.isEmpty() && sexo.isEmpty() || porte.isEmpty() || raca.isEmpty() || tipo.isEmpty() || caracteristica.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ops!\nParece que há informações faltando!\nObs:Você pode pular essa etapa",
                        Toast.LENGTH_LONG).show();
                return false;
            } else if (nomeAnimal.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ops!\nParece que esqueceu o nome do " + (quantidade + 1) + "° pet!\nObs:Você pode pular essa etapa",
                        Toast.LENGTH_LONG).show();
                return false;
            } else if (sexo.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ops!\nParece que esqueceu o sexo do " + (quantidade + 1) + "° pet!\nObs:Você pode pular essa etapa",
                        Toast.LENGTH_LONG).show();
                return false;
            } else if (porte.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ops!\nParece que esqueceu de selecionar o porte do " + (quantidade + 1) + "° pet!\nObs:Você pode pular essa etapa",
                        Toast.LENGTH_LONG).show();
                return false;
            } else if (raca.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ops!\nParece que esqueceu de selecionar a raça do " + (quantidade + 1) + "° pet!\nObs:Você pode pular essa etapa",
                        Toast.LENGTH_LONG).show();
                return false;
            } else if (tipo.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ops!\nParece que esqueceu de selecionar o tipo do " + (quantidade + 1) + "° pet!\nObs:Você pode pular essa etapa",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return retorno;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnContinuar:

                if (host.getCurrentTab() == 0) {
                    nomeUsuario = String.valueOf(txtNome.getText()).trim();
                    email = String.valueOf(txtEmail.getText()).trim();
                    senha = String.valueOf(txtSenha.getText());
                    confSenha = String.valueOf(txtConfirmaSenha.getText());

                    int num = rbSexo.getCheckedRadioButtonId();
                    if (num == R.id.rbMasc) {
                        sexo = "Masculino";
                    } else if (num == R.id.rbFem) {
                        sexo = "Feminino";
                    } else {
                        sexo = "";
                    }

                    if (testeEtapaUm(nomeUsuario, email, senha, confSenha, sexo)) {
                        break;
                    }
                } else if (host.getCurrentTab() == 1) {
                    endereco = String.valueOf(txtEndereco.getText()).trim();
                    numeroEndereco = String.valueOf(txtNumero.getText());
                    complemento = String.valueOf(txtComplemento.getText());
                    cep = String.valueOf(txtCEP.getText());
                    bairro = String.valueOf(txtBairro.getText());
                    telefone = String.valueOf(txtTelefone.getText());
                    celular = String.valueOf(txtCelular.getText());
                    if (testeEtapaDois(endereco, numeroEndereco, complemento, cep, bairro, telefone, celular)) {
                        break;
                    }
                }

                int current = getItem(+1);
                setCurrentTab(current);
                setTag(current);

                if (btnContinuar.getText().equals("Cadastrar") && host.getCurrentTab() == 2) {
                    temp = Integer.parseInt(sp_Quantidade.getSelectedItem().toString());

                    if (testeEtapaTres(temp)) {
                        btnContinuar.setOnClickListener(new View.OnClickListener() {

                            @Override

                            public void onClick(View v) {
                                registerUser(nomeUsuario, email, senha, confSenha, sexo, telefone, celular, endereco + ", " + numero, complemento, cep, bairro);
                            }

                        });
                    }

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
        String tipo;
        switch (parent.getId()) {
            case R.id.sp_quantidade:
                temp = Integer.parseInt(parent.getSelectedItem().toString());

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
                        linear1.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.GONE);
                        linear3.setVisibility(View.GONE);
                        linear4.setVisibility(View.GONE);
                        linear5.setVisibility(View.GONE);
                        break;
                    case 2:
                        linear1.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.VISIBLE);
                        linear3.setVisibility(View.GONE);
                        linear4.setVisibility(View.GONE);
                        linear5.setVisibility(View.GONE);
                        break;
                    case 3:
                        linear1.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.VISIBLE);
                        linear3.setVisibility(View.VISIBLE);
                        linear4.setVisibility(View.GONE);
                        linear5.setVisibility(View.GONE);
                        break;
                    case 4:
                        linear1.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.VISIBLE);
                        linear3.setVisibility(View.VISIBLE);
                        linear4.setVisibility(View.VISIBLE);
                        linear5.setVisibility(View.GONE);
                        break;
                    case 5:
                        linear1.setVisibility(View.VISIBLE);
                        linear2.setVisibility(View.VISIBLE);
                        linear3.setVisibility(View.VISIBLE);
                        linear4.setVisibility(View.VISIBLE);
                        linear5.setVisibility(View.VISIBLE);
                        break;
                }
                break;

            case R.id.sp_tipo1:
                tipo = parent.getSelectedItem().toString();
                sp_porte = (Spinner) findViewById(R.id.sp_porte1);

                if (tipo.equals("Gato")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[0]);
                    sp_porte.setEnabled(false);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaGato);
                    sp_raca.setAdapter(adaptadorRaca);
                } else if (tipo.equals("Cachorro")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[0]);
                    sp_porte.setEnabled(true);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaCao);
                    sp_raca.setAdapter(adaptadorRaca);
                }
                break;

            case R.id.sp_tipo2:
                tipo = parent.getSelectedItem().toString();
                sp_porte = (Spinner) findViewById(R.id.sp_porte2);

                if (tipo.equals("Gato")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[1]);
                    sp_porte.setEnabled(false);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaGato);
                    sp_raca.setAdapter(adaptadorRaca);
                } else if (tipo.equals("Cachorro")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[1]);
                    sp_porte.setEnabled(true);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaCao);
                    sp_raca.setAdapter(adaptadorRaca);
                }
                break;

            case R.id.sp_tipo3:
                tipo = parent.getSelectedItem().toString();
                sp_porte = (Spinner) findViewById(R.id.sp_porte3);

                if (tipo.equals("Gato")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[2]);
                    sp_porte.setEnabled(false);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaGato);
                    sp_raca.setAdapter(adaptadorRaca);
                } else if (tipo.equals("Cachorro")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[2]);
                    sp_porte.setEnabled(true);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaCao);
                    sp_raca.setAdapter(adaptadorRaca);
                }
                break;

            case R.id.sp_tipo4:
                tipo = parent.getSelectedItem().toString();
                sp_porte = (Spinner) findViewById(R.id.sp_porte4);

                if (tipo.equals("Gato")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[3]);
                    sp_porte.setEnabled(false);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaGato);
                    sp_raca.setAdapter(adaptadorRaca);
                } else if (tipo.equals("Cachorro")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[3]);
                    sp_porte.setEnabled(true);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaCao);
                    sp_raca.setAdapter(adaptadorRaca);
                }
                break;

            case R.id.sp_tipo5:
                tipo = parent.getSelectedItem().toString();
                sp_porte = (Spinner) findViewById(R.id.sp_porte5);

                if (tipo.equals("Gato")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[4]);
                    sp_porte.setEnabled(false);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaGato);
                    sp_raca.setAdapter(adaptadorRaca);
                } else if (tipo.equals("Cachorro")) {
                    sp_raca = (Spinner) findViewById(sp_racaView[4]);
                    sp_porte.setEnabled(true);
                    ArrayAdapter<String> adaptadorRaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, racaCao);
                    sp_raca.setAdapter(adaptadorRaca);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
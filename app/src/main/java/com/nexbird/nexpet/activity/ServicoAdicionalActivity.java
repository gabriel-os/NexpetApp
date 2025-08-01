package com.nexbird.nexpet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nexbird.nexpet.R;
import com.nexbird.nexpet.adapter.AdaptadorServicoAd;
import com.nexbird.nexpet.adapter.ServicoAdicional;
import com.nexbird.nexpet.app.AppConfig;
import com.nexbird.nexpet.app.AppController;
import com.nexbird.nexpet.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel on 15/10/2016.
 */

public class ServicoAdicionalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SelecionarhoraActivity.class.getSimpleName();
    private static HashMap rs;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private RecyclerView recyclerView;
    private Button btnProximo;
    private AdaptadorServicoAd mAdapter;
    private List<ServicoAdicional> listaServico;
    private RadioGroup rgPagamento;
    private String nomeAnimal, nomePetshop, endereco, servico, preco, formaPag, data, hora;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicoad);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        btnProximo = (Button) findViewById(R.id.btnProximo);
        rgPagamento = (RadioGroup) findViewById(R.id.rgPagamento);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view4);

        listaServico = new ArrayList<ServicoAdicional>();

        ServicoAdicional st = new ServicoAdicional("Corte de unha", "10.00", "Corta a unha do seu pet!", false);
        listaServico.add(st);

        st = new ServicoAdicional("Leva e traz", "25.00", "Leva e trás o seu animal de casa para o petshop", false);
        listaServico.add(st);

        mAdapter = new AdaptadorServicoAd(listaServico);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(mAdapter);

       /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerAndSeparator(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);*/

        Bundle params = getIntent().getExtras();

        nomeAnimal = params.getString("nomeAnimal");
        nomePetshop = params.getString("nomePetshop");
        endereco = params.getString("endereco");
        servico = params.getString("servico");
        preco = params.getString("preco");
        data = params.getString("data");
        hora = params.getString("hora");

        // getAdditional();

        btnProximo.setOnClickListener(this);
    }

    public void getAdditional() {

        rs = new HashMap<Integer, String>();

        final HashMap<Integer, String> info = new HashMap<Integer, String>();

        String tag_string_req = "req_DataHora";

        pDialog.setMessage("Verificando ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SERVICO_ADCIONAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Resposta do Serviço adicional: " + response.toString());
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
                            temp += tempRow.getString("nomeServico") + ",,,";
                            temp += tempRow.getString("preco") + ",,,";
                            temp += tempRow.getString("descricaoServico");

                            info.put(i, temp);
                            Log.e("Linhas: ", String.valueOf(info.get(i)));
                            ServicoAdicionalActivity.rs = info;
                        }
                        Log.e("Array: ", String.valueOf(info));
                        Log.e("Número de info: ", String.valueOf(info.size()));
                        for (int i = 0; i < info.size(); i++) {
                            Log.e("For: ", String.valueOf(i));

                            String[] temp = info.get((i)).split(",,,");
                            Log.e("Teste Array: ", String.valueOf(temp[0]));
                            ServicoAdicional ag = new ServicoAdicional(temp[0], temp[1], temp[2], false);
                            listaServico.add(ag);
                            mAdapter.notifyDataSetChanged();
                        }

                    } else {
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
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("uid", idPetshop);
                // params.put("nome", servico);
                // Log.e("Teste servico: ", servico);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnProximo:

                switch (rgPagamento.getCheckedRadioButtonId()) {

                    case R.id.rbDinheiro:
                        formaPag = "Dinheiro";
                        break;

                    case R.id.rbDebito:
                        formaPag = "Débito";
                        break;

                    case R.id.rbCredito:
                        formaPag = "Crédito";
                        break;

                }

                String servicoAdicional = "";
                double temp = Double.parseDouble(preco);
                List<ServicoAdicional> servicoAd = ((AdaptadorServicoAd) mAdapter)
                        .getServicoSelecionado();

                for (int i = 0; i < servicoAd.size(); i++) {
                    ServicoAdicional ser = servicoAd.get(i);
                    if (ser.isSelected() == true) {
                        if (i == 0) {
                            servicoAdicional = servicoAdicional + ser.getNomeServico().toString();
                            temp += Double.parseDouble(ser.getPreco().toString());
                        } else {
                            servicoAdicional = servicoAdicional + ", " + ser.getNomeServico().toString();
                            temp += Double.parseDouble(ser.getPreco().toString());
                        }
                    }

                }

                Intent j = new Intent(getApplicationContext(), ConfirmarActivity.class);

                Bundle params = new Bundle();

                Log.e("Teste preço total: ", String.valueOf(temp));
                Log.e("Teste serviço ad: ", servicoAdicional);

                params.putString("nomeAnimal", nomeAnimal);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", endereco);
                params.putString("servico", servico);
                params.putString("servicoAd", servicoAdicional);
                params.putString("preco", String.valueOf(temp));
                params.putString("formaPag", formaPag);
                params.putString("data", data);
                params.putString("hora", hora);

                j.putExtras(params);

                startActivity(j);

                break;
        }
    }
}

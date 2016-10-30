package com.nexbird.nexpet.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nexbird.nexpet.R;
import com.nexbird.nexpet.adapter.AdaptadorHora;
import com.nexbird.nexpet.app.AppConfig;
import com.nexbird.nexpet.app.AppController;
import com.nexbird.nexpet.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 06/08/2016.
 */

public class SelecionarhoraActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = SelecionarhoraActivity.class.getSimpleName();
    private static HashMap rs;
    private Button btnData;
    private TextView lblData, lblAtiv;
    private GridView gridView;
    private AdaptadorHora mAdapter;
    private String idPetshop, nomePetshop, enderecoPetshop, servico, dataMarcada, precoP, precoM, precoG, precoGG, precoGato, horaFunc,
            nome, nomePet, portePet, racaPet, tipoPet, caracPet, horaAbertura, horaFecha, hora = "";
    private int duracaoCao, duracaoGato;
    private ProgressDialog pDialog;
    private ArrayList<String> horas = new ArrayList<>();
    private Spinner sp_animal;
    private ArrayList<String> animal;
    private ArrayList resultSQL;
    private SQLiteHandler db;
    private String[] temp;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        btnData = (Button) findViewById(R.id.btnData);
        lblData = (TextView) findViewById(R.id.lblData);
        lblAtiv = (TextView) findViewById(R.id.lblAtiv);
        gridView = (GridView) findViewById(R.id.gridView);
        sp_animal = (Spinner) findViewById(R.id.spAnimal);

        btnData.setEnabled(false);

        resultSQL = db.getPetNameAndType();

        Log.e("Teste de SQL:", String.valueOf(resultSQL));

        animal = new ArrayList<String>();
        animal.add("");

        for (int i = 0; i < resultSQL.size(); i++) {
            switch (i) {
                case 1:
                    animal.add(String.valueOf(resultSQL.get(i)));
                    break;
                case 8:
                    animal.add(String.valueOf(resultSQL.get(i)));
                    break;
                case 14:
                    animal.add(String.valueOf(resultSQL.get(i)));
                    break;
                case 20:
                    animal.add(String.valueOf(resultSQL.get(i)));
                    break;
                case 26:
                    animal.add(String.valueOf(resultSQL.get(i)));
                    break;
            }
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, animal);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_animal.setAdapter(adaptador);

        Bundle params = getIntent().getExtras();

        nome = params.getString("nome");
        precoP = params.getString("precoP");
        precoM = params.getString("precoM");
        precoG = params.getString("precoG");
        precoGG = params.getString("precoGG");
        precoGato = params.getString("precoGato");
        duracaoCao = params.getInt("duracaoCao");
        duracaoGato = params.getInt("duracaoGato");
        horaFunc = params.getString("hora");

        temp = horaFunc.split(" às ");

        idPetshop = params.getString("idPetshop");
        nomePetshop = params.getString("nomePetshop");
        enderecoPetshop = params.getString("endereco");

        mAdapter = new AdaptadorHora(this, horas);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                hora = (String) ((TextView) v.findViewById(R.id.grid_item_label)).getText();
                String preco = "";
                String animal = sp_animal.getSelectedItem().toString();

                if (tipoPet.equals("Gato")) {
                    preco = precoGato;
                } else {
                    switch (portePet) {

                        case "Pequeno":
                            preco = precoP;
                            break;

                        case "Médio":
                            preco = precoM;
                            break;

                        case "Grande":
                            preco = precoG;
                            break;

                        case "Gigante":
                            preco = precoGG;
                            break;
                    }
                }

                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.grid_item_label))
                                .getText(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ServicoAdicionalActivity.class);

                Bundle params = new Bundle();

                params.putString("nomeAnimal", nomePet);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", nome);
                params.putString("preco", preco);
                params.putString("data", dataMarcada);
                params.putString("hora", hora);

                i.putExtras(params);

                startActivity(i);

            }
        });

        btnData.setOnClickListener(this);

        sp_animal.setOnItemSelectedListener(this);
    }

    private void prepareSelecionarData() {
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
                params.put("nomePetshop", nomePetshop);
                params.put("dataMarcada", dataMarcada);
                // params.put("horaMarcada", horaMarcada + ":00");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void generateHours(int duracao, String horaAbertura, String horaFechamento) {

        int diferencaHora = 0, diferencaMinuto = 0, contHora = 0, contMin = 0;
        String[] temp1 = horaAbertura.split(":");
        String[] temp2 = horaFechamento.split(":");
        int[] horaAb = new int[2];
        int[] horaF = new int[2];
        for (int i = 0; i < 2; i++) {
            horaAb[i] = Integer.parseInt(temp1[i]);
            horaF[i] = Integer.parseInt(temp2[i]);

            if (i == 0) {
                diferencaHora = horaF[i] - horaAb[i];
                Log.e("Teste de hora11: ", String.valueOf(horaAb[i]));
                Log.e("Teste de hora: ", String.valueOf(diferencaHora));
            } else if (i == 1) {
                diferencaMinuto = horaF[i] - horaAb[i];
                Log.e("Teste de minuto: ", String.valueOf(diferencaMinuto));
            }
        }

        contHora = horaAb[0];
        contMin = horaAb[1];
        Log.e("Teste horaF: ", String.valueOf(horaF[0]));
        while (contHora < horaF[0] || contMin < horaF[1]) {

            Log.e("Teste horaAb2: ", String.valueOf(horaAb[1]));
            String horaGerada = "";
            String min = "";
            contMin += duracao;

            if (contMin >= 60) {
                contMin -= 60;
                contHora++;
            }
            min = String.valueOf(contMin);
            if (contMin == 0) {
                min = "00";
            }
            horaGerada += contHora + ":" + min;
            Log.e("Teste geração hora: ", horaGerada);
            horas.add(horaGerada);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnData:
                final Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(SelecionarhoraActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String erg = "";
                                if (dayOfMonth < 10) {
                                    erg = "0" + String.valueOf(dayOfMonth);
                                } else if (dayOfMonth > 9) {
                                    erg = String.valueOf(dayOfMonth);
                                }
                                if (monthOfYear < 10) {
                                    erg += "/0" + String.valueOf(monthOfYear + 1);
                                } else if (monthOfYear > 9) {
                                    erg += "/" + String.valueOf(monthOfYear + 1);
                                }
                                erg += "/" + year;
                                dataMarcada = erg;
                                lblData.setText(erg);
                                lblAtiv.setText("Selecione a hora:");
                                gridView.invalidateViews();
                                horas.clear();
                                mAdapter.notifyDataSetChanged();
                                if (tipoPet.equals("Cachorro")) {
                                    generateHours(duracaoCao, temp[0], temp[1]);
                                } else if (tipoPet.equals("Gato")) {
                                    generateHours(duracaoGato, temp[0], temp[1]);
                                }
                            }
                        }, y, m, d);
                dp.show();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        gridView.invalidateViews();
        horas.clear();
        mAdapter.notifyDataSetChanged();

        String temp = parent.getSelectedItem().toString();
        int numPosition = parent.getSelectedItemPosition();

        if (temp.equals("") || numPosition == 0) {
            Toast.makeText(getApplicationContext(),
                    "Selecione um animal para continuar!", Toast.LENGTH_LONG).show();
        } else {
            btnData.setEnabled(true);
        }

        switch (numPosition) {
            case 1:
                nomePet = String.valueOf(resultSQL.get(1));
                portePet = String.valueOf(resultSQL.get(2));
                racaPet = String.valueOf(resultSQL.get(3));
                tipoPet = String.valueOf(resultSQL.get(5));
                caracPet = String.valueOf(resultSQL.get(6));

                break;
            case 2:
                nomePet = String.valueOf(resultSQL.get(8));
                portePet = String.valueOf(resultSQL.get(9));
                racaPet = String.valueOf(resultSQL.get(10));
                tipoPet = String.valueOf(resultSQL.get(12));
                caracPet = String.valueOf(resultSQL.get(13));
                break;
            case 3:
                nomePet = String.valueOf(resultSQL.get(15));
                portePet = String.valueOf(resultSQL.get(16));
                racaPet = String.valueOf(resultSQL.get(17));
                tipoPet = String.valueOf(resultSQL.get(19));
                caracPet = String.valueOf(resultSQL.get(20));
                break;
            case 4:
                nomePet = String.valueOf(resultSQL.get(22));
                portePet = String.valueOf(resultSQL.get(23));
                racaPet = String.valueOf(resultSQL.get(24));
                tipoPet = String.valueOf(resultSQL.get(26));
                caracPet = String.valueOf(resultSQL.get(27));
                break;
            case 5:
                nomePet = String.valueOf(resultSQL.get(29));
                portePet = String.valueOf(resultSQL.get(30));
                racaPet = String.valueOf(resultSQL.get(31));
                tipoPet = String.valueOf(resultSQL.get(33));
                caracPet = String.valueOf(resultSQL.get(34));
                break;
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
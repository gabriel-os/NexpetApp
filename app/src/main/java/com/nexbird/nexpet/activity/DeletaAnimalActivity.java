package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 03/11/2016.
 */

public class DeletaAnimalActivity extends Activity {
    private static final String TAG = AlteraSenhaActivity.class.getSimpleName();
    private Spinner nomeAnimal;
    private Button btnDeletar;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private ArrayList<String> animal;

    protected void onCreate(Bundle savedInstanceState) {

        ArrayList resultSQL;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletanimal);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        nomeAnimal = (Spinner) findViewById(R.id.spinner);

        btnDeletar = (Button) findViewById(R.id.btnDeletar);

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

        nomeAnimal.setAdapter(adaptador);

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String animalSelec = nomeAnimal.getSelectedItem().toString();

                if (animalSelec.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Selecione o animal antes de deletar", Toast.LENGTH_LONG).show();
                } else {

                    for (int i = 0; i < animal.size(); i++) {
                        String temp = animal.get(i);

                        if (animalSelec.equals(temp)) {
                            prepareDeletaAnimalData(animalSelec);
                            break;
                        }

                    }

                }
            }
        });

    }

    private void prepareDeletaAnimalData(final String nomeAnimal) {
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
                params.put("nomePetshop", "");
                params.put("dataMarcada", "");
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

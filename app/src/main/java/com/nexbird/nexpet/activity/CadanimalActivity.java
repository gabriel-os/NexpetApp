package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
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

/**
 * Created by Gabriel on 13/06/2016.
 */


public class CadanimalActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = CadanimalActivity.class.getSimpleName();
    private SQLiteHandler db;
    private List<Animal> listaAnimal = new ArrayList<>();
    private AdaptadorAnimal mAdapter;
    private SessionManager session;
    private RecyclerView recyclerView;
    private Spinner spQuantidade;
    private ProgressDialog pDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadanimal);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        db = new SQLiteHandler(getApplicationContext());

    }

    private void prepareCompleta(final String query, final String nome, final String porte, final String raca, final String caracteristica) {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Cadastrando pet ...");
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
                        // db.(name, email, uid, created_at, endereco, telefone);

                        Toast.makeText(getApplicationContext(), "Cadastro de pet concluído com sucesso!!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                CadanimalActivity.this,
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


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       /* int temp = Integer.parseInt(parent.getSelectedItem().toString());
        mAdapter.notifyItemRangeRemoved(0, 5);
        Log.e("Teste Spinner:", String.valueOf(temp)); //Teste de variavél

        Animal ag = new Animal("", "", "", "", "");
        listaAnimal.clear();
        for (int i = 1; i <= temp; i++) {
            Log.e("For Spinner:*******", String.valueOf(i));
            listaAnimal.add(ag);
            mAdapter.notifyDataSetChanged();

        }*/
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}

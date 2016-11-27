package com.nexbird.nexpet.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nexbird.nexpet.R;
import com.nexbird.nexpet.adapter.AdaptadorPerfilPet;
import com.nexbird.nexpet.adapter.PerfilPet;
import com.nexbird.nexpet.app.AppConfig;
import com.nexbird.nexpet.app.AppController;
import com.nexbird.nexpet.helper.SQLiteHandler;
import com.nexbird.nexpet.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gabriel on 05/08/2016.
 */

public class PerfilpetActivity extends AppCompatActivity {

    private static final String TAG = AgendarActivity.class.getSimpleName();
    private static HashMap rs;
    private TextView lblTelefone, lblNome, lblDescricao, lblEndereco, lblHoraFunc;
    private SQLiteHandler db;
    private ArrayList<PerfilPet> listaServico = new ArrayList<>();
    private AdaptadorPerfilPet mAdapter;
    private RecyclerView recyclerView;
    private SessionManager session;
    private ProgressDialog pDialog;
    private String hora, nomePetshop, enderecoPetshop, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilpet);

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        lblTelefone = (TextView) findViewById(R.id.lblTelefone);
        lblNome = (TextView) findViewById(R.id.lblNome);
        lblDescricao = (TextView) findViewById(R.id.lblDescricao);
        lblEndereco = (TextView) findViewById(R.id.lblEndereco);
        lblHoraFunc = (TextView) findViewById(R.id.lblHoraFunc);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view3);

        mAdapter = new AdaptadorPerfilPet(listaServico);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerAndSeparator(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        Bundle params = getIntent().getExtras();

        hora = params.getString("hora");
        uid = params.getString("id");
        nomePetshop = params.getString("nome");
        enderecoPetshop = params.getString("endereco");

        lblNome.setText(nomePetshop);
        lblTelefone.setText(params.getString("telefone"));
        lblDescricao.setText(params.getString("descricao"));
        lblEndereco.setText(enderecoPetshop);
        lblHoraFunc.setText(hora);

        recyclerView.addOnItemTouchListener(new PerfilpetActivity.RecyclerTouchListener(getApplicationContext(), recyclerView, new AgendarActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PerfilPet ag = listaServico.get(position);

                String id = ag.getId();
                String nome = ag.getTxtServico();
                String precoP = ag.getTxtPequeno();
                String precoM = ag.getTxtMedio();
                String precoG = ag.getTxtGrande();
                String precoGG = ag.getTxtGigante();
                String precoGato = ag.getTxtGato();
                String duracaoCao = ag.getDuracaoCao();
                String duracaoGato = ag.getDuracaoGato();
                String descricao = ag.getDescricao();


                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("nome", nome);
                params.putString("precoP", precoP);
                params.putString("precoM", precoM);
                params.putString("precoG", precoG);
                params.putString("precoGG", precoGG);
                params.putString("precoGato", precoGato);
                params.putInt("duracaoCao", Integer.parseInt(duracaoCao));
                params.putInt("duracaoGato", Integer.parseInt(duracaoGato));
                params.putString("idPetshop", uid);
                params.putString("hora", hora);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);

                i.putExtras(params);

                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                PerfilPet ag = listaServico.get(position);
                Toast.makeText(getApplicationContext(),
                        ag.getDescricao(), Toast.LENGTH_LONG).show();
            }
        }));

        prepareRecuperaServicos(uid);
    }

    private void prepareRecuperaServicos(final String uid) {

        rs = new HashMap<Integer, String>();

        String tag_string_req = "req_agendar";
        final HashMap<Integer, String> info = new HashMap<Integer, String>();

        pDialog.setMessage("Aguarde ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RECUPERA_SERVICO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Resposta do agendar: " + response.toString());
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
                            temp += tempRow.getString("id") + ",,,";
                            temp += tempRow.getString("nome") + ",,,";
                            temp += tempRow.getString("precoP") + ",,,";
                            temp += tempRow.getString("precoM") + ",,,";
                            temp += tempRow.getString("precoG") + ",,,";
                            temp += tempRow.getString("precoGG") + ",,,";
                            temp += tempRow.getString("precoGato") + ",,,";
                            temp += tempRow.getString("duracaoCao") + ",,,";
                            temp += tempRow.getString("duracaoGato") + ",,,";
                            temp += tempRow.getString("descricao");

                            info.put(i, temp);
                            Log.e("Linhas: ", String.valueOf(info.get(i)));
                            PerfilpetActivity.rs = info;
                        }
                        Log.e("Array: ", String.valueOf(info));
                        Log.e("Número de info: ", String.valueOf(info.size()));

                        for (int i = 0; i < info.size(); i++) {
                            Log.e("For: ", String.valueOf(i));

                            String[] temp = info.get((i)).split(",,,");
                            Log.e("Teste Array: ", String.valueOf(temp.length));
                            PerfilPet ag = new PerfilPet(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9]);

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
                Log.e(TAG, "Erro ao recuperar: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", uid);

                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.e("Teste: ", String.valueOf(info));


       /**/
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

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
        finish();
        startActivity(i);

    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private AgendarActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final AgendarActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}

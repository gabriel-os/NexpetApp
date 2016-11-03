package com.nexbird.nexpet.activity;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.nexbird.nexpet.R;
import com.nexbird.nexpet.helper.SQLiteHandler;
import com.nexbird.nexpet.helper.SessionManager;


public class PrincipalActivity extends ActivityGroup {

    private static final String TAG = PrincipalActivity.class.getSimpleName();
    private SQLiteHandler db;
    private ImageButton btnToolbar;
    private SessionManager session;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnToolbar = (ImageButton) findViewById(R.id.imgBtnPerfil);
        final EditText txtPesquisa = (EditText) findViewById(R.id.txtPesquisa);

        session = new SessionManager(getApplicationContext());

        db = new SQLiteHandler(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        if (!session.isRegistredIn()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cadastro pessoal incompleto!");
            builder.setMessage("Deseja completar seu cadastro agora?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent i = new Intent(getApplication(), CadastroCompletoActivity.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            AlertDialog alerta = builder.create();
            alerta.show();
        }

        final TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup(this.getLocalActivityManager());

        TabHost.TabSpec ts1 = host.newTabSpec("tela1");
        ts1.setIndicator("", getResources().getDrawable(R.drawable.ic_agendar));
        ts1.setContent(new Intent(this, AgendarActivity.class));
        host.addTab(ts1);

        TabHost.TabSpec ts2 = host.newTabSpec("tela2");
        ts2.setIndicator("", getResources().getDrawable(R.drawable.ic_agendado));
        ts2.setContent(new Intent(this, AgendadoActivity.class));
        host.addTab(ts2);

        TabHost.TabSpec ts3 = host.newTabSpec("tela3");
        ts3.setIndicator("", getResources().getDrawable(R.drawable.ic_config));
        ts3.setContent(new Intent(this, ConfigActivity.class));
        host.addTab(ts3);

        btnToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addPet("123456", "Kim", "Pequeno", "Macho","Maltês", "Cachorro", "Nenhuma");
                db.addPet("12345", "Thor", "Grande", "Femea","Cocker", "Cachorro", "Agressivo");
            }
        });

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(host.getCurrentTab() != 0 ){
                    txtPesquisa.setVisibility(View.GONE);
                }else {
                    txtPesquisa.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair?");
        builder.setMessage("Deseja realmente sair?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                session.setLogin(false);

                db.deleteUsers();
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }


}
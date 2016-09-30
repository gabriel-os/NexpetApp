/*package com.nexbird.nexpet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nexbird.nexpet.R;

public class ServicosActivity extends AppCompatActivity {

    private ImageButton btnBanho, btnHidrata, btnPulgas, btnTosaCompleta, btnTosaHig, btnTosaRaca;
    //Labels
    private TextView lblBanho, lblHidrata, lblPulgas, lblTosaCompleta, lblTosaHig, lblTosaRaca;
    //Strings
    private String nomePetshop, idPetshop, enderecoPetshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        btnBanho = (ImageButton) findViewById(R.id.btnBanho);
        btnHidrata = (ImageButton) findViewById(R.id.btnHidrata);
        btnPulgas = (ImageButton) findViewById(R.id.btnPulgas);
        btnTosaCompleta = (ImageButton) findViewById(R.id.btnTosaCompleta);
        btnTosaHig = (ImageButton) findViewById(R.id.btnTosaHig);
        btnTosaRaca = (ImageButton) findViewById(R.id.btnTosaRaca);
        //Labels
        lblBanho = (TextView) findViewById(R.id.lblBanho);
        lblHidrata = (TextView) findViewById(R.id.lblHidrata);
        lblPulgas = (TextView) findViewById(R.id.lblPulgas);
        lblTosaCompleta = (TextView) findViewById(R.id.lblTosaCompleta);
        lblTosaHig = (TextView) findViewById(R.id.lblTosaHig);
        lblTosaRaca = (TextView) findViewById(R.id.lblTosaRaca);

        Bundle params = getIntent().getExtras();

        nomePetshop = params.getString("nome");
        idPetshop = params.getString("id");
        enderecoPetshop = params.getString("endereco");

        //Clicks----------------------------------------------------------------------

        btnBanho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("id", idPetshop);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", "Banho");
                i.putExtras(params);

                startActivity(i);
            }
        });

        btnHidrata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("id", idPetshop);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", "Banho com hidratação");
                i.putExtras(params);

                startActivity(i);
            }
        });
        btnPulgas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("id", idPetshop);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", "Banho anti-pulgas");
                i.putExtras(params);

                startActivity(i);
            }
        });
        btnTosaCompleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("id", idPetshop);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", "Tosa completa");
                i.putExtras(params);

                startActivity(i);
            }
        });
        btnTosaHig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("id", idPetshop);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", "Tosa Higiênica");
                i.putExtras(params);

                startActivity(i);
            }
        });
        btnTosaRaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SelecionarhoraActivity.class);

                Bundle params = new Bundle();

                params.putString("id", idPetshop);
                params.putString("nomePetshop", nomePetshop);
                params.putString("endereco", enderecoPetshop);
                params.putString("servico", "Tosa da Raça");
                i.putExtras(params);

                startActivity(i);
            }
        });
        //*------------------------------------------------------------------------------------------------*
    }
}*/
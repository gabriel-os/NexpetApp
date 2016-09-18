package com.nexbird.nexpet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 05/08/2016.
 */

public class PerfilpetActivity extends AppCompatActivity {
    private TextView lblTelefone, lblNome, lblDescricao, lblEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilpet);

        lblTelefone = (TextView) findViewById(R.id.lblTelefone);
        lblNome = (TextView) findViewById(R.id.lblNome);
        lblDescricao = (TextView) findViewById(R.id.lblDescricao);
        lblEndereco = (TextView) findViewById(R.id.lblEndereco);


        Bundle params = getIntent().getExtras();

        lblNome.setText(params.getString("nome"));
        lblTelefone.setText(params.getString("telefone"));
        lblDescricao.setText(params.getString("descricao"));
        lblEndereco.setText(params.getString("endereco"));
    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(i);
    }
}

package com.nexbird.nexpet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 11/08/2016.
 */
public class GeralActivity extends AppCompatActivity {
    private Button btnDados, btnSenha, btnPet, btnDel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geral);

        btnDados = (Button) findViewById(R.id.btnDados);
        btnSenha = (Button) findViewById(R.id.btnSenha);
        btnPet = (Button) findViewById(R.id.btnPet);
        btnDel = (Button) findViewById(R.id.btnDel);

        btnDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InformacaoActivity.class);

                Bundle params = new Bundle();
                params.putBoolean("alteracao", true);

                i.putExtras(params);

                startActivity(i);
            }
        });
        btnSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), AlteraSenhaActivity.class);

                startActivity(i);
            }
        });
        btnPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdAnimalActivity.class);

                startActivity(i);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DeletaAnimalActivity.class);

                startActivity(i);
            }
        });
    }
}

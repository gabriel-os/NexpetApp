package com.nexbird.nexpet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 11/08/2016.
 */
public class GeralActivity extends AppCompatActivity {
    private Button btnDados, btnSenha, btnPet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geral);

        btnDados = (Button) findViewById(R.id.btnDados);
        btnSenha = (Button) findViewById(R.id.btnSenha);
        btnPet = (Button) findViewById(R.id.btnPet);
    }
}

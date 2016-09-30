package com.nexbird.nexpet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 30/09/2016.
 */

public class EnviarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnEmail, btnPDF, btnGaleria, btnVoltar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        btnEmail = (Button) findViewById(R.id.btnEmail);
        btnPDF = (Button) findViewById(R.id.btnPDF);
        btnGaleria = (Button) findViewById(R.id.btnGaleria);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnEmail.setOnClickListener(this);
        btnPDF.setOnClickListener(this);
        btnGaleria.setOnClickListener(this);
        btnVoltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEmail:

                break;
            case R.id.btnPDF:

                break;
            case R.id.btnGaleria:

                break;
            case R.id.btnVoltar:

                break;
        }
    }
}

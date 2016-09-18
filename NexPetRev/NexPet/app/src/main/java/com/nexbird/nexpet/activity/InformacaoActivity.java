package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nexbird.nexpet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 07/09/2016.
 */
public class InformacaoActivity extends Activity {
    private List<String> logradouros;
    private Spinner sp_Logradouro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao);

        sp_Logradouro = (Spinner) findViewById(R.id.sp_Logradouro);

        logradouros = new ArrayList<String>();
        logradouros.add("Rua");
        logradouros.add("Avenida");
        logradouros.add("Beco");
        logradouros.add("Viela");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, logradouros);

        // Drop down layout style - list view with radio button
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_Logradouro.setAdapter(adaptador);
    }


}

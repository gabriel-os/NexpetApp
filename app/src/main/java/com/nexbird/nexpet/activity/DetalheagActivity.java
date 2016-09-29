package com.nexbird.nexpet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 28/09/2016.
 */

public class DetalheagActivity extends AppCompatActivity {
    private String unique_index;
    private String dataAgendada;
    private String nomePetshop;
    private String endereco;
    private String nomeAnimal;
    private String servico;
    private String precoFinal;
    private String confirmado;
    private TextView txtUnique, txtData, txtEndereco, txtNomePetshop, txtNomeAnimal, txtServico, txtPreco;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basico);

        txtUnique = (TextView) findViewById(R.id.txtUnique);
        txtData = (TextView) findViewById(R.id.txtData);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        txtNomePetshop = (TextView) findViewById(R.id.txtPetshop);
        txtNomeAnimal = (TextView) findViewById(R.id.txtAnimal);
        txtServico = (TextView) findViewById(R.id.txtServico);
        txtPreco = (TextView) findViewById(R.id.txtPreco);

        Bundle params = new Bundle();

        unique_index = params.getString("unique_index");
        dataAgendada = params.getString("dataAgendada");
        nomePetshop = params.getString("nomePetshop");
        endereco = params.getString("endereco");
        nomeAnimal = params.getString("nomeAnimal");
        servico = params.getString("servico");
        precoFinal = params.getString("precoFinal");
        confirmado = params.getString("confirmado");

        txtUnique.setText(unique_index);
        txtData.setText(dataAgendada);
        txtNomePetshop.setText(nomePetshop);
        txtNomeAnimal.setText(nomeAnimal);
        txtServico.setText(servico);
        txtPreco.setText(precoFinal);

    }
}

package com.nexbird.nexpet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.nexbird.nexpet.R;
import com.nexbird.nexpet.helper.SQLiteHandler;

import java.util.HashMap;

/**
 * Created by Gabriel on 07/11/2016.
 */

public class PerfilActivity extends Activity {

    private TextView txtNome, txtEmail, txtSexo, txtTelefone, txtCelular, txtEndereco;
    private SQLiteHandler db;
    private HashMap<String, String> resultSQL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        String nome, email, telefone, celular, enderenco;

        db = new SQLiteHandler(getApplicationContext());

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        txtCelular = (TextView) findViewById(R.id.txtCelular);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);

        resultSQL = db.getUserDetails();

        nome = resultSQL.get("name");
        email = resultSQL.get("email");
        telefone = resultSQL.get("telefoneUm");
        celular = resultSQL.get("telefoneDois");
        enderenco = resultSQL.get("endereco") + ", " + resultSQL.get("numero");

        if (nome.isEmpty()) {
            txtNome.setText("Não informado");
        } else if (email.isEmpty()) {
            txtEmail.setText("Não informado");
        } else if (telefone.isEmpty()) {
            txtTelefone.setText("Não informado");
        } else if (celular.isEmpty()) {
            txtCelular.setText("Não informado");
        } else if (enderenco.isEmpty()) {
            txtEndereco.setText("Não informado");
        }

        txtNome.setText(nome);
        txtEmail.setText(email);
        txtTelefone.setText(telefone);
        txtCelular.setText(celular);
        txtEndereco.setText(enderenco);
    }
}

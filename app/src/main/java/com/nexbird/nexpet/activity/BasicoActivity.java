package com.nexbird.nexpet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 07/09/2016.
 */
public class BasicoActivity extends FragmentActivity {
    public EditText txtNome, txtEmail, txtSenha, txtConftimarSenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basico);

        txtNome = (EditText) findViewById(R.id.txtNomeUsuario);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConftimarSenha = (EditText) findViewById(R.id.txtConfirmaSenha);
    }

    public String getValue() {
        String temp = "";
        temp += String.valueOf(txtNome.getText()).trim() + ",,,";
        temp += String.valueOf(txtEmail.getText()).trim() + ",,,";
        temp += String.valueOf(txtSenha.getText()).trim() + ",,,";
        temp += String.valueOf(txtConftimarSenha.getText()).trim();


        return temp;
    }
}

package com.nexbird.nexpet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 07/09/2016.
 */
public class BasicoActivity extends FragmentActivity {
    private EditText txtNome, txtEmail, txtSenha, txtConftimarSenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basico);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConftimarSenha = (EditText) findViewById(R.id.txtConfirmaSenha);
    }
}

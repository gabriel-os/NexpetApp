package com.nexbird.nexpet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import com.nexbird.nexpet.R;

/**
 * Created by Gabriel on 11/08/2016.
 */
public class NotificacaoActivity extends AppCompatActivity {

    private Switch sw_notif, sw_breve;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        sw_notif = (Switch) findViewById(R.id.sw_notif);
        sw_breve = (Switch) findViewById(R.id.sw_breve);
    }
}

package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexbird.nexpet.R;
import com.nexbird.nexpet.activity.PerfilpetActivity;

import java.util.List;

public class AdaptadorAgendar extends RecyclerView.Adapter<AdaptadorAgendar.MyViewHolder> implements View.OnClickListener {

    private List<Agendar> agendar;
    private Button btnPerfilPet;

    public AdaptadorAgendar(List<Agendar> agendar) {
        this.agendar = agendar;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_agendar, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Agendar ag = agendar.get(position);
        holder.lblNomePetshop.setText(ag.getNomePetshop());
        holder.imLogoPetshop.setImageResource(R.mipmap.teste);
    }

    @Override
    public int getItemCount() {
        return agendar.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView idPetshop, lblNomePetshop;
        public ImageView imLogoPetshop;

        public MyViewHolder(View view) {
            super(view);

            lblNomePetshop = (TextView) view.findViewById(R.id.lblNomePetshop);
            imLogoPetshop = (ImageView) view.findViewById(R.id.imLogoPetshop);
            btnPerfilPet = (Button) view.findViewById(R.id.btnPerfilPet);


            btnPerfilPet.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String nomePetshop = (String) lblNomePetshop.getText();
                    int cont = 0;
                    String descricao = "";
                    String telefone = "";
                    String endereco = "";

                    for (int i = 0; i < agendar.size(); i++) {
                        String comp = agendar.get(i).getNomePetshop();
                        if (comp.equals(nomePetshop)) {
                            descricao = agendar.get(cont).getDescricaoPetshop();
                            telefone = agendar.get(cont).getTelefonePetshop();
                            endereco = agendar.get(cont).getEnderecoPetshop();
                            Log.e("Teste de variavél: ", String.valueOf(agendar.get(0)));
                            break;
                        } else {
                            cont++;
                        }
                    }
                    Intent i = new Intent(v.getContext(), PerfilpetActivity.class);

                    Bundle params = new Bundle();
                    params.putString("nome", nomePetshop);
                    params.putString("descricao", descricao);
                    params.putString("telefone", telefone);
                    params.putString("endereco", endereco);
                    Log.e("Teste: ", nomePetshop + " " + descricao + " " + telefone + " " + endereco);
                    i.putExtras(params);
                    v.getContext().startActivity(i);
                }
            });
        }

    }
}

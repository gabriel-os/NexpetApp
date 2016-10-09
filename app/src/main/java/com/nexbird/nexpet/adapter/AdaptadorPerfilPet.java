package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexbird.nexpet.R;

import java.util.List;

public class AdaptadorPerfilPet extends RecyclerView.Adapter<AdaptadorPerfilPet.MyViewHolder> implements View.OnClickListener {

    private List<PerfilPet> perfilpet;

    public AdaptadorPerfilPet(List<PerfilPet> perfilpet) {
        this.perfilpet = perfilpet;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_perfilpet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PerfilPet ag = perfilpet.get(position);

        holder.txtServico.setText(ag.getTxtServico());

        holder.txtPequeno.setText("R$" + ag.getTxtPequeno());
        holder.txtMedio.setText("R$" + ag.getTxtMedio());
        holder.txtGrande.setText("R$" + ag.getTxtGrande());
        holder.txtGigante.setText("R$" + ag.getTxtGigante());
        holder.txtGato.setText("R$" + ag.getTxtGato());
    }

    @Override
    public int getItemCount() {
        return perfilpet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView txtServico, txtPequeno, txtMedio, txtGrande, txtGigante, txtGato;

        public MyViewHolder(View view) {
            super(view);

            txtServico = (TextView) view.findViewById(R.id.txtServico);
            txtPequeno = (TextView) view.findViewById(R.id.txtPequeno);
            txtMedio = (TextView) view.findViewById(R.id.txtMedio);
            txtGrande = (TextView) view.findViewById(R.id.txtGrande);
            txtGigante = (TextView) view.findViewById(R.id.txtGigante);
            txtGato = (TextView) view.findViewById(R.id.txtGato);
/*
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
                    v.getContext().startActivity(i);*/

        }

    }
}

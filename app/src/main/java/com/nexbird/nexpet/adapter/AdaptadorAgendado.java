package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nexbird.nexpet.R;

import java.util.List;

public class AdaptadorAgendado extends RecyclerView.Adapter<AdaptadorAgendado.MyViewHolder> {

    private List<Agendados> agendados;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView nomePetshop, nomePet, dataMarcada;

        public MyViewHolder(View view) {
            super(view);
            nomePetshop = (TextView) view.findViewById(R.id.lblNomePetshop);
            nomePet = (TextView) view.findViewById(R.id.nomePet);
            dataMarcada = (TextView) view.findViewById(R.id.dataMarcada);
        }
    }


    public AdaptadorAgendado(List<Agendados> agendados) {
        this.agendados = agendados;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_agendado, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Agendados ag = agendados.get(position);
        Log.e("Teste do adaptador: ", ag.getIdAgendado());
        holder.nomePetshop.setText(ag.getNomePetshop());
        holder.nomePet.setText(ag.getNomePet());
        holder.dataMarcada.setText(ag.getDataMarcada());
    }

    @Override
    public int getItemCount() {
        return agendados.size();
    }
}

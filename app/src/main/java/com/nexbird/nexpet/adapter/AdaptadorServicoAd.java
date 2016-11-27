package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 12/10/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nexbird.nexpet.R;

import java.util.List;

public class AdaptadorServicoAd extends RecyclerView.Adapter<AdaptadorServicoAd.ViewHolder> {

    private List<ServicoAdicional> servico;

    public AdaptadorServicoAd(List<ServicoAdicional> servico) {
        this.servico = servico;
    }

    @Override
    public AdaptadorServicoAd.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.linha_servicoad, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.nomeServico.setText(servico.get(position).getNomeServico());
        viewHolder.txtValor.setText("R$" + servico.get(position).getPreco());
        viewHolder.txtValor.setGravity(Gravity.LEFT);

        viewHolder.chkSelected.setTag(servico.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ServicoAdicional contact = (ServicoAdicional) cb.getTag();

                contact.setSelected(cb.isChecked());
                servico.get(pos).setSelected(cb.isChecked());


            }
        });

    }

    @Override
    public int getItemCount() {
        return servico.size();
    }

    public List<ServicoAdicional> getServicoSelecionado() {
        return servico;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nomeServico, txtValor;

        public CheckBox chkSelected;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            nomeServico = (TextView) itemLayoutView.findViewById(R.id.nomeServico);
            txtValor = (TextView) itemLayoutView.findViewById(R.id.txtValor);

            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.cb_servico);

        }

    }

}
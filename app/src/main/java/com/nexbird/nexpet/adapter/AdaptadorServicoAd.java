package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 12/10/2016.
 */

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.nexbird.nexpet.R;

public class AdaptadorServicoAd extends RecyclerView.Adapter<AdaptadorServicoAd.ViewHolder> {

    private List<ServicoAdicional> servico;

    public AdaptadorServicoAd(List<ServicoAdicional> servico) {
        this.servico = servico;

    }

    // Create new views
    @Override
    public AdaptadorServicoAd.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.linha_servicoad, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.nomeServico.setText(servico.get(position).getNomeServico());

        viewHolder.chkSelected.setTag(servico.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ServicoAdicional contact = (ServicoAdicional) cb.getTag();

                contact.setSelected(cb.isChecked());
                servico.get(pos).setSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return servico.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nomeServico;

        public CheckBox chkSelected;

        public ServicoAdicional singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            nomeServico = (TextView) itemLayoutView.findViewById(R.id.txtServico);

            chkSelected = (CheckBox) itemLayoutView
                    .findViewById(R.id.cb_servico);

        }

    }

    // method to access in activity after updating selection
    public List<ServicoAdicional> getServicoSelecionado() {
        return servico;
    }

}
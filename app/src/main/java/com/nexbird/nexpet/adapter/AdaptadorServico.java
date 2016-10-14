package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 12/10/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.nexbird.nexpet.R;

import java.util.List;

public class AdaptadorServico extends RecyclerView.Adapter<AdaptadorServico.MyViewHolder> implements View.OnClickListener {

    private List<ServicoAdicional> servico;

    public AdaptadorServico(List<ServicoAdicional> servico) {
        this.servico = servico;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_servicoad, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ServicoAdicional ag = servico.get(position);
        holder.cb_servico.setText(ag.getNomeServico());
    }

    @Override
    public int getItemCount() {
        return servico.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CheckBox cb_servico;

        public MyViewHolder(View view) {
            super(view);

            cb_servico = (CheckBox) view.findViewById(R.id.cb_servico);
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

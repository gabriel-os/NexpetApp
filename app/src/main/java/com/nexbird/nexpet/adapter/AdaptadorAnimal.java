package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nexbird.nexpet.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorAnimal extends RecyclerView.Adapter<AdaptadorAnimal.MyViewHolder> {

    private List<Animal> animal;

    public AdaptadorAnimal(List<Animal> animal) {
        this.animal = animal;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linha_animal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return animal.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView nomeAnimal, txtCaracteristica;
        public RadioGroup sexoAnimal;
        public Spinner racaAnimal, porteAnimal;

        public MyViewHolder(View view) {
            super(view);
            nomeAnimal = (TextView) view.findViewById(R.id.txtAnimal);
            sexoAnimal = (RadioGroup) view.findViewById(R.id.rbGruop);
            racaAnimal = (Spinner) view.findViewById(R.id.sp_raca);
            porteAnimal = (Spinner) view.findViewById(R.id.sp_porte);
            txtCaracteristica = (TextView) view.findViewById(R.id.txtCaracteristica);

            List<String> raca = new ArrayList<String>();
            raca.add("Maltês");
            raca.add("Galgo Afegão");
            raca.add("Chow Chow");

            List<String> porte = new ArrayList<String>();
            porte.add("Pequeno");
            porte.add("Médio");
            porte.add("Grande");
            porte.add("Gigante");

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, raca);
            ArrayAdapter<String> adaptadorPorte = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, porte);

            // Drop down layout style - list view with radio button
            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adaptadorPorte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            racaAnimal.setAdapter(adaptador);
            porteAnimal.setAdapter(adaptadorPorte);


        }
    }
}

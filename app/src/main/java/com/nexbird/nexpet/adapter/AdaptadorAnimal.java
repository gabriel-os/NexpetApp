package com.nexbird.nexpet.adapter;

/**
 * Created by Gabriel on 04/07/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.nexbird.nexpet.R;

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


        public TextView nomeAnimal;
        public RadioGroup sexoAnimal;
        public Spinner racaAnimal, porteAnimal;

        public MyViewHolder(View view) {
            super(view);
            nomeAnimal = (TextView) view.findViewById(R.id.txtAnimal);
            sexoAnimal = (RadioGroup) view.findViewById(R.id.rbGruop);
            racaAnimal = (Spinner) view.findViewById(R.id.sp_raca);
            porteAnimal = (Spinner) view.findViewById(R.id.sp_porte);
        }
    }
}

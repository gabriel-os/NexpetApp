package com.nexbird.nexpet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexbird.nexpet.R;

import java.util.ArrayList;

/**
 * Created by Gabriel on 27/08/2016.
 */


public class AdaptadorHora extends BaseAdapter {
    private Context context;
    private final ArrayList<String> valorHoras;

    public AdaptadorHora(Context context, ArrayList<String> valorHoras) {
        this.context = context;
        this.valorHoras = valorHoras;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);


            gridView = inflater.inflate(R.layout.linha_hora, null);


            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(valorHoras.get(position));


            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String mobile = valorHoras.get(position);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return valorHoras.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
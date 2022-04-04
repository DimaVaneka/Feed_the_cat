package com.example.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList result_id, result_score, result_date;

    CustomAdapter(Context context, ArrayList result_id, ArrayList result_score,
                  ArrayList result_date) {
        this.context = context;
        this.result_id = result_id;
        this.result_score = result_score;
        this.result_date = result_date;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.result_id_txt.setText(String.valueOf(result_id.get(position)));
        holder.result_score_txt.setText("Satiety: " + String.valueOf(result_score.get(position)));
        holder.result_date_txt.setText(String.valueOf(result_date.get(position)));

    }

    @Override
    public int getItemCount() {
        return result_id.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView result_id_txt, result_score_txt, result_date_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            result_id_txt = itemView.findViewById(R.id.result_id_txt);
            result_score_txt = itemView.findViewById(R.id.result_score_txt);
            result_date_txt = itemView.findViewById(R.id.result_date_txt);
        }
    }
}

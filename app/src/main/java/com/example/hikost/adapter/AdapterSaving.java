package com.example.hikost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikost.R;
import com.example.hikost.obj.ObjectSaving;

import java.util.ArrayList;

public class AdapterSaving extends RecyclerView.Adapter<AdapterSaving.ViewHolder>{
    Context context;
    ArrayList<ObjectSaving> savingList;

    public ArrayList<ObjectSaving> getSavingList() {
        return savingList;
    }

    public void setSavingList(ArrayList<ObjectSaving> savingList) {
        this.savingList = savingList;
    }

    public AdapterSaving(Context context, ArrayList<ObjectSaving> savingList){
        this.context = context;
        this.savingList = savingList;
    }

    @NonNull
    @Override
    public AdapterSaving.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_component_card_all_budgets,parent,false);
        return new AdapterSaving.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSaving.ViewHolder holder, int position) {
        holder.txtTitle.setText(savingList.get(position).getTitle());
        holder.txtValue.setText(savingList.get(position).getValue().toString());
    }

    @Override
    public int getItemCount() {
        return savingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription, txtValue;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.componentbudget_title);
            txtValue = itemView.findViewById(R.id.componentbudget_value);
        }
    }
}
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterSaving extends RecyclerView.Adapter<AdapterSaving.ViewHolder>{
    Context context;
    ArrayList<ObjectSaving> savingList;
    private AdapterSaving.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View v);
    }

    public void setOnItemClickListener(AdapterSaving.OnItemClickListener listener){
        mListener = listener;
    }
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

        long value = savingList.get(position).getValue();
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String valueWithCurrency = currencyFormatter.format(value);
        holder.txtValue.setText(valueWithCurrency);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    ArrayList<ObjectSaving> Savinglist = getSavingList();
//                    String key, title, category, description, savingType, value, target;
//
//                    key = Savinglist.get(position).getKey();
//                    title = Savinglist.get(position).getUsername();
//                    category = Savinglist.get(position).getJudul();
//                    description = Savinglist.get(position).getKategori();
//                    savingType = Savinglist.get(position).getPertanyaan();
//                    value = Savinglist.get(position).getDate();
//                    target = String.valueOf(Savinglist.get(position).getStar());
//
//                    if(mListener!=null){
//                        mListener.onItemClick(v,key,username,judul,kategori,pertanyaan,date,star,path);
//                    }
                    mListener.onItemClick(v);
                }
            });
        }
    }
}

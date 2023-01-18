package com.example.hikost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikost.R;
import com.example.hikost.obj.ObjectBudget;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterBudget extends RecyclerView.Adapter<AdapterBudget.ViewHolder>{
    Context context;
    ArrayList<ObjectBudget> budgetList;

    private AdapterBudget.OnItemClickListener mListener;

    public ArrayList<ObjectBudget> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(ArrayList<ObjectBudget> budgetList) {
        this.budgetList = budgetList;
    }

    public AdapterBudget(Context context, ArrayList<ObjectBudget> budgetList){
        this.context = context;
        this.budgetList = budgetList;
    }

    public interface OnItemClickListener{
        void onItemClick(View v);
    }

    public void setOnItemClickListener(AdapterBudget.OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public AdapterBudget.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_component_card_all_budgets,parent,false);
        return new AdapterBudget.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBudget.ViewHolder holder, int position) {
        holder.txtTitle.setText(budgetList.get(position).getTitle());

        long value = budgetList.get(position).getValue();
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String valueWithCurrency = currencyFormatter.format(value);
        holder.txtValue.setText(valueWithCurrency);
    }

    @Override
    public int getItemCount() {
        return budgetList.size();
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
//                    ArrayList<ObjectForum> Forumlist = getForumList();
//                    String key, username, judul, kategori, pertanyaan, date, star, path;
//
//                    key = Forumlist.get(position).getKey();
//                    username = Forumlist.get(position).getUsername();
//                    judul = Forumlist.get(position).getJudul();
//                    kategori = Forumlist.get(position).getKategori();
//                    pertanyaan = Forumlist.get(position).getPertanyaan();
//                    date = Forumlist.get(position).getDate();
//                    star = String.valueOf(Forumlist.get(position).getStar());
//                    path = Forumlist.get(position).getFilepath();
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

package com.example.hikost.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hikost.R;
import com.example.hikost.obj.ObjectTransaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterTransaction extends RecyclerView.Adapter<AdapterTransaction.ViewHolder> {
    Context context;
    ArrayList<ObjectTransaction> transactionArrayList;


    public AdapterTransaction(Context context, ArrayList<ObjectTransaction> transactionArrayList) {
        this.context = context;
        this.transactionArrayList = transactionArrayList;
    }

    public ArrayList<ObjectTransaction> getTransactionArrayList() {
        return transactionArrayList;
    }

    @NonNull
    @Override
    public AdapterTransaction.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_component_card_last_records,parent,false);
        return new AdapterTransaction.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTransaction.ViewHolder holder, int position) {
        holder.recordsTitle.setText(transactionArrayList.get(position).getTitle());
        holder.paymentTypeTitle.setText(transactionArrayList.get(position).getPaymentType());
        holder.dateTitle.setText(transactionArrayList.get(position).getDate());

        long value = transactionArrayList.get(position).getValue();

        if(transactionArrayList.get(position).getTransactionType().equals("Income")){
            holder.valueTitle.setTextColor(Color.parseColor("#46B4CD"));
        } else holder.valueTitle.setTextColor(Color.parseColor("#FE6B00"));

        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String valueWithCurrency = currencyFormatter.format(value);
        holder.valueTitle.setText(valueWithCurrency);
    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView recordsTitle, paymentTypeTitle, valueTitle, dateTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recordsTitle = itemView.findViewById(R.id.records_title);
            paymentTypeTitle = itemView.findViewById(R.id.payment_type_title);
            valueTitle = itemView.findViewById(R.id.value_title);
            dateTitle = itemView.findViewById(R.id.date_title);

        }
    }


}

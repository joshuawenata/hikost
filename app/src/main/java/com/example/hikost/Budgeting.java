package com.example.hikost;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hikost.adapter.AdapterBudget;
import com.example.hikost.adapter.AdapterSaving;
import com.example.hikost.obj.ObjectBudget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Budgeting extends AppCompatActivity {
    public NestedScrollView scrollView;

    Context context = this;

    TextView totalValueLabel;

    private Long totalValue = 0L;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);

        initComponents();

        RecyclerView budgetsrecyclerView = findViewById(R.id.budgeting_budgetsrecyclerview);
        RecyclerView specialrecyclerView = findViewById(R.id.budgeting_specialrecyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceBudget = firebaseDatabase.getReference("budget");

        ArrayList<ObjectBudget> budgetsList = new ArrayList<>();
        ArrayList<ObjectBudget> specialList = new ArrayList<>();
        databaseReferenceBudget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot budget : snapshot.getChildren()) {
                    ObjectBudget currBudgetObj = budget.getValue(ObjectBudget.class);

                    //add budget object to arraylist
                    if(currBudgetObj.getBudgetType().equals("Budget")){
                        budgetsList.add(currBudgetObj);
                    }
                    else {
                        specialList.add(currBudgetObj); //special budget
                    }

                    //to add budget to total budget
                    totalValue += Integer.parseInt(String.valueOf(currBudgetObj.getValue()));
                }

                budgetsrecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                AdapterBudget allAdapter = new AdapterBudget(context, budgetsList);

                specialrecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                AdapterBudget specialAdapter = new AdapterBudget(context, specialList);

                allAdapter.setOnItemClickListener(new AdapterBudget.OnItemClickListener() {

                    @Override
                    public void onItemClick(View v) {
                        Intent i = new Intent(Budgeting.this, edit_budget.class);
                        i.putExtra("objectLabel","Budget");
                        startActivity(i);
                    }

//                    @Override
//                    public void onItemClick(View v, String key, String username, String judul, String kategori, String pertanyaan, String date, String star, String path) {
//                        Intent i = new Intent(HomePage.this, ForumCardPage.class);
//                        i.putExtra("key",key);
//                        i.putExtra("username",username);
//                        i.putExtra("judul",judul);
//                        i.putExtra("kategori",kategori);
//                        i.putExtra("pertanyaan",pertanyaan);
//                        i.putExtra("date",date);
//                        i.putExtra("star",star);
//                        i.putExtra("path",path);
//                        startActivity(i);
//                    }
                });

                specialAdapter.setOnItemClickListener(new AdapterBudget.OnItemClickListener() {

                    @Override
                    public void onItemClick(View v) {
                        Intent i = new Intent(Budgeting.this, edit_budget.class);
                        i.putExtra("objectLabel","Special Budget");
                        startActivity(i);
                    }

//                    @Override
//                    public void onItemClick(View v, String key, String username, String judul, String kategori, String pertanyaan, String date, String star, String path) {
//                        Intent i = new Intent(HomePage.this, ForumCardPage.class);
//                        i.putExtra("key",key);
//                        i.putExtra("username",username);
//                        i.putExtra("judul",judul);
//                        i.putExtra("kategori",kategori);
//                        i.putExtra("pertanyaan",pertanyaan);
//                        i.putExtra("date",date);
//                        i.putExtra("star",star);
//                        i.putExtra("path",path);
//                        startActivity(i);
//                    }
                });

                budgetsrecyclerView.setAdapter(allAdapter);
                specialrecyclerView.setAdapter(specialAdapter);

                allAdapter.notifyDataSetChanged();
                specialAdapter.notifyDataSetChanged();

                displayTotalValue(totalValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void initComponents() {
        scrollView = findViewById(R.id.scroll_view);
        totalValueLabel = findViewById(R.id.total_value_label);
    }

    public void displayTotalValue(Long totalValue) {
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String valueWithCurrency = currencyFormatter.format(totalValue);

        totalValueLabel.setText(valueWithCurrency);
    }

    public void intoDashboard(View view) {
        startActivity(new Intent(this, Dashboard.class));
        finish();
    }

    public void intoBudgeting(View view) {
        scrollView.smoothScrollTo(0,0);
    }    public void intoSavings(View view) {
        startActivity(new Intent(this, Savings.class));
        finish();
    }    public void intoAccount(View view) {
        startActivity(new Intent(this, Account.class));
        finish();
    }

    public void intoAddBudget(View view) {
        Intent i = new Intent(this, add_budget.class);
        i.putExtra("objectLabel","Budget");
        startActivity(i);
    }

    public void intoAddSpecial(View view) {
        Intent i = new Intent(this, add_budget.class);
        i.putExtra("objectLabel","Special Budget");
        startActivity(i);
    }
}
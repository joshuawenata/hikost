package com.example.hikost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hikost.adapter.AdapterBudget;
import com.example.hikost.obj.ObjectBudget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Budgeting extends AppCompatActivity {

    public NestedScrollView scrollView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceBudget, databaseReferenceSpecial;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);

        initComponents();
    }

    public void initComponents() {
        scrollView = findViewById(R.id.scroll_view);
        RecyclerView budgetsrecyclerView = findViewById(R.id.budgeting_budgetsrecyclerview);
        RecyclerView specialrecyclerView = findViewById(R.id.budgeting_specialrecyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceBudget = firebaseDatabase.getReference("budget");
        databaseReferenceSpecial = firebaseDatabase.getReference("specialbudget");

        ArrayList<ObjectBudget> budgetsList = new ArrayList<>();
        ArrayList<ObjectBudget> specialList = new ArrayList<>();
        databaseReferenceBudget.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    budgetsList.add(postSnapshot.getValue(ObjectBudget.class));
                }

                budgetsrecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                AdapterBudget allAdapter = new AdapterBudget(context, budgetsList);
                budgetsrecyclerView.setAdapter(allAdapter);
                allAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceSpecial.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    specialList.add(postSnapshot.getValue(ObjectBudget.class));
                }

                specialrecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                AdapterBudget allAdapter = new AdapterBudget(context, specialList);
                specialrecyclerView.setAdapter(allAdapter);
                allAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        startActivity(new Intent(this, add_budget.class));
    }

    public void intoAddSpecial(View view) {
        startActivity(new Intent(this, add_special_budget.class));
    }
}
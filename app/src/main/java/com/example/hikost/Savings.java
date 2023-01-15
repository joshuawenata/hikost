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
import com.example.hikost.adapter.AdapterSaving;
import com.example.hikost.obj.ObjectSaving;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Savings extends AppCompatActivity {

    public NestedScrollView scrollView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        initComponents();
    }

    public void initComponents() {
        scrollView = findViewById(R.id.scroll_view);
        RecyclerView recyclerView = findViewById(R.id.saving_allrecyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("saving");

        ArrayList<ObjectSaving> newList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    newList.add(postSnapshot.getValue(ObjectSaving.class));
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                AdapterSaving allAdapter = new AdapterSaving(context, newList);
                recyclerView.setAdapter(allAdapter);
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
        startActivity(new Intent(this, Budgeting.class));
        finish();
    }
    public void intoSavings(View view) {
        scrollView.smoothScrollTo(0,0);
    }    public void intoAccount(View view) {
        startActivity(new Intent(this, Account.class));
        finish();
    }
}
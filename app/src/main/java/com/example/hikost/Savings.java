package com.example.hikost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Savings extends AppCompatActivity {

    public NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        initComponents();
    }

    public void initComponents() {
        scrollView = findViewById(R.id.scroll_view);
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
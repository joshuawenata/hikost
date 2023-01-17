package com.example.hikost;

import static com.github.mikephil.charting.animation.Easing.EaseInOutCubic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    public TextView txtDashboard, txtBudgeting, txtSavings, txtAccount;
    public ImageView icDashboard, icBudgeting, icSavings, icAccount;
    public NestedScrollView scrollView;
    public PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initComponents();
    }

    public void initComponents(){
        icDashboard = findViewById(R.id.ic_graph);
        icBudgeting = findViewById(R.id.ic_filter);
        icSavings = findViewById(R.id.ic_wallet);
        icAccount = findViewById(R.id.ic_account);

        txtDashboard = findViewById(R.id.txt_graph);
        txtBudgeting = findViewById(R.id.txt_filter);
        txtSavings = findViewById(R.id.txt_wallet);
        txtAccount = findViewById(R.id.txt_account);

        scrollView = findViewById(R.id.scroll_view);

        chart = findViewById(R.id.chart);
        invokeChart();
    }

    public void invokeChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));
        PieDataSet set = new PieDataSet(entries, "Election Results");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(set);
        chart.setData(data);
        chart.invalidate(); // refresh
        chart.setHoleColor(Color.argb(1,255,0,0));
        chart.animateXY(1000,1000,EaseInOutCubic);
    }

    public void intoDashboard(View view) {
        scrollView.smoothScrollTo(0,0);
    }
    public void intoBudgeting(View view) {
        startActivity(new Intent(this, Budgeting.class));
        finish();
    }    public void intoSavings(View view) {
        startActivity(new Intent(this, Savings.class));
        finish();
    }    public void intoAccount(View view) {
        startActivity(new Intent(this, Account.class));
        finish();
    }

    public void intoTransaction(View view) {
        Intent i = new Intent(this, Transaction.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("objectLabel","Transaction");
        startActivity(i);
    }
}
package com.example.hikost;

import static com.github.mikephil.charting.animation.Easing.EaseInOutCubic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hikost.adapter.AdapterTransaction;
import com.example.hikost.obj.ObjectTransaction;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Dashboard extends AppCompatActivity {
    Context context = this;
    public TextView txtDashboard, txtBudgeting, txtSavings, txtAccount, totalValueLabel, incomesTotal, expensesTotal, mascotText, summaryTitle;
    public ImageView icDashboard, icBudgeting, icSavings, icAccount, mascotView;
    public NestedScrollView scrollView;
    public PieChart chart;
    private List<PieEntry> pieEntries;
//    Typeface tf;
    private Long totalValue = 0L, totalIncome=0L, totalExpense=0L, userExpected = 1000000L;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initComponents();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("transaction");

        RecyclerView transactionRecyclerView = findViewById(R.id.transaction_recycler_view);
        ArrayList<ObjectTransaction> transactionArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    ObjectTransaction obj = ds.getValue(ObjectTransaction.class);

                    if(obj.getTransactionType().equals("Income")){
                        totalValue += (obj.getValue()); //income
                        totalIncome += (obj.getValue());
                    } else {
                        totalValue += (obj.getValue() * -1); //expense
                        totalExpense += (obj.getValue() * -1);
                    }
                    transactionArrayList.add(obj);

                    //add as well to graph
                    pieEntries.add(new PieEntry(obj.getValue().floatValue(),obj.getCategory()));
                }
                setUpMascot(mechaDetermineMascotLevel(totalIncome,totalExpense,userExpected),mascotView,mascotText);

                PieDataSet set = new PieDataSet(pieEntries, "Monthly Spending");
                set.setValueTextColor(R.color.gray);
                set.setColors(ColorTemplate.MATERIAL_COLORS);
                PieData data = new PieData(set);
                data.setValueTextSize(15);
                data.setValueTextColor(R.color.gray);

                chart.setData(data);
                chart.invalidate(); // refresh
                chart.setHoleColor(Color.argb(1,255,0,0));
                chart.setEntryLabelColor(R.color.black);
                Typeface typeface = ResourcesCompat.getFont(Dashboard.this,R.font.montserrat_regular);
                chart.setEntryLabelTypeface(typeface);
                chart.animateXY(1000,1000,EaseInOutCubic);

                Legend legend = chart.getLegend();
                legend.setEnabled(false);

                chart.setDescription(new Description()); //set desc to none
                chart.setNoDataText("It look like you just recently moved into your Kost, huh?");

                Description description = chart.getDescription();
                description.setText("");

                chart.setUsePercentValues(true);
                chart.setHoleRadius(20);

                chart.notifyDataSetChanged();


                transactionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                AdapterTransaction adapterTransaction = new AdapterTransaction(context,transactionArrayList);


                transactionRecyclerView.setAdapter(adapterTransaction);
                adapterTransaction.notifyDataSetChanged();

                displayTotalValue(totalValue);
                displayWithCurrency(totalIncome,incomesTotal);
                displayWithCurrency(totalExpense,expensesTotal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void displayTotalValue(Long totalValue) {
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String valueWithCurrency = currencyFormatter.format(totalValue);

        totalValueLabel.setText(valueWithCurrency);
    }

    public void displayWithCurrency(Long totalValue, TextView textView) {
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String valueWithCurrency = currencyFormatter.format(totalValue);

        textView.setText(valueWithCurrency);
    }

    public void initComponents(){
        icDashboard = findViewById(R.id.ic_graph);
        icBudgeting = findViewById(R.id.ic_filter);
        icSavings = findViewById(R.id.ic_wallet);
        icAccount = findViewById(R.id.ic_account);
        mascotView = findViewById(R.id.mascot_view);

        totalValueLabel = findViewById(R.id.total_value_label);

        txtDashboard = findViewById(R.id.txt_graph);
        txtBudgeting = findViewById(R.id.txt_filter);
        txtSavings = findViewById(R.id.txt_wallet);
        txtAccount = findViewById(R.id.txt_account);
        mascotText = findViewById(R.id.mascot_text);
        incomesTotal = findViewById(R.id.total_income);
        expensesTotal = findViewById(R.id.total_expense);
        summaryTitle = findViewById(R.id.summary_title);

        scrollView = findViewById(R.id.scroll_view);

        chart = findViewById(R.id.chart);

        pieEntries = new ArrayList<>();

        rand = new Random();
    }

    public int mechaDetermineMascotLevel(Long totalIncome, Long totalExpense, Long userExpected) {
        int ret = 0;
        long diff = totalIncome - (totalExpense*(-1));

        if(diff < 0) {
            ret = -2;
        }
        else if (diff < userExpected){
            ret = -1;
        }
        else if(diff < userExpected*2){
            ret = 0;
        }
        else if(diff >= userExpected*2 && diff < userExpected*3) {
            ret = 1;
        }
        else {
            ret = 2;
        }

        return ret;
    }

    public void setUpMascot(int mascotLevel, ImageView imageView, TextView textView) {
        if(mascotLevel==-2) {
            int n = rand.nextInt(4);
            if(n==0){
                imageView.setImageResource(R.drawable.meme_crying_cat);
                textView.setText("Look at this broke person...");
            }
            else if(n==1){
                imageView.setImageResource(R.drawable.meme_crying_kim_kardashian);
                textView.setText("NOOOOOO YOU'RE SO BROKE...");
            }
            else if(n==2){
                imageView.setImageResource(R.drawable.meme_crying_michael_jordan);
                textView.setText("I cannot say anything no more. Please don't be this broke...");
            }
            else{
                imageView.setImageResource(R.drawable.meme_woman_yelling);
                textView.setText("I ASHAMED THAT YOU ARE THIS BROKE BRO...");
            }
        }
        else if(mascotLevel==-1){
            int n = rand.nextInt(7);
            if(n==0){
                imageView.setImageResource(R.drawable.meme_baby_yoda_drinking_soup);
                textView.setText("Ahhh... look at this broke person");
            }
            else if(n==1){
                imageView.setImageResource(R.drawable.meme_cat_being_yelled_at);
                textView.setText("You don't have a lot of money, do you?");
            }
            else if(n==2){
                imageView.setImageResource(R.drawable.meme_facepalm);
                textView.setText("Why are you spending so much bro...");
            }
            else if(n==3){
                imageView.setImageResource(R.drawable.meme_patrick_i_have_3_dollars);
                textView.setText("You are kinda same as broke as Patrick with 3 dollars...");
            }
            else if(n==4){
                imageView.setImageResource(R.drawable.meme_sad_frog);
                textView.setText("Your balance is not that good honestly..");
            }
            else if(n==5){
                imageView.setImageResource(R.drawable.meme_surprised_shocked_pikachu);
                textView.setText("Ahh... You are that broke person...");
            }
            else if(n==6){
                imageView.setImageResource(R.drawable.meme_this_is_fine_dog);
                textView.setText("Your money seems not fine...");
            }
        }
        else if(mascotLevel==0){
            int n = rand.nextInt(5);
            if(n==0){
                imageView.setImageResource(R.drawable.meme_awkward_look_monkey_puppet);
                textView.setText("Yeah, I guess we're fine fow now...");
            }
            if(n==1){
                imageView.setImageResource(R.drawable.meme_doge_dog);
                textView.setText("Doge said 'nice looking balance'");
            }
            if(n==2){
                imageView.setImageResource(R.drawable.meme_homer_simpson_bushes);
                textView.setText("Yep, better hiding and don't buy anything unnecessary...");
            }
            if(n==3){
                imageView.setImageResource(R.drawable.meme_norton_smirking);
                textView.setText("Hey, good balance...");
            }
            if(n==4){
                imageView.setImageResource(R.drawable.meme_ralph_wiggum_diving_through_window);
                textView.setText("Just don't buy anything stupid okay...");
            }
        }
        else if(mascotLevel==1){
            int n = rand.nextInt(5);
            if(n==0){
                imageView.setImageResource(R.drawable.meme_hide_the_pain_harold);
                textView.setText("Look at that nice series of number on your balance...");
            }
            if(n==1){
                imageView.setImageResource(R.drawable.meme_is_this_a_pigeon);
                textView.setText("Is this, wealth...?");
            }
            if(n==2){
                imageView.setImageResource(R.drawable.meme_kermit_not_my_business);
                textView.setText("Yes. Looking very good. Astonishing I might say...");
            }
            if(n==3){
                imageView.setImageResource(R.drawable.meme_leonardo_dicaprio_laughing);
                textView.setText("EYYY MY FELLOW Wolf of Wall Street broker...");
            }
            if(n==4){
                imageView.setImageResource(R.drawable.meme_polite_cat);
                textView.setText("Can I have some of your money?");
            }
        }
        else {
            int n = rand.nextInt(3);
            if(n==0){
                imageView.setImageResource(R.drawable.meme_handsome_squidward);
                textView.setText("You are a distinguished fellow rich gentlemen.");
            }
            if(n==1){
                imageView.setImageResource(R.drawable.meme_roll_safe);
                textView.setText("Big brain time baby...");
            }
            if(n==2){
                imageView.setImageResource(R.drawable.meme_salt_bae);
                textView.setText("I'M RICH HELL YEAH");
            }
        }
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
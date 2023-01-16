package com.example.hikost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hikost.InsertFirebase.BudgetInsertFirebase;
import com.example.hikost.InsertFirebase.SavingInsertFirebase;

public class add_saving extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving);
    }

    public void intoSavings(View view) {
        startActivity(new Intent(this, Savings.class));
        finishAffinity();
    }

    public void submit(View view) {
        EditText txtTitle, txtDescription, txtBudget;

        txtTitle = findViewById(R.id.title_input);
        txtDescription = findViewById(R.id.description_input);
        txtBudget = findViewById(R.id.budget_input);

        String title, description, budget;
        title = txtTitle.getText().toString();
        description = txtDescription.getText().toString();
        budget = txtBudget.getText().toString();

        boolean flag = true;
        if(title.isEmpty()){
            txtTitle.setError("Please input budget title!");
            flag = false;
        }else if(description.isEmpty()){
            txtDescription.setError("Please input budget description!");
            flag = false;
        }else if(budget.isEmpty()){
            txtBudget.setError("Please input budget!");
            flag = false;
        }else{
            try
            {
                Integer.parseInt(budget);
            }
            catch (NumberFormatException e)
            {
                txtBudget.setError("Please input number only!");
                flag = false;
            }
        }

        if(flag){
            SavingInsertFirebase.insertSaving(title, description, Integer.valueOf(budget));
            startActivity(new Intent(this, Savings.class));
            finishAffinity();
        }
    }
}
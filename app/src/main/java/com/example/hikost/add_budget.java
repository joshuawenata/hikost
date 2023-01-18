package com.example.hikost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hikost.InsertFirebase.BudgetInsertFirebase;
import com.example.hikost.obj.ObjectBudget;
import com.example.hikost.obj.User;

public class add_budget extends AppCompatActivity {

    TextView activityLabel, titleLabel, descriptionLabel, valueLabel;

    String objectLabel;

    Button bttSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        initComponents();
    }

    public void initComponents() {
        Bundle extras = getIntent().getExtras();
        objectLabel = extras.getString("objectLabel");

        activityLabel = findViewById(R.id.activity_label);
        titleLabel = findViewById(R.id.title_label);
        descriptionLabel = findViewById(R.id.description_label);
        valueLabel = findViewById(R.id.value_label);
        bttSubmit = findViewById(R.id.submit_button);

        activityLabel.setText(getString(R.string.activity_add_title,objectLabel));
        titleLabel.setText(getString(R.string.title_label,objectLabel));
        descriptionLabel.setText(getString(R.string.description_label,objectLabel));
        valueLabel.setText(getString(R.string.value_label,objectLabel));
        bttSubmit.setText(getString(R.string.submit_label,objectLabel));
    }

    public void intoBudgeting(View view) {
        startActivity(new Intent(this, Budgeting.class));
        finishAffinity();
    }

    public void submit(View view) {
        EditText txtTitle, txtDescription, txtBudget;

        txtTitle = findViewById(R.id.title_input);
        txtDescription = findViewById(R.id.description_input);
        txtBudget = findViewById(R.id.value_input);

        String title, description;
        Long budget = 0L;
        title = txtTitle.getText().toString();
        description = txtDescription.getText().toString();
        budget = Long.parseLong(txtBudget.getText().toString());

        boolean flag = true;
        if(title.isEmpty()){
            txtTitle.setError(getString(R.string.title_label_error,objectLabel));
            flag = false;
        }
        else if(budget == 0){
            txtBudget.setError(getString(R.string.value_label_error,objectLabel));
            flag = false;
        }

        ObjectBudget budgetToPush = new ObjectBudget(title, description, objectLabel, budget, User.getUserId());

        if(flag){
            //TO CHECK what kind of activity to push into Firebase
            BudgetInsertFirebase.insertBudget(budgetToPush);
            startActivity(new Intent(this, Budgeting.class));

            finishAffinity();
        }
    }
}
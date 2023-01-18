package com.example.hikost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hikost.InsertFirebase.BudgetInsertFirebase;
import com.example.hikost.InsertFirebase.SavingInsertFirebase;
import com.example.hikost.obj.ObjectBudget;
import com.example.hikost.obj.ObjectSaving;
import com.example.hikost.obj.User;

public class edit_saving extends AppCompatActivity {

    TextView activityLabel, titleLabel, categoryLabel, descriptionLabel, valueLabel, valueLabelSecond;

    String objectLabel;

    Button bttSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_saving);

        initComponents();
    }

    public void initComponents() {
        Bundle extras = getIntent().getExtras();
        objectLabel = extras.getString("objectLabel");

        activityLabel = findViewById(R.id.activity_label);
        titleLabel = findViewById(R.id.title_label);
        descriptionLabel = findViewById(R.id.description_label);
        categoryLabel = findViewById(R.id.category_label);
        valueLabel = findViewById(R.id.value_label);
        valueLabelSecond = findViewById(R.id.value_label_second);
        bttSubmit = findViewById(R.id.submit_button);

        activityLabel.setText(getString(R.string.activity_add_title,objectLabel));
        titleLabel.setText(getString(R.string.title_label,objectLabel));
        descriptionLabel.setText(getString(R.string.description_label,objectLabel));
        categoryLabel.setText(getString(R.string.category_label,objectLabel));
        valueLabel.setText(getString(R.string.value_label,objectLabel));
        valueLabelSecond.setText(getString(R.string.value_label_target,objectLabel));
        bttSubmit.setText(getString(R.string.submit_label,objectLabel));
    }

    public void intoSavings(View view) {
        startActivity(new Intent(this, Savings.class));
        finishAffinity();
    }

    public void submit(View view) {
        EditText txtTitle, txtDescription, valueInput, targetInput, categoryInput;

        txtTitle = findViewById(R.id.title_input);
        txtDescription = findViewById(R.id.description_input);
        valueInput = findViewById(R.id.budget_input);
        targetInput = findViewById(R.id.target_input);
        categoryInput = findViewById(R.id.category_input);

        String title, description, category;
        Long savings = 0L, target = 0L;
        title = txtTitle.getText().toString();
        category = categoryInput.getText().toString();
        description = txtDescription.getText().toString();
        savings = Long.parseLong(valueInput.getText().toString());
        target = Long.parseLong(targetInput.getText().toString());

        boolean flag = true;
        if(title.isEmpty()){
            txtTitle.setError(getString(R.string.title_label_error,objectLabel));
            flag = false;
        }
        else if(savings == 0){
            valueInput.setError(getString(R.string.value_label_error,objectLabel));
            flag = false;
        }
        else if(target == 0){
            targetInput.setError(getString(R.string.value_label_error,objectLabel));
            flag = false;
        }

        ObjectSaving savingsToPush = new ObjectSaving(title, category, description, objectLabel, savings, target);

        if(flag){
            SavingInsertFirebase.insertSaving(savingsToPush);
            startActivity(new Intent(this, Savings.class));
            finishAffinity();
        }
    }
}
package com.example.hikost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hikost.InsertFirebase.TransactionInsertFirebase;
import com.example.hikost.obj.ObjectTransaction;
import com.example.hikost.obj.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Transaction extends AppCompatActivity {
    //objectLabel
    String objectLabel, transactionType;

    //toggle income/expense
    MaterialButtonToggleGroup toggleGroup;

    Spinner categoryInput;

    //calendar vars
    final Calendar myCalendar= Calendar.getInstance();
    EditText titleInput, valueInput, datePicker, descInput, paymentTypeInput;
    Button bttSubmit;
    ImageView bttBack;
    TextView activityLabel, titleLabel, descriptionLabel, valueLabel, dateLabel;
    LinearLayout activityHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        initComponents();
        toggleListener();

        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                Intent i = new Intent(Transaction.this, Dashboard.class);
                // set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        bttSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionInsertFirebase.insertTransaction(getFormValues());
                Intent i = new Intent(Transaction.this, Dashboard.class);
                // set the new task
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    public void initComponents() {
        //get object label
        Bundle extras = getIntent().getExtras();
        objectLabel = extras.getString("objectLabel");

        activityHeader = findViewById(R.id.activity_header);
        bttBack = findViewById(R.id.back_btt);
        activityLabel = findViewById(R.id.activity_label);
        bttSubmit = findViewById(R.id.submit_button);
        toggleGroup = findViewById(R.id.type_of_transaction_toggle);
        titleLabel = findViewById(R.id.title_label);
        dateLabel = findViewById(R.id.date_label);
        descriptionLabel = findViewById(R.id.description_label);
        valueLabel = findViewById(R.id.value_label);

        //FIND INPUTS
        titleInput = findViewById(R.id.title_input);
        categoryInput = findViewById(R.id.category_input);
        descInput = findViewById(R.id.description_input);
        datePicker = findViewById(R.id.date_picker);
        valueInput = findViewById(R.id.value_input);
        paymentTypeInput = findViewById(R.id.payment_type_input);

        activityLabel.setText(getString(R.string.activity_add_title,objectLabel));
        titleLabel.setText(getString(R.string.title_label,objectLabel));
        dateLabel.setText(getString(R.string.date_label,objectLabel));
        descriptionLabel.setText(getString(R.string.description_label,objectLabel));
        valueLabel.setText(getString(R.string.value_label,objectLabel));
        bttSubmit.setText(getString(R.string.submit_label,objectLabel));

        //set bttIncome as initial value
        toggleGroup.check(R.id.btt_income);
        activityHeader.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.primary));
        transactionType = "Income";
        titleInput.setHint(R.string.uang_jajan);



        categoryInput =  findViewById(R.id.category_input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.transaction_category, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        categoryInput.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Transaction.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public ObjectTransaction getFormValues() {
        ObjectTransaction transaction = new ObjectTransaction();

        transaction.setUserId(User.getUserId());
        transaction.setTransactionType(transactionType);
        transaction.setTitle(titleInput.getText().toString());
        transaction.setCategory(categoryInput.getSelectedItem().toString());
        transaction.setDescription(descInput.getText().toString());
        transaction.setDate(datePicker.getText().toString());
        transaction.setValue(Long.parseLong(valueInput.getText().toString()));
        transaction.setPaymentType(paymentTypeInput.getText().toString());

        return transaction;
    }

    public void toggleListener() {
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.btt_income) {
                        activityHeader.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.primary));
                        bttSubmit.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.primary));
                        transactionType = "Income";
                        titleInput.setHint(R.string.uang_jajan);
                    }
                    else if (checkedId == R.id.btt_expense) {
                        activityHeader.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.danger));
                        bttSubmit.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.danger));
                        transactionType = "Expense";
                        titleInput.setHint(R.string.lunch_ayam_geprek);
                    }
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        datePicker.setText(dateFormat.format(myCalendar.getTime()));
    }
}
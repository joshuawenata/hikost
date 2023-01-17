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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Transaction extends AppCompatActivity {
    //objectLabel
    String objectLabel;

    //toggle income/expense
    MaterialButtonToggleGroup toggleGroup;

//    Spinner type_of_transaction_spinner;

    //calendar vars
    final Calendar myCalendar= Calendar.getInstance();
    EditText titleInput, valueInput, datePicker;
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
                finish();
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
        valueInput = findViewById(R.id.value_input);
        titleInput = findViewById(R.id.title_input);

        activityLabel.setText(getString(R.string.activity_add_title,objectLabel));
        titleLabel.setText(getString(R.string.title_label,objectLabel));
        dateLabel.setText(getString(R.string.date_label,objectLabel));
        descriptionLabel.setText(getString(R.string.description_label,objectLabel));
        valueLabel.setText(getString(R.string.value_label,objectLabel));
        bttSubmit.setText(getString(R.string.submit_label,objectLabel));

        //set bttIncome as initial value
        toggleGroup.check(R.id.btt_income);
        activityHeader.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.primary));

//        type_of_transaction_spinner =  findViewById(R.id.type_of_transaction_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.state, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//        type_of_transaction_spinner.setAdapter(adapter);

        datePicker = findViewById(R.id.date_picker);
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

    public void toggleListener() {
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.btt_income) {
                        activityHeader.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.primary));
                        bttSubmit.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.primary));
                    }
                    else if (checkedId == R.id.btt_expense) {
                        activityHeader.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.danger));
                        bttSubmit.setBackgroundColor(ContextCompat.getColor(Transaction.this,R.color.danger));
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
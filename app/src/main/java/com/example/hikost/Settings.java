package com.example.hikost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    LinearLayout activityHeader;
    ImageView backButton;
    TextView activityLabel;
    String objectLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initComponents();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this, Dashboard.class);
                // set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    public void initComponents(){
        Bundle extras = getIntent().getExtras();
        objectLabel = extras.getString("objectLabel");

        activityHeader = findViewById(R.id.activity_header);
        backButton = findViewById(R.id.back);
        activityLabel = findViewById(R.id.activity_label);

        activityLabel.setText(objectLabel);
    }
}
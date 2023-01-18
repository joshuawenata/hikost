package com.example.hikost.ui;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyValueFormatter extends ValueFormatter {
    private DecimalFormat decimalFormat;

    public MyValueFormatter() {
        decimalFormat = new DecimalFormat("###,###,##0.0");
    }
}

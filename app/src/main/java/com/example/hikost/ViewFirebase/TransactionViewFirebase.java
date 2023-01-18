package com.example.hikost.ViewFirebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionViewFirebase {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference transacRef = database.getReference("transaction");

    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static DatabaseReference getTransacRef() {
        return transacRef;
    }
}

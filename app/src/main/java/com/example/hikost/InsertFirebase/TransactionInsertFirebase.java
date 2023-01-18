package com.example.hikost.InsertFirebase;

import com.example.hikost.obj.ObjectTransaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;

public class TransactionInsertFirebase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference transacRef = database.getReference("transaction");

    public static void insertTransaction(ObjectTransaction objectTransaction) {
        //input push time
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            long pushTime = Instant.now().getEpochSecond();
            objectTransaction.setPushTime(pushTime);
        }

        String key = transacRef.push().getKey();
        transacRef.child(key).setValue(objectTransaction);
    }

}

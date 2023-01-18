package com.example.hikost.InsertFirebase;

import com.example.hikost.InsertFirebase.Model.ModelBudget;
import com.example.hikost.obj.ObjectSaving;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;

public class SavingInsertFirebase {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference savingRef = db.getReference("saving");
    public static void insertSaving(ObjectSaving objectSaving){
        //input push time
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            long pushTime = Instant.now().getEpochSecond();
            objectSaving.setPushTime(pushTime);
        }

        String key = savingRef.push().getKey();
        savingRef.child(key).setValue(objectSaving);
    }
}

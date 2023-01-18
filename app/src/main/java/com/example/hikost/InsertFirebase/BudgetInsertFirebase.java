package com.example.hikost.InsertFirebase;

import com.example.hikost.InsertFirebase.Model.ModelBudget;
import com.example.hikost.obj.ObjectBudget;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;

public class BudgetInsertFirebase {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference budgetRef = database.getReference("budget");
    public static void insertBudget(ObjectBudget objectBudget){
        //input push time
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            long pushTime = Instant.now().getEpochSecond();
            objectBudget.setPushTime(pushTime);
        }

        String key = budgetRef.push().getKey();
        budgetRef.child(key).setValue(objectBudget);
    }
}

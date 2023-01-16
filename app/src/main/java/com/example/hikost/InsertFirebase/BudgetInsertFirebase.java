package com.example.hikost.InsertFirebase;

import com.example.hikost.InsertFirebase.Model.ModelBudget;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BudgetInsertFirebase {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference Ref = db.getReference("budget");
    public static void insertBudget(String title, String description, Integer value){
        String Key = Ref.push().getKey();
        ModelBudget newBudget = new ModelBudget(Key,
                title,
                description,
                value);
        Ref.child(Key).setValue(newBudget.toMap());
    }
}

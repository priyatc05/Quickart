package com.middlesex.quickart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.middlesex.quickart.databinding.ActivityMainBinding;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference rfidReference = db.getReference("rfid_data");
        DatabaseReference prodReference = db.getReference("productDetail");
        HashMap<String, productDetails> prodList = new HashMap<String, productDetails>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rfidReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String, HashMap> value = (HashMap<String, HashMap>) dataSnapshot.getValue();
                for(HashMap.Entry entry: value.entrySet()){
                    HashMap itr = (HashMap) entry.getValue();
                    String productName = itr.get("card_data").toString().split("-")[0];
                    prodReference.child(productName).child("price").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {

                                if(prodList.containsKey(productName)){
                                    prodList.get(productName).setProductQuantity(prodList.get(productName).getProductQuantity()+1);
                                }
                                else{
                                    productDetails x = new productDetails(productName, "klint", Integer.valueOf(String.valueOf(task.getResult().getValue())), 1);
                                    prodList.put(productName, x);
                                }
                                Log.d("prodList", String.valueOf(prodList));
//                                Log.d(productName, "price:"+String.valueOf(task.getResult().getValue()));
                            }
                        }
                    });
//                    Log.d(productName, "price:" +prodReference.child(productName).child("price").get());
                }
                Log.d("prodList", String.valueOf(prodList.size()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });
    }
}






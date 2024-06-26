package com.middlesex.quickart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class home_screen extends AppCompatActivity {

    Button btn_cart;

//    FirebaseDatabase db = FirebaseDatabase.getInstance();
//    DatabaseReference rfidReference = db.getReference("rfid_data");
//    DatabaseReference prodReference = db.getReference("productDetail");
//    HashMap<String, productDetails> prodList = new HashMap<String, productDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        rfidReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                HashMap<String, HashMap> value = (HashMap<String, HashMap>) dataSnapshot.getValue();
//                for(HashMap.Entry entry: value.entrySet()){
//                    HashMap itr = (HashMap) entry.getValue();
//                    String productName = itr.get("card_data").toString().split("-")[0];
//                    prodReference.child(productName).child("price").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//
//                        @Override
//                        public void onComplete(@NonNull Task<DataSnapshot> task) {
//                            if (!task.isSuccessful()) {
//                                Log.e("firebase", "Error getting data", task.getException());
//                            }
//                            else {
//
//                                if(prodList.containsKey(productName)){
//                                    prodList.get(productName).setProductQuantity(prodList.get(productName).getProductQuantity()+1);
//                                }
//                                else{
//                                    productDetails x = new productDetails(productName, "klint", Integer.valueOf(String.valueOf(task.getResult().getValue())), 1);
//                                    prodList.put(productName, x);
//                                }
//                                Log.d("prodList", String.valueOf(prodList));
////                                Log.d(productName, "price:"+String.valueOf(task.getResult().getValue()));
//                            }
//                        }
//                    });
////                    Log.d(productName, "price:" +prodReference.child(productName).child("price").get());
//                }
//                Log.d("prodList", String.valueOf(prodList.size()));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("file", "Failed to read value.", error.toException());
//            }
//        });

        btn_cart=findViewById(R.id.btn_cart);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(home_screen.this, list_page.class);
//                intent.putExtra("prodList", prodList);
                startActivity(intent);
            }
        });

    }
}
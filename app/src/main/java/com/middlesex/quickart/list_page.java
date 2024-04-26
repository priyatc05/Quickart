package com.middlesex.quickart;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class list_page extends AppCompatActivity {

    Button button_proceed_to_payment;
    LinearLayout productListContainer;
    Boolean flag = true;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference productDetailsRef;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference rfidReference = db.getReference("rfid_data");
    DatabaseReference prodReference = db.getReference("productDetail");
    HashMap<String, productDetails> prodList = new HashMap<String, productDetails>();

    HashMap<String, HashMap<String, TextView>> productViewsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//  prodList.clear();
        super.onCreate(savedInstanceState);
        rfidReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int total=0;
                productListContainer.removeAllViews();
                for(productDetails val: prodList.values()){
                    addProductRow(val.getProductName(), val);
                    total+= val.getProductQuantity()*val.getProductPrice();
                }
                button_proceed_to_payment.setText("Your total: "+total+" Proceed to payment");

                prodList.clear();

//                prodList.clear();
                Log.d("reached", "reached");
                productViewsMap.clear();
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
                            else { //milk, comb, lipstick, milk, comb     //string, productetails

                                if(prodList.containsKey(productName)){
                                    prodList.get(productName).setProductQuantity(prodList.get(productName).getProductQuantity()+1);
                                }
                                else{
                                    productDetails x = new productDetails(productName, "iohbibuhuy", Integer.valueOf(String.valueOf(task.getResult().getValue())), 1);
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


//                prodList.clear();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("file", "Failed to read value.", error.toException());
            }
        });

        Log.d("prodlist size", String.valueOf(prodList.size()));

        setContentView(R.layout.list_page); // Update this line with the correct file name

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        productDetailsRef = firebaseDatabase.getReference("productDetail");
//
        productListContainer = findViewById(R.id.product_list_container);


//        productDetailsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                productListContainer.removeAllViews();
//                productViewsMap.clear();
//
//                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
//                    String productName = productSnapshot.getKey();
//                    productDetails product = productSnapshot.getValue(productDetails.class);
//
//                    if (product != null) {
//                        addProductRow(productName, product);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database error
//            }
//        });

        button_proceed_to_payment = findViewById(R.id._button_proceed_to_payment_);
        button_proceed_to_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(list_page.this, Payment_screen.class);
//                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
    }

    private void addProductRow(String productName, productDetails product) {
        LinearLayout productRow = new LinearLayout(this);
        productRow.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        productRow.setOrientation(LinearLayout.HORIZONTAL);
        productRow.setGravity(Gravity.CENTER_VERTICAL);
        productRow.setPadding(16, 16, 16, 16);

        // Create TextViews for product name, quantity, and price
        TextView productNameTextView = new TextView(this);
        TextView productQuantityTextView = new TextView(this);
        TextView productPriceTextView = new TextView(this);

        productNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
        ));
        productQuantityTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));
        productPriceTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        ));

        productNameTextView.setText(product.getProductName());
        productQuantityTextView.setText(String.valueOf(product.getProductQuantity()));
        productPriceTextView.setText(String.valueOf(product.getProductPrice()));

        productNameTextView.setTextColor(Color.WHITE);
        productQuantityTextView.setTextColor(Color.WHITE);
        productPriceTextView.setTextColor(Color.WHITE);

        productQuantityTextView.setGravity(Gravity.END);
        productPriceTextView.setGravity(Gravity.END);

        // Create and configure the delete button
        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setTextColor(Color.WHITE);
        deleteButton.setBackgroundResource(R.drawable.rounded_button);
        deleteButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        deleteButton.setPadding(16, 8, 16, 8);

        // Set an OnClickListener for the delete button
        deleteButton.setOnClickListener(v -> {
            // Remove the product from the Firebase Realtime Database
            Query prodDel = rfidReference.orderByChild("card_data").equalTo(productName);
            prodDel.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot prod: snapshot.getChildren()){
                        prod.getRef().removeValue();
                    }
                    finish();
                    startActivity(getIntent());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("cancelled", "cancelled Delete: ", error.toException());
                }
            });

        });

        // Add the TextViews and the delete button to the product row
        productRow.addView(productNameTextView);
        productRow.addView(productQuantityTextView);
        productRow.addView(productPriceTextView);
        productRow.addView(deleteButton);

        // Add the product row to the container
        productListContainer.addView(productRow);

        // Store the TextViews in the productViewsMap
        HashMap<String, TextView> viewMap = new HashMap<>();
        viewMap.put("name", productNameTextView);
        viewMap.put("quantity", productQuantityTextView);
        viewMap.put("price", productPriceTextView);
        productViewsMap.put(productName, viewMap);
    }}

















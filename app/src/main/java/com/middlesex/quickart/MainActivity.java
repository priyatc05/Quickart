package com.middlesex.quickart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.middlesex.quickart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        String productId, producName;
        int productPrice;
        FirebaseDatabase db;
        DatabaseReference reference;


    FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product1=new Product();
                product1.setProductId("www222");
                product1.setProductName("Laban");
                product1.setProductPrice(3);
                Product product2=new Product();
                product2.setProductId("xxx333");
                product2.setProductName("lipstick");
                product2.setProductPrice(4);

                db = FirebaseDatabase.getInstance();
                reference = db.getReference("productDetail");
                reference.child(product1.getProductName()).setValue(product1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Sucessfully updated", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });


    }
}
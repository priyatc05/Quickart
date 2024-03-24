package com.middlesex.quickart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.middlesex.quickart.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

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
        FirebaseApp.initializeApp(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<Product> productList = new ArrayList<>();
        Product product1=new Product();
        product1.setProductId("www222");
        product1.setProductName("Laban");
        product1.setProductPrice(3);
        productList.add(product1);
        Product product2=new Product();
        product2.setProductId("xxx333");
        product2.setProductName("lipstick");
        product2.setProductPrice(4);
        productList.add(product2);
        Product product3 = new Product();
        product3.setProductId("gfh37837");
        product3.setProductName("Balloon");
        product3.setProductPrice(1);
        productList.add(product3);
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseDatabase.getInstance();
                reference = db.getReference("productDetail");
                for(int i=0;i<productList.size();i++) {
                    int finalI = i;
                    reference.child(productList.get(i).getProductName()).setValue(productList.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Sucessfully added to database: " + productList.get(finalI).getProductName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {


    private ListView listView;

    private ArrayList<Payment> list;
    private com.google.firebase.database.DatabaseReference databaseReference;
    private ValueEventListener databaseListener = null;
    Payment payment;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://smart-wallet-9a2f3-default-rtdb.europe-west1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = (ListView) findViewById(R.id.listPayments);
        databaseReference = database.getReference().child("wallet");

        list = new ArrayList<>();
        final PaymentAdapter adapter = new PaymentAdapter(this, R.layout.parent_item, list);

        payment = new Payment();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    payment = ds.getValue(Payment.class);
                    list.add(payment);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
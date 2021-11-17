package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity<DatabaseReference> extends AppCompatActivity {

    private Button searchButton, updateButton;
    private TextView tStatus;
    private EditText eSearch, eIncome, eExpenses;
    private String currentMonth;
    private ValueEventListener databaseListener = null;
    private Spinner spinner;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;


    private com.google.firebase.database.DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tStatus = (TextView) findViewById(R.id.message_box);
        eIncome = (EditText) findViewById(R.id.income_box);
        eExpenses = (EditText) findViewById(R.id.expenses_box);
        spinner = (Spinner) findViewById(R.id.spinner);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smart-wallet-9a2f3-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference =  database.getReference();

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,spinnerDataList);

        updateButton = (Button) findViewById(R.id.update_button);
        spinner.setAdapter(adapter);
        retrieveMonth();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                System.out.println(spinner.getItemAtPosition(position).toString());
                currentMonth = spinner.getItemAtPosition(position).toString();
                createNewDBListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eSearch.getText().toString().isEmpty()) {

                    if(!eIncome.getText().toString().isEmpty() && !eExpenses.getText().toString().isEmpty())
                    {

                        if (databaseReference != null && currentMonth != null && databaseListener != null)
                            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);

                        databaseReference =  database.getReference().child("calendar").child(currentMonth);

                        float newIncome=0,newExpenses=0;
                        try{
                            newIncome = Float.parseFloat(eIncome.getText().toString());
                        }catch(NumberFormatException e){
                            databaseReference.child("income").setValue(0);
                        }

                        try{
                            newExpenses = Float.parseFloat(eExpenses.getText().toString());
                        }catch(NumberFormatException e){

                            databaseReference.child("expenses").setValue(0);
                        }

                        databaseReference.child("expenses").setValue(newExpenses);
                        databaseReference.child("income").setValue(newIncome);


                    }

                } else {

                    Toast.makeText(MainActivity.this, "Search field may not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void retrieveMonth()
    {


        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()){

                    spinnerDataList.add(item.getKey().toString());
                    System.out.println(item.getKey().toString());
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.child("calendar").addValueEventListener(databaseListener);
    }

    private void createNewDBListener() {
        // remove previous databaseListener

        if (databaseReference != null && currentMonth != null && databaseListener != null)
            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);

        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MonthlyExpenses monthlyExpense = dataSnapshot.getValue(MonthlyExpenses.class);
                // explicit mapping of month name from entry key
                monthlyExpense.month = dataSnapshot.getKey();

                eIncome.setText(String.valueOf(monthlyExpense.getIncome()));
                eExpenses.setText(String.valueOf(monthlyExpense.getExpenses()));
                tStatus.setText("Found entry for " + currentMonth);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        };

        // set new databaseListener
        databaseReference.child("calendar").child(currentMonth).addValueEventListener(databaseListener);
    }

}
package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity<DatabaseReference> extends AppCompatActivity {

    private Button searchButton;
    private TextView tStatus;
    private EditText eSearch, eIncome, eExpenses;
    private String currentMonth;
    private ValueEventListener databaseListener = null;

    private com.google.firebase.database.DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tStatus = (TextView) findViewById(R.id.message_box);
        eSearch = (EditText) findViewById(R.id.month_box);
        eIncome = (EditText) findViewById(R.id.income_box);
        eExpenses = (EditText) findViewById(R.id.expenses_box);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eSearch.getText().toString().isEmpty()) {
                    // save text to lower case (all our months are stored online in lower case)
                    currentMonth = eSearch.getText().toString().toLowerCase();
                    databaseReference =  database.getReference().child("calendar").child(currentMonth);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //MonthlyExpenses monthlyExpense = snapshot.getValue(MonthlyExpenses.class);
                            // explicit mapping of month name from entry key
                           // monthlyExpense.month = snapshot.getKey();

                            eIncome.setText(snapshot.child("income").getValue().toString());
                            eExpenses.setText(snapshot.child("expenses").getValue().toString());
                            tStatus.setText("Found entry for " + currentMonth);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    tStatus.setText("Searching...");
                    //createNewDBListener();
                } else {

                    Toast.makeText(MainActivity.this, "Search field may not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
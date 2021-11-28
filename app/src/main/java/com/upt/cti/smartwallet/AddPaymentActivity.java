package com.upt.cti.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AddPaymentActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ValueEventListener databaseListener;

    private TextView timestamp;
    private Spinner type;
    private EditText price, description;
    private Payment payment;

    private Button saveButton,deleteButton;
    private Object View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        timestamp = (TextView) findViewById(R.id.timestamp);
        type = (Spinner) findViewById(R.id.type_spinner);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);

        saveButton = (Button) findViewById(R.id.save_button);
        deleteButton = (Button) findViewById(R.id.delete_button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://smart-wallet-9a2f3-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = database.getReference();


    }
    public void clicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.save_button:
                //Action on save button pressed
                break;
            case R.id.delete_button:
                //Action on delete button pressed
        }
    }
}
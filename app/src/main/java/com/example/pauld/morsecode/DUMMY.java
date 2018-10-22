package com.example.pauld.morsecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DUMMY extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        //User Init
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseUser user = userAuth.getCurrentUser();

        //Database Init
        FirebaseDatabase firebaseDBInstance = FirebaseDatabase.getInstance();
        DatabaseReference firebaseReferenceOne = firebaseDBInstance.getReference("one");


        // Read from the database
        firebaseReferenceOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String msg = "Value is: "+dataSnapshot.getValue()+
                             "\nKey is: "+dataSnapshot.getKey();
                Toast.makeText(getApplicationContext(),(String)dataSnapshot.getValue(),Toast.LENGTH_SHORT).show();
                Log.d("SINGLETAG", msg);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("SINGLETAG", "Failed to read value.", error.toException());
            }
        });


        if(user == null){
            Toast.makeText(getApplicationContext(),"NOT LOGGED IN BUD",Toast.LENGTH_SHORT).show();
        }
        /*IFIFIFIFIFIFI*/

        Button btn_signUp = findViewById(R.id.btn_signUp);
        Button btn_logIn = findViewById(R.id.btn_logIn);

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}

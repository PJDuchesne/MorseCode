package com.example.pauld.morsecode;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DUMMY extends AppCompatActivity {

    private ArrayList<String> phrases;
    private ListView lv1;
    private FirebaseUser user;
    private FirebaseAuth userAuth;
    private Button btn_signUp,btn_logIn,btn_submit,btn_logout;
    private TextView txt_user,txt_info;
    private FirebaseDatabase firebaseDBInstance;
    private DatabaseReference firebaseReferenceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        //User Init
        userAuth = FirebaseAuth.getInstance();
        user = userAuth.getCurrentUser();


        //Database Init
        firebaseDBInstance = FirebaseDatabase.getInstance();
        DatabaseReference firebaseReferenceOne = firebaseDBInstance.getReference("one");
        DatabaseReference firebaseReferencePublic = firebaseDBInstance.getReference("public");


        //Screen Elements
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_logIn = findViewById(R.id.btn_logIn);
        btn_submit = findViewById(R.id.btn_submit);
        btn_logout = findViewById(R.id.btn_logout);
        txt_user = findViewById(R.id.txt_user);
        txt_info = findViewById(R.id.txt_info);
        lv1 = findViewById(R.id.lv1);

        //List Adapter
        phrases = new  ArrayList<String>();


        // Read from the database
        firebaseReferenceOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Datasnapshot has access to all the elements in the reference branch .
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

        firebaseReferencePublic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Datasnapshot has access to all the elements in the reference branch
                //Using datasnapshot we build the arraylist that we want to attach to the list view
                String msg = "";
                for( DataSnapshot D : dataSnapshot.getChildren() ){
                    msg = D.getKey() +": "+D.getValue();
                    phrases.add(msg);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, phrases) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView t = (TextView) view.findViewById(android.R.id.text1);
                        t.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                lv1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("SINGLETAG", "Failed to read value.", error.toException());
            }
        });

        checkUser();

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

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAuth.signOut();
                checkUser();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        checkUser();
    }

    private void checkUser() {
        user = userAuth.getCurrentUser();
        if(user != null){
            txt_user.setText("Welcome");
            txt_info.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
            btn_signUp.setVisibility(View.INVISIBLE);
            btn_logIn.setVisibility(View.INVISIBLE);
        }else{
            txt_user.setText("Not Logged In");
            txt_info.setVisibility(View.INVISIBLE);
            btn_submit.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
            btn_signUp.setVisibility(View.VISIBLE);
            btn_logIn.setVisibility(View.VISIBLE);
        }
    }

}

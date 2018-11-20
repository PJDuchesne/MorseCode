package com.example.pauld.morsecode;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DUMMY extends AppCompatActivity {

    private ArrayList<String> phrases;
    private ListView lv1,lv2;
    private FirebaseUser user;
    private FirebaseAuth userAuth;
    private Button btn_signUp,btn_logIn,btn_submit,btn_logout;
    private TextView txt_user,txt_info;
    private FirebaseDatabase firebaseDBInstance;
    private DatabaseReference firebaseReferenceUser;
    private Map<String, Object> userInfo;
    private ArrayList<String> userInfoList;

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
        lv2 = findViewById(R.id.lv2);

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

        //NOTE: submit is only visible if the user is logged in and therefore firebaseReferenceUser is set
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String key = firebaseReferenceUser.child("info").push().getKey();
               Map<String, Object> updates = new HashMap<>();
               updates.put("/info/"+key,txt_info.getText().toString());
               firebaseReferenceUser.updateChildren(updates);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        checkUser();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        userAuth.signOut();
    }

    private void checkUser() {
        user = userAuth.getCurrentUser();

        if(user != null){
            getUserCompletedLessons();
            lv2.setVisibility(View.VISIBLE);
            firebaseReferenceUser = firebaseDBInstance.getReference("users").child(user.getUid());
            firebaseDBInstance.getReference("users").child(user.getUid()).child("lessonsCompleted").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean[] lessonsCompleted = new Boolean[10];
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        lessonsCompleted[Integer.valueOf(d.getKey())] = Boolean.valueOf( d.getValue().toString() );
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            firebaseReferenceUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for( DataSnapshot D : dataSnapshot.getChildren() ){
                        if(D.getKey().equals("name")){
                            txt_user.setText("Welcome "+D.getValue());
                        }
                        if(D.getKey().equals("info")){
                            userInfo = new HashMap<>();
                            userInfoList = new ArrayList<>();
                            for(DataSnapshot childInfo : D.getChildren()){
                                userInfo.put(childInfo.getKey(),childInfo.getValue());
                                userInfoList.add(childInfo.getValue().toString());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, userInfoList) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getView(position, convertView, parent);
                                    TextView t = (TextView) view.findViewById(android.R.id.text1);
                                    t.setTextColor(Color.BLACK);
                                    return view;
                                }
                            };
                            lv2.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv = (TextView) view;
                    for(String key : userInfo.keySet()){
                        Log.d("SINGLETAG",userInfo.get(key).toString());
                        if(userInfo.get(key).toString().equals(tv.getText().toString())){
                            Log.d("SINGLETAG",key);
                            firebaseReferenceUser.child("info").child(key).removeValue();
                            return;
                        }
                    }
                }
            });


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
            lv2.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This method fetches the user data and populates an array of lessons completed
     *
     * Setting a lesson as completed is done with this single line where N is the lesson competed
     *firebaseDBInstance.getReference("users").child(user.getUid()).child("lessonsCompleted").child("N").setValue(1);
     * */
    //private FirebaseUser user;
    //private FirebaseAuth userAuth;
    //private FirebaseDatabase firebaseDBInstance;
    private Boolean[] lessonsCompleted = new Boolean[10];
    private void getUserCompletedLessons(){
        //firebaseDBInstance = FirebaseDatabase.getInstance();
        //User Init
        userAuth = FirebaseAuth.getInstance();
        user = userAuth.getCurrentUser();
        if(user == null){
            lessonsCompleted = null;
            return;
        }
        firebaseDBInstance.getReference("users").child(user.getUid()).child("lessonsCompleted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    lessonsCompleted[Integer.valueOf(d.getKey())] =  (1 == Integer.valueOf( d.getValue().toString() ));
                }
                Toast.makeText(getApplicationContext(), Arrays.toString(lessonsCompleted),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

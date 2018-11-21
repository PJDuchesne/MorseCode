package com.example.pauld.morsecode;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Lessons extends AppCompatActivity {
    private FirebaseDatabase firebaseDBInstance;
    private DatabaseReference firebaseReferenceLessons;
    private ArrayList<String> lessonsList;
    private ListView lessonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        firebaseDBInstance = FirebaseDatabase.getInstance();
        firebaseReferenceLessons = firebaseDBInstance.getReference("lessons");
        lessonsList = new ArrayList<String>();
        lessonListView = findViewById(R.id.lessonListView);


        firebaseReferenceLessons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for( DataSnapshot D : dataSnapshot.getChildren() ){
                    lessonsList.add(D.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, lessonsList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView t = (TextView) view.findViewById(android.R.id.text1);
                        t.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                lessonListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("SINGLETAG", "Failed to read value.", error.toException());
            }
        });

        lessonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                firebaseReferenceLessons.child(tv.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                        String response = dataSnapshot.getValue().toString();
                        Intent intent1=new Intent(getApplicationContext(),TrainingScreen.class);
                        intent1.putExtra("LESSON_STRING",response);
                        startActivity(intent1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Toast.makeText(getApplicationContext(), firebaseReferenceLessons.child(tv.getText().toString())..toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

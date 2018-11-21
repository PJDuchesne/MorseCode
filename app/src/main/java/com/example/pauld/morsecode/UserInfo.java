package com.example.pauld.morsecode;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserInfo {
    private FirebaseUser user;
    private FirebaseAuth userAuth;
    private FirebaseDatabase firebaseDBInstance;
    private boolean[] lessonsCompleted;

    public UserInfo(){
        firebaseDBInstance = FirebaseDatabase.getInstance();
        userAuth = FirebaseAuth.getInstance();
        user = userAuth.getCurrentUser();
    }

    public void getCompletedLessons(final UserInfoListener callback) {
        lessonsCompleted = new boolean[10];
        if (user == null) {
            for(int i = 0; i < 10; i++)
                lessonsCompleted[i] = false;
            callback.onArrayFetched(lessonsCompleted);
            return;
        }
        firebaseDBInstance.getReference("users").child(user.getUid()).child("lessonsCompleted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    lessonsCompleted[Integer.valueOf(d.getKey())] = (1 == Integer.valueOf(d.getValue().toString()));
                }
                callback.onArrayFetched(lessonsCompleted);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setCompleted(int lessonIndex){
        if (user != null) {
            firebaseDBInstance.getReference("users").child(user.getUid()).child("lessonsCompleted").child(String.valueOf(lessonIndex)).setValue(1);
        }
    }
}

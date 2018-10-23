package com.example.pauld.morsecode;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Button btn_signUp;
    private EditText edit_email ,edit_pass ,edit_name;
    private String name,password,email;
    private FirebaseAuth userAuth;
    private FirebaseDatabase firebaseDBInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //User Init
        userAuth = FirebaseAuth.getInstance();

        //Database Init
        firebaseDBInstance = FirebaseDatabase.getInstance();

        btn_signUp = findViewById(R.id.btn_signUp);
        edit_email = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);
        edit_name = findViewById(R.id.edit_name);


        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_email.getText().toString();
                name = edit_name.getText().toString();
                password = edit_pass.getText().toString();
                if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"All fields must be filled in",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // https://firebase.google.com/docs/auth/android/password-auth
                userAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SINGLETAG", "createUserWithEmail:success");
                                    FirebaseUser user = userAuth.getCurrentUser();
                                    DatabaseReference userData = firebaseDBInstance.getReference("users").child(user.getUid());
                                    userData.child("name").setValue(name);
                                    userData.child("ID").setValue(user.getUid());
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d("SINGLETAG", "createUserWithEmail:failure");
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }
}

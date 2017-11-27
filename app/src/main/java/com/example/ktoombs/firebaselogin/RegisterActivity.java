package com.example.ktoombs.firebaselogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by ktoombs on 11/22/2017.
 */

public class RegisterActivity extends AppCompatActivity{

    private EditText firtName, lastName, email, password;
    private Button register;
    private final String TAG = "debug";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firtName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "*** onClick() ***");
                registerUser(email.getText().toString(), password.getText().toString());
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(String email, String password){
        Log.d(TAG, "*** registerUser() ***");

        if(email.isEmpty()){
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "Register user successful.");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent homepageIntent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(homepageIntent);
                        }
                        else{
                            Log.d(TAG, "Register unsuccessful. " + task.getException());
                        }
                    }
                });
    }
}

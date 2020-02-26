package com.example.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {


    private EditText enrollment_number , mobile_number , email , password;
    private TextView lgn_only ;
    private Button register ;
    private FirebaseAuth fAuth;
    //private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        enrollment_number = findViewById(R.id.email);
        mobile_number = findViewById(R.id.mob);
        register = findViewById(R.id.btn);
        email = findViewById(R.id.eml);
        password = findViewById(R.id.pass);
        lgn_only = findViewById(R.id.login);

        fAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext() , MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString().trim();
                String enrollment = enrollment_number.getText().toString().trim();
                String mobile = mobile_number.getText().toString().trim();
                String emails = email.getText().toString().trim();

                if(TextUtils.isEmpty(emails))
                {
                    email.setError("wrong");
                    return ;
                }

                if(TextUtils.isEmpty(enrollment)){
                    enrollment_number.setError("Enrollment Number is Required");
                    return ;
                }

                if(TextUtils.isEmpty(mobile))
                {
                    mobile_number.setError("Mobile number is req");
                    return ;
                }

                if(TextUtils.isEmpty(pass))
                {
                    password.setError("password is required");
                    return ;
                }
                if(pass.length() < 6)
                {
                    password.setError("at least 6 char is required");
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword( emails ,pass ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this, "user created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , current_location.class));
                        }else{
                            Toast.makeText(register.this, "error"  + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });

        lgn_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , login.class));
            }
        });


    }
}

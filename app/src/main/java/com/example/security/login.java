package com.example.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText email , password ;
    private Button mlogin;
    private String userId;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email);
        password = findViewById((R.id.password));
        mlogin = findViewById((R.id.login));

        fAuth = FirebaseAuth.getInstance();

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                final String flag = emails.substring(0,8);
                final String testing = "security";
                //flag = emails.substring(0,8);
                if(TextUtils.isEmpty(emails))
                {
                    email.setError("wrong");
                    return ;
                }

                if(TextUtils.isEmpty(pass))
                {
                    password.setError("password is required");
                    return ;
                }

                fAuth.signInWithEmailAndPassword(emails , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(login.this, "login full", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(login.this, "login succes", Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            Log.e("jenef","user id = " + userId);


                            //Intent intent = new Intent(current_location, )

                            //startActivity(new Intent(getApplicationContext() , current_location.class));
//                            String flag;
//                            flag = emails.substring(0,8);
//


                            int len = flag.length();

                            int len2 = testing.length();


                            boolean ck = flag.equals(testing);

                            Log.e("ajay_rand" , flag + " " +  len + " " + testing + " " + len2 + ck );

                            if(ck)
                            {
                                Log.e("goingin" , "itsinssinde");
                                Intent intent = new Intent(login.this , securer.class);
                                intent .putExtra("userId" , userId);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(login.this , MainActivity.class);
                                intent .putExtra("userId" , userId);
                                startActivity(intent);
                            }




                        }else
                        {
                            Toast.makeText(login.this, "error"  + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

    }
}

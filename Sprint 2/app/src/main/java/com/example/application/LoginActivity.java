package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

// Users will log in/sign up if they do not already done so
public class LoginActivity extends AppCompatActivity {

    // Firebase credentials
    Button btn_login;
    Button btn_signup;
    FirebaseAuth fAuth;
    EditText mEmail,mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.Login_button);
        btn_signup = (Button) findViewById(R.id.Signup_button);
        mEmail = findViewById(R.id.text_userid);
        mPassword = findViewById(R.id.text_userPass);
        fAuth = FirebaseAuth.getInstance();

        // Firebase Sign-in
        // Log in with user's own credential and store them in Firebase
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Log in successful
                            Toast.makeText(LoginActivity.this,"Log in successfully",Toast.LENGTH_SHORT).show();
                            // Go to RoleSelection
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, RoleSelection.class);
                            startActivity(intent);
                            finish();

                        }else{
                            // Log in failed
                            Toast.makeText(LoginActivity.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();;
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }
                });
            }
        });
        // Go to sign up page if users don't have an account
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SignUpPage.class);
                startActivity(intent);
            }
        });


    } // End of onCreate() Method


}
package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.*;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btn_login = null;
    Button btn_signup;
    RadioGroup radioGroup = null;
    FirebaseAuth fAuth;
    EditText mEmail,mPassword;

    SignInButton googleSignInButton;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        radioGroup = findViewById(R.id.radioButton);
        btn_login = (Button) findViewById(R.id.Login_button);
        btn_signup = (Button) findViewById(R.id.Signup_button);
        mEmail = findViewById(R.id.text_userid);
        mPassword = findViewById(R.id.text_userPass);
        fAuth = FirebaseAuth.getInstance();

        //Log in with checking firebase
        btn_login.setOnClickListener(new OnClickListener() {
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
                            Toast.makeText(MainActivity.this,"Please select system",Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(MainActivity.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();;
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }
                });
            }
        });


        //sign in page jump to sign up page
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignUpPage.class);
                startActivity(intent);
            }
        });
        //system select and jump to different page
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Patient:
                        btn_login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckPatientAccount();
                            }
                        });
                        break;
                    case R.id.Caretaker:
                        btn_login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckCaretakerAccount();
                            }
                        });
                        break;
                }
            }
        });

        // Google sign-in activity
        googleSignInButton = findViewById(R.id.sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                }
            }
        });
        // Configure Google sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    protected void CheckCaretakerAccount(){
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
                    Toast.makeText(MainActivity.this,"Log In Successfully",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CalenderActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();;
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
            }
        });
    }
    protected void CheckPatientAccount(){
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
                    Toast.makeText(MainActivity.this,"Log In Successfully",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, IncomingActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();;
                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    // Method to handle Google sign-in result, success or failure
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // *Have not added role case yet
            // Signed in successfully, start calender activity
            Intent intent = new Intent(MainActivity.this, CalenderActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
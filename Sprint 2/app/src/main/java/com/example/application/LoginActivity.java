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
    Button btn_login = null;
    Button btn_signup;
    //RadioGroup radioGroup = null;
    FirebaseAuth fAuth;
    EditText mEmail,mPassword;

    // Google credentials
    SignInButton googleSignInButton;
    GoogleSignInClient googleSignInClient;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //radioGroup = findViewById(R.id.radioButton);
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
                            Toast.makeText(LoginActivity.this,"Log in successfully",Toast.LENGTH_SHORT).show();
                            // Go to RoleSelection
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, RoleSelection.class);
                            startActivity(intent);


                        }else{
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
        // Role select and go to the corresponding page
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.Patient:
//                        btn_login.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                CheckPatientAccount();
//                                //myApplication.setRolePatient();
//                            }
//                        });
//                        googleSignInButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Initialize sign in intent
//                                Intent intent = googleSignInClient.getSignInIntent();
//                                // Start activity for result
//                                startActivityForResult(intent,100);
//                            }
//                        });
//                        break;
//                    case R.id.Caretaker:
//                        btn_login.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                CheckCaretakerAccount();
//                                //myApplication.setRoleCaretaker();
//                            }
//                        });
//                        googleSignInButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Initialize sign in intent
//                                Intent intent = googleSignInClient.getSignInIntent();
//                                // Start activity for result
//                                startActivityForResult(intent,100);
//                            }
//                        });
//                        break;
//                }
//            }
//        });

        // Google Sign-in
        // Reference: Geeksforgeeks
        googleSignInButton = findViewById(R.id.sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        // Initialize sign in options
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("631126193882-14rt6kbc9d4ecinmmmtgj0omtmefcuo0.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Initialize sign in client
        googleSignInClient= GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);



    } // End of onCreate() Method

    // Sign user in if they are caretaker
//    protected void CheckCaretakerAccount(){
//        String email = mEmail.getText().toString().trim();
//        String password = mPassword.getText().toString().trim();
//
//        if(TextUtils.isEmpty(email)){
//            mEmail.setError("Email is Required.");
//            return;
//        }
//        if(TextUtils.isEmpty(password)){
//            mPassword.setError("Password is Required");
//            return;
//        }
//        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(LoginActivity.this,"Log In Successfully",Toast.LENGTH_SHORT).show();
//                    //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, CalenderActivity.class);
//                    startActivity(intent);
//
//                }else{
//                    Toast.makeText(LoginActivity.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();;
//                    //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//
//                }
//            }
//        });
//    }
    // Sign user in if they are patient
//    protected void CheckPatientAccount(){
//        String email = mEmail.getText().toString().trim();
//        String password = mPassword.getText().toString().trim();
//
//        if(TextUtils.isEmpty(email)){
//            mEmail.setError("Email is Required.");
//            return;
//        }
//        if(TextUtils.isEmpty(password)){
//            mPassword.setError("Password is Required");
//            return;
//        }
//        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(LoginActivity.this,"Log In Successfully",Toast.LENGTH_SHORT).show();
//                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, IncomingActivity.class);
//                    startActivity(intent);
//
//                }else{
//                    Toast.makeText(LoginActivity.this,"Error! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();;
//                    //startActivity(new Intent(getApplicationContext(),MainActivity.class));
//
//                }
//            }
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        //FirebaseUser currentUser = fAuth.getCurrentUser();
    }

    // Google sign-in result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        {
            // When request code is equal to 100
            // Initialize task
            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            // check condition
            if(signInAccountTask.isSuccessful())
            {
                // When google sign in successful
                // Initialize string
                String s="Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount=signInAccountTask
                            .getResult(ApiException.class);
                    // Check condition
                    if(googleSignInAccount != null)
                    {
                        // When sign in account is not equal to null
                        // Initialize auth credential
                        AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(),null);
                        // Check credential
                        fAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check condition
                                        if(task.isSuccessful())
                                        {
                                            // When task is successful
                                            Intent intent = new Intent();
                                            intent.setClass(LoginActivity.this, RoleSelection.class);
                                            startActivity(intent);
                                            // Display Toast
                                            displayToast("Firebase authentication successful");
                                        }
                                        else
                                        {
                                            // When task is unsuccessful
                                            // Display Toast
                                            displayToast("Authentication Failed :"+task.getException()
                                                    .getMessage());
                                        }
                                    }
                                });

                    }
                }
                catch (ApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }



}
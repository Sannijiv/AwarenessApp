package com.example.sanjiv.awarenessapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "LOGIN";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseDatabase mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText emailInput, passwordInput;
    private Button normalLogin, googleLogin, confirmLogin;
    private TextView passwordForgot, registerAccount, toLogin;


    private ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        normalLogin = findViewById(R.id.btnEmployee);
        googleLogin = findViewById(R.id.btnGuest);
        emailInput = findViewById(R.id.loginUsername);
        passwordInput = findViewById(R.id.loginPassword);
        confirmLogin = findViewById(R.id.btnConfirm);
        passwordForgot= findViewById(R.id.forgotPassword);
        registerAccount = findViewById(R.id.registerAccount);
        toLogin = findViewById(R.id.toLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        //Auto generated
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.CLIENT_TAG_ID))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        normalLogin.setOnClickListener(this);
        googleLogin.setOnClickListener(this);

        registerAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        passwordForgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPage.class);
                startActivity(intent);
            }
        });


        toLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }
        });

    }

    //Check bij start of gebruiker al is ingelogd
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    //Login voor google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        progressDialog.setMessage("Bezig met inloggen");
        progressDialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            
                            UserModel userModel = new UserModel("admin");
                            mDatabase.getReference("users").child(user.getUid()).child("rollen").setValue(userModel);
                            finish();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            Toast.makeText(Login.this,"Welkom!", Toast.LENGTH_SHORT).show();


                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this,"Auth failed", Toast.LENGTH_SHORT).show();

                        }

                        progressDialog.dismiss();
                        // ...
                    }
                });

    }

    public AlertDialog employeeCheckWithDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);

        final EditText edittext = new EditText(Login.this);
        alert.setMessage("Voer de employee bevestiging code in");
        alert.setTitle("Employee Code");

        alert.setView(edittext);

        alert.setPositiveButton("Bevestigen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String code = edittext.getText().toString();
                if(code.equalsIgnoreCase("123")){
                    enableEmployeeLogin();
                }
            }
        });

        alert.setNegativeButton("Annuleren", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        return alert.create();

    }

    private void enableEmployeeLogin(){
        emailInput.setVisibility(View.VISIBLE);
        passwordInput.setVisibility(View.VISIBLE);
        confirmLogin.setVisibility(View.VISIBLE);
        normalLogin.setVisibility(View.GONE);
        googleLogin.setVisibility(View.GONE);
        passwordForgot.setVisibility(View.VISIBLE);
        registerAccount.setVisibility(View.VISIBLE);
        toLogin.setVisibility(View.VISIBLE);

    }

    private void signInNormal() {

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Emailadres veld is leeg !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Wachtwoord veld is leeg !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Wachtwoord is minimaal 6 tekens!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Bezig met inloggen");
        progressDialog.show();


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(Login.this, "Welkom!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Inloggen mislukt, check uw gegevens!", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == normalLogin) {
           AlertDialog dialog = employeeCheckWithDialog();
           dialog.show();
        }

        if (v == googleLogin) {
            signIn();
        }

        if(v == confirmLogin){
            signInNormal();

        }
    }
}

package com.jorch.proyecto.firechat.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jorch.proyecto.firechat.R;
import com.jorch.proyecto.firechat.dialogs.RegistrationDialog;
import com.jorch.proyecto.firechat.model.User;
import com.jorch.proyecto.firechat.utils.EncryptHelper;
import com.jorch.proyecto.firechat.utils.FirebaseHelper;
import com.jorch.proyecto.firechat.utils.UserHelper;

public class Login extends AppCompatActivity{
    private FirebaseAuth.AuthStateListener stateListener;
    private Button bttnSignIn, bttnSignUp;
    private EditText etNickname, etPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bttnSignIn = (Button) findViewById(R.id.bttn_Login_signin);
        bttnSignUp = (Button) findViewById(R.id.bttn_Login_signup);
        etNickname = (EditText) findViewById(R.id.et_Login_nickname);
        etPassword = (EditText) findViewById(R.id.et_Login_password);
        etNickname.requestFocus();
        progressDialog = new ProgressDialog(this);

        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseHelper.getCurrentUser()!= null) {
                    FirebaseHelper.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                if (user.getEmail().equals(FirebaseHelper.getCurrentUser().getEmail())){
                                    UserHelper.setStatus(user.getNickname(),true);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                    startActivity(new Intent(getApplicationContext(), GroupsChat.class));
                }
            }
        };

        bttnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });
        bttnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(new RegistrationDialog(),"REGISTRATION_DIALOG").commit();
            }
        });
    }

    private void signInUser() {
        progressDialog.setTitle("LOGGING IN");
        progressDialog.show();
        final String sNickname, sPassword, sEncryptedPassword;
        sNickname = etNickname.getText().toString();
        sPassword = etPassword.getText().toString();
        sEncryptedPassword = EncryptHelper.encryptMesssage(sPassword);
        DatabaseReference reference = FirebaseHelper.getReference("users");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sEmail = "";
                User user;
                for (DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    user = snapshot.getValue(User.class);
                    if (user.getNickname().equals(sNickname)){
                        sEmail = user.getEmail();
                        break;
                    }
                }
                if (sEmail!=""){
                    FirebaseHelper.getAuth()
                            .signInWithEmailAndPassword(sEmail,sEncryptedPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(),GroupsChat.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("ERROR_AUTHENTICATION",e.getMessage());
                            progressDialog.dismiss();
                            if (e.getMessage().equals("The password is invalid or the user does not have a password.")){
                                Toast.makeText(getApplicationContext(),
                                        "The password is invalid.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Username and/or email unknown\nPlease try again",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseHelper.getAuth().addAuthStateListener(stateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseHelper.getAuth().removeAuthStateListener(stateListener);
    }
}

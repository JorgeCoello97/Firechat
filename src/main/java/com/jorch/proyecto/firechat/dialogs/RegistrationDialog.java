package com.jorch.proyecto.firechat.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jorch.proyecto.firechat.R;
import com.jorch.proyecto.firechat.model.User;
import com.jorch.proyecto.firechat.utils.EncryptHelper;
import com.jorch.proyecto.firechat.utils.FirebaseHelper;

import java.util.Calendar;

/**
 * Created by JORCH on 18/03/2017.
 */

public class RegistrationDialog extends DialogFragment {
    private EditText etNickname, etEmail, etPassword, etConfirmPassword;
    private Button bttnSignUp;
    private ProgressDialog progressDialog;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_registration,null);
        builder.setView(view);
        progressDialog = new ProgressDialog(getContext());

        etNickname = (EditText) view.findViewById(R.id.et_Registration_nickname);
        etEmail = (EditText) view.findViewById(R.id.et_Registration_email);
        etPassword = (EditText) view.findViewById(R.id.et_Registration_password);
        etConfirmPassword = (EditText) view.findViewById(R.id.et_Registration_confirmpassword);
        bttnSignUp = (Button) view.findViewById(R.id.bttn_Registration_signup);

        bttnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
        return builder.create();
    }

    private void registerNewUser() {
        final String sNickname, sEmail, sPassword, sConfirmPassword;
        sNickname = etNickname.getText().toString();
        sEmail = etEmail.getText().toString();
        sPassword = etPassword.getText().toString();
        sConfirmPassword = etConfirmPassword.getText().toString();

        if (sNickname.isEmpty()){
            etNickname.requestFocus();
            etNickname.setError("Introduce a nickname.");
            return;
        }
        if (sEmail.isEmpty()){
            etEmail.requestFocus();
            etEmail.setError("Introduce an email.");
            return;
        }
        if (sPassword.isEmpty()){
            etPassword.requestFocus();
            etPassword.setError("Introduce a password.");
            return;
        }
        if (sConfirmPassword.isEmpty()){
            etConfirmPassword.requestFocus();
            etConfirmPassword.setError("Introduce the same password that above.");
            return;
        }
        if (!sConfirmPassword.equals(sPassword)){
            etPassword.setText("");
            etConfirmPassword.setText("");
            etPassword.requestFocus();
            etPassword.setError("Introduce the password and the confirm password");
            return;
        }
        progressDialog.setTitle("LOGIN UP");
        progressDialog.show();
        FirebaseHelper.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataUser:dataSnapshot.getChildren()) {
                    User user = dataUser.getValue(User.class);
                    if (user.getNickname().equals(sNickname)){
                        Toast.makeText(getActivity().getApplicationContext(),"The nickname already exists.\nTry again.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                final String sEncryptPassword = EncryptHelper.encryptMesssage(sPassword);
                createNewUser(sNickname,sEmail,sEncryptPassword);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createNewUser(final String sNickname, final String sEmail, final String sPassword) {

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Calendar calendar = Calendar.getInstance();
                            String dateCreation = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" +calendar.get(Calendar.YEAR);
                            User user = new User(sNickname,sEmail,sPassword,dateCreation,false);
                            FirebaseHelper.getReference("users").push().setValue(user);
                            dismiss();
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Account created successful.",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR_DATABASE",e.getMessage()+" "+e.toString()+" "+" "+e.getLocalizedMessage());
                        progressDialog.dismiss();
                        if (e.getMessage().equals("The email address is badly formatted.")){
                            etEmail.setText("");
                            etPassword.setText("");
                            etConfirmPassword.setText("");
                            etEmail.requestFocus();
                            Toast.makeText(getContext(),
                                    "Introduce a correct email address.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

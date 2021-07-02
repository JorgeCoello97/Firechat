package com.jorch.proyecto.firechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jorch.proyecto.firechat.ui.GroupsChat;
import com.jorch.proyecto.firechat.ui.Login;
import com.jorch.proyecto.firechat.utils.FirebaseHelper;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUser = FirebaseHelper.getCurrentUser();
        if (firebaseUser != null) {
            finish();
            //TODO falta por hacer lista de chats del usuario logeado.
            startActivity(new Intent(this, GroupsChat.class));
        }else{
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }
}

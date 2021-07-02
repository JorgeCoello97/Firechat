package com.jorch.proyecto.firechat.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jorch.proyecto.firechat.model.User;

/**
 * Created by JORCH on 20/03/2017.
 */

public class UserHelper {

    public static void setStatus(final String nickname, final Boolean status){
        FirebaseHelper.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    String userNickname = snapshot.child("nickname").getValue(String.class);
                    if (userNickname.equals(nickname)){
                        String keyUser = snapshot.getKey();
                        FirebaseHelper.getReference("users").child(keyUser).child("status").setValue(status);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

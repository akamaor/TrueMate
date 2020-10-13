package com.aw.truemate;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.Map;

public class test {

    public DocumentReference  mFirestore=FirebaseFirestore.getInstance().document("users/8sVeX7Nsf5fIxSskq0el");
    public CollectionReference myfirestore = FirebaseFirestore.getInstance().collection("users");

    public void run() {

        String x="aka";
        String y="maor";
        Map<String,Object> datatosave = new HashMap<String,Object>();
        datatosave.put("username",x);
        datatosave.put("phone",y);
        datatosave.put("mass",null);
        //update
        mFirestore.set(datatosave);
        //add new
        myfirestore.add(datatosave);


    }

    public void run1(){
        DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
        DatabaseReference  usr = DB.child("users/888u4ExM9w2jiWdCGNtE");
        Map<String,Object> datatosave = new HashMap<String,Object>();
        datatosave.put("username","x");
        datatosave.put("phone","y");
        datatosave.put("mass",null);
        usr.setValue(datatosave);

    }

    protected void update(String user_id, String filed_name, String value) {

    }
}


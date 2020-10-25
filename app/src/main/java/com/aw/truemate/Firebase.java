package com.aw.truemate;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

public class Firebase {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public void updateCollection(String collectionName, String documentKey, Map<String, Object> details) {
        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" +documentKey);
        document.set(details); // if document key don't exist- create new one. else overwrite
    }

    public String getUid(){
        return mAuth.getCurrentUser().getUid();
    }


    public void readCollection(String collectionName, final String field, String userId, final FirebaseCallback fbCallback){
        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" +userId);
        final Task<DocumentSnapshot> source = document.get();
        source.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Object value = source.getResult().get(field);
                    fbCallback.onCallback(value);
                }
            }
        });
    }

    public void updateFieldInDocument(String collectionName, String documentKey, String field, HashMap<Object, Object>[] fieldValueMap){
        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" + documentKey);
        document.update(field, fieldValueMap[0]);
    }

//    public Object getFieldInDocument(String collectionName, String documentKey, String field){
//        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" + documentKey + "/" + field);
//        Object res = document.get();
//        return res;
//    }

    public Task<QuerySnapshot> getAllDocumentFromCollection(String collectionName){
        Task<QuerySnapshot> collection = FirebaseFirestore.getInstance().collection(collectionName).get();
//        CollectionReference collection = FirebaseFirestore.getInstance().collection(collectionName);
        return collection;
    }
}

package com.aw.truemate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Source;

import java.util.Map;
import java.lang.*;

public class Firebase {
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    public void updateCollection(String collectionName, String documentKey, Map<String, Object> details) {
        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" +documentKey);
        document.set(details); // if document key don't exist- create new one. else overwrite
    }

    public Object readCollection(String collectionName,String detail, String userId){
        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" +userId);
        Task<DocumentSnapshot> s = document.get();
        return s.getResult().get(detail);
    }

    public String getUid(){
        return mAuth.getCurrentUser().getUid();
    }
}

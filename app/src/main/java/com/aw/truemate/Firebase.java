package com.aw.truemate;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Firebase {
    public void updateCollection(String collectionName, String documentKey, Map<String, Object> details) {
        DocumentReference document = FirebaseFirestore.getInstance().document(collectionName + "/" +details.get("user_id"));
        document.set(details); // if document key don't exist- create new one. else override
    }

}

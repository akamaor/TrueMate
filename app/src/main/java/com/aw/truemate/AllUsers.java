package com.aw.truemate;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class AllUsers {

    private static HashMap<String, userDetails> allUsers = new HashMap<>();

    public static HashMap<String, userDetails> convertTaskToUserDetailsList(Task<QuerySnapshot> usersFromFirebase) {
        for(QueryDocumentSnapshot document : usersFromFirebase.getResult()) {
            userDetails user = new userDetails(
                    document.get("user_id"),
                    document.get("name"),
                    document.get("email"),
                    document.get("gender"),
                    document.get("age"),
                    document.get("city"),
                    document.get("neighborhood"),
                    document.get("roommate_number"),
                    document.get("liked_list"));

            allUsers.put((String)document.get("user_id"), user);
        }
        return allUsers;
    }
}

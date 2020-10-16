package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okio.Timeout;

public class SwipeActivity extends AppCompatActivity {

    ImageView userImage;
    ListView userNeighborhoods;
    TextView userName, roommateNumber, roommateText;
    ImageButton likeButton, dislikeButton;
//    LinkedHashMap<String, userDetails> allUsersList = new LinkedHashMap<>();
    Task<QuerySnapshot> usersFromFirebase;
    HashMap<String, userDetails> allUsers = new HashMap<>();
    Firebase fb = new Firebase();
    String userId = fb.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAllUsersFromDB();

        convertTaskToUserDetailsList();
        deleteCurrentUserFromList();
        final Iterator<userDetails> iterator = allUsers.values().iterator();
        updateActivityContent(iterator);

        setContentView(R.layout.activity_swipe);

        roommateText = findViewById(R.id.roommateText);
        likeButton = findViewById(R.id.like);
        dislikeButton = findViewById(R.id.dislike);


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLikedItemToList();
                updateActivityContent(iterator);
            }
        });

        dislikeButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateActivityContent(iterator);
            }
        });
    }


    private synchronized void updateActivityContent(Iterator<userDetails> iterator) {
        userDetails userDisplay = getNextUser(iterator);
        if(userDisplay == null) {
            Toast.makeText(SwipeActivity.this, "No more users", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SwipeActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            userName.setText(userDisplay.getName());
//            todo: update image!!! userImage.setImage(userDisplay.getImage());
            List<String> neighborhoodList = userDisplay.getNeighborhood();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, neighborhoodList);
            userNeighborhoods.setAdapter(arrayAdapter);
            roommateNumber.setText(userDisplay.getRoommate_number());
        }
    }

    private userDetails getNextUser(Iterator<userDetails> iterator) {
        userDetails nextUser = null;
        if(iterator.hasNext())
            nextUser = iterator.next();
        return nextUser;
    }

    private void addLikedItemToList() {
        List<Object> userLikedList = (List<Object>)fb.readCollection("users", "likedList", userId);
        userDetails likedUser = allUsers.get(userId);
        userLikedList.add(likedUser.getUser_id());
        fb.updateFieldInDocument("users", userId, "liked_list", userLikedList);
    }

    private void getAllUsersFromDB() {
        Task<QuerySnapshot> cr = fb.getAllDocumentFromCollection("users");
        cr.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    usersFromFirebase = task;
//                    try {
//                        SwipeActivity.this.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    Log.d("Error!", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private synchronized void convertTaskToUserDetailsList() {
        for(QueryDocumentSnapshot document : usersFromFirebase.getResult()) {
            userDetails user = new userDetails(
                    document.get("user_id"),
                    document.get("user_email"),
                    document.get("name"),
                    document.get("gender"),
                    document.get("age"),
                    document.get("city"),
                    document.get("neighborhood"),
                    document.get("roommate_number"));

            allUsers.put((String)document.get("user_id"), user);
        }
    }

    private synchronized void deleteCurrentUserFromList() {
        allUsers.remove(userId);
    }
}
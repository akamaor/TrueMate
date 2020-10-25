package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.*;

import com.google.firebase.firestore.*;

import java.util.*;

public class SwipeActivity extends AppCompatActivity {

    ImageView userImage;
    ListView userNeighborhoods;
    TextView userName;
    TextView roommateNumber;
    TextView roommateText;
    ImageButton likeButton ;
    ImageButton dislikeButton;
    Task<QuerySnapshot> usersFromFirebase;
    HashMap<String, userDetails> allUsers = new HashMap<>();
    Firebase fb = new Firebase();
    String userId = fb.getUid();
    Iterator<userDetails> iterator;
    String displayUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipe);

        userImage = (ImageView) findViewById(R.id.imageView);
        userNeighborhoods = (ListView) findViewById(R.id.neighborhoodList);
        userName = (TextView) findViewById(R.id.userName);
        roommateNumber = (TextView) findViewById(R.id.roommateNumber);
        roommateText = (TextView) findViewById(R.id.roommateText);
        likeButton = (ImageButton) findViewById(R.id.like);
        dislikeButton = (ImageButton) findViewById(R.id.dislike);

        getAllUsersFromDBAndShowingUsers();


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLikedItemToListAndUpdateActivityContent();
            }
        });

        dislikeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateActivityContent(iterator);
            }
        });
    }

    private void startConvertDataFromDBAndShowingUsers() {
        convertTaskToUserDetailsList();
        deleteCurrentUserFromList();
        initializeIterator();
        showingData();
    }

    private void initializeIterator() {
        iterator = allUsers.values().iterator();
    }

    private void showingData() {
        updateActivityContent(iterator);
    }

    private void updateActivityContent(Iterator<userDetails> iterator) {
        userDetails userDisplay = getNextUser(iterator);

        if(userDisplay == null) {
            Toast.makeText(SwipeActivity.this, "No more users", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SwipeActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            displayUserID = userDisplay.getUser_id();
            userName.setText(userDisplay.getName());
//            todo: update image!!! userImage.setImage(userDisplay.getImage());
//            userImage.setImageURI().setImageIcon(new Icon);set.setImage(userDisplay.getImage());
            List<String> neighborhoodList = userDisplay.getNeighborhood();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, neighborhoodList);
            userNeighborhoods.setAdapter(arrayAdapter);
            roommateNumber.setText((String)userDisplay.getRoommate_number());
        }
    }

    private userDetails getNextUser(Iterator<userDetails> iterator) {
        userDetails nextUser = null;
        if(iterator.hasNext())
            nextUser = iterator.next();
        return nextUser;
    }

    private void addLikedItemToListAndUpdateActivityContent() {
//        final HashMap<Object, Object>[] userLikedList = new HashMap[]{new HashMap<>()};
        final List<Object>[] userLikedListID = new List[]{new ArrayList<>()};
        fb.readCollection("users", "liked_list", userId, new FirebaseCallback() {
            @Override
            public void onCallback(Object obj) {
                userLikedListID[0] = ((List<Object>) obj);
                userDetails likedUser = allUsers.get(displayUserID);
                userLikedListID[0].add(likedUser.getUser_id());
                fb.updateFieldInDocument("users", userId, "liked_list", userLikedListID[0]);
                updateActivityContent(iterator);
            }
        });
    }

    private void getAllUsersFromDBAndShowingUsers(){
        final Task<QuerySnapshot> allDocuments = fb.getAllDocumentFromCollection("users");
        allDocuments.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    usersFromFirebase = task;
                    startConvertDataFromDBAndShowingUsers();
                } else {
                    Log.d("Error!", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void convertTaskToUserDetailsList() {
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
    }

    private void deleteCurrentUserFromList() {
        allUsers.remove(userId);
    }
}
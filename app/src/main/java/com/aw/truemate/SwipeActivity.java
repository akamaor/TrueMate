package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SwipeActivity extends AppCompatActivity {

    ImageView userImage;
    ListView userNeighborhoods;
    TextView userName;
    TextView roommateNumber;
    TextView roommateText;
    ImageButton likeButton ;
    ImageButton dislikeButton;
//    LinkedHashMap<String, userDetails> allUsersList = new LinkedHashMap<>();
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

        getAllUsersFromDB();
//        startConvertDataFromDB();

//        convertTaskToUserDetailsList();
//        deleteCurrentUserFromList();
//        final Iterator<userDetails> iterator = allUsers.values().iterator();
//        updateActivityContent(iterator);


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLikedItemToListAndUpdateActivityContent();
//                updateActivityContent(iterator);
            }
        });

        dislikeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateActivityContent(iterator);
            }
        });
    }

    private void startConvertDataFromDB() {
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
//        final List<Object>[] userLikedList = new List[]{new LinkedList<>()};
//        final List<Object> userLikedList = new LinkedList<>();
        final HashMap<Object, Object>[] userLikedList = new HashMap[]{new HashMap<>()};
        fb.readCollection("users", "liked_list", userId, new FirebaseCallback() {
            @Override
            public void onCallback(Object obj) {
//                userLikedList.add(obj);

                userLikedList[0] = (HashMap<Object, Object>) obj;
                userDetails likedUser = allUsers.get(displayUserID);
                userLikedList[0].put(likedUser.getUser_id(), likedUser);
//                Map<String, Object> likedListMap = new HashMap<>();
//                likedListMap.put("liked_list", userLikedList[0]);
//                fb.updateFieldInDocument("users", userId, likedListMap);

                fb.updateFieldInDocument("users", userId, "liked_list", userLikedList);
                updateActivityContent(iterator);
            }
        });
    }

    private void getAllUsersFromDB(){
        final Task<QuerySnapshot> cr = fb.getAllDocumentFromCollection("users");
//        while (!cr.isComplete()){
//        }
//        usersFromFirebase = cr;

        cr.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    usersFromFirebase = task;
                    startConvertDataFromDB();

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
                    document.get("roommate_number"));

            allUsers.put((String)document.get("user_id"), user);
        }
    }

    private void deleteCurrentUserFromList() {
        allUsers.remove(userId);
    }
}
package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.List;

public class QuestionsActivity<DB> extends AppCompatActivity {
    //layout
    EditText editName,editAge,editGender,editRoommate,editNeighborhood,editCity;
    Button buttonUpdate;
    List<String> editLikedList;

    //Firebase database
    /*public DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference  userdb = DB.child("users");*/
    Firebase DB = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);

        //create userID
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid();
        final String email = mAuth.getCurrentUser().getEmail();
        //create variable for the layout
        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);
        editGender = (EditText) findViewById(R.id.editGender);
        editRoommate = (EditText) findViewById(R.id.editRoommate);
        editNeighborhood = (EditText) findViewById(R.id.editNeighborhood);
        editCity = (EditText) findViewById(R.id.editCity);


        FirebaseFirestore FB=FirebaseFirestore.getInstance();
        DocumentReference docRef = FB.collection("users").document(userID);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        editName.setText(document.getString("name"));
                        editAge.setText(document.getString("age"));
                        editGender.setText(document.getString("gender"));
                        editCity.setText(document.getString("city"));
                        editNeighborhood.setText(document.getString("neighborhood"));
                        editRoommate.setText(document.getString("roommate_number"));
                        editLikedList = (List<String>) document.get("liked_list");
                    } else {//no doc
                    }
                } else {//fail somehow
                }
            }
        });
        //editName.setText("234");



        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

        //Clicking on Update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //the Firebase doesn't work well
                                                final userDetails uDetails = new userDetails(
                                                        userID,
                                                        editName.getText().toString(),
                                                        email,
                                                        editGender.getText().toString(),
                                                        editAge.getText().toString(),
                                                        editCity.getText().toString(),
                                                        editNeighborhood.getText().toString(),
                                                        editRoommate.getText().toString(),
                                                        editLikedList
                                                        );
                                                DB.updateCollection("users", userID, uDetails.toMap());
                                                Toast.makeText(QuestionsActivity.this,"Update complete!",Toast.LENGTH_SHORT).show();
                                                Intent intToMain = new Intent(QuestionsActivity.this, HomeActivity.class);
                                                startActivity(intToMain);
                                            }
                                        }
        );
    }
}
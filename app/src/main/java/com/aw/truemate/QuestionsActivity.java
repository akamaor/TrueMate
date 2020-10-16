package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuestionsActivity<DB> extends AppCompatActivity {
    //layout
    EditText editName,editAge,editGender,editRoommate,editNeighborhood,editCity;
    Button buttonUpdate;

    //Firebase database
    /*public DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference  userdb = DB.child("users");*/
    Firebase DB = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);

        //create variable for the layout
        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);
        editGender = (EditText) findViewById(R.id.editGender);
        editRoommate = (EditText) findViewById(R.id.editRoommate);
        editNeighborhood = (EditText) findViewById(R.id.editNeighborhood);
        editCity = (EditText) findViewById(R.id.editCity);

        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

        //Clicking on Update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                final userDetails uDetails = new userDetails("888u4ExM9w2jiWdCGNtE",editName.getText().toString(),editGender.getText().toString(),editAge.getText().toString(),editCity.getText().toString(),editNeighborhood.getText().toString(),editRoommate.getText().toString());
//                DB.updateCollection("users","anydocumentKey",uDetails.toMap());

                /*
                userdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //update action

                            userdb.child(uDetails.getUser_id()).setValue(editName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        }

        );


    }
}
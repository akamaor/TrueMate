package com.aw.truemate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionsActivity1<DB> extends AppCompatActivity {
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
        Object userName = DB.readCollection("users","name",DB.getUid());
        if(userName != null) {
            editName.setText(userName.toString());
        }

        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

        //Clicking on Update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //
                                                final userDetails uDetails = new userDetails("akamaor@gmail.com",editName.getText().toString(),editGender.getText().toString(),editAge.getText().toString(),editCity.getText().toString(),editNeighborhood.getText().toString(),editRoommate.getText().toString());
                                                DB.updateCollection(DB.getUid(),"anydocumentKey",uDetails.toMap());

                                                Toast.makeText(QuestionsActivity1.this,"Update complete!",Toast.LENGTH_SHORT).show();
                                            }
                                        }

        );



    }
}
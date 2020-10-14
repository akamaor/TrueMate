package com.aw.truemate;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionsActivity extends AppCompatActivity {
    //layout
    EditText editName,editAge,editGender,editRoommate,editNeighborhood,editCity;
    Button buttonUpdate;
    //Firebase database
    Firebase DB = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //create variable for the layout
        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);
        editGender = (EditText) findViewById(R.id.editGender);
        editRoommate = (EditText) findViewById(R.id.editRoommate);
        editNeighborhood = (EditText) findViewById(R.id.editNeighborhood);
        editCity = (EditText) findViewById(R.id.editCity);
        Object DBName = DB.readCollection("users","name",DB.getUid());
        if(DBName != null) {
            editName.setText(DBName.toString());
        }

        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);

        //Clicking on Update button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                //
                final userDetails uDetails = new userDetails(DB.getUid(),editName.getText().toString(),editGender.getText().toString(),editAge.getText().toString(),editCity.getText().toString(),editNeighborhood.getText().toString(),editRoommate.getText().toString());
                DB.updateCollection(DB.getUid(),"anydocumentKey",uDetails.toMap());

                Toast.makeText(QuestionsActivity.this,"Update complete!",Toast.LENGTH_SHORT).show();
            }
        }

        );

    }

}
package com.aw.truemate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
//this activity folliw the case of successful registraion.
public class HomeActivity extends AppCompatActivity {

    Button btnLogout;
    Button btnSwipe;
    Button btnUpdate;
    Button btnMylist;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSwipe=findViewById(R.id.swipeButton);
        btnLogout = findViewById(R.id.logoutButton);
        btnUpdate = findViewById(R.id.updateButton);
        btnMylist=findViewById(R.id.mylistButton);

 //swiping
        btnSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(i);
            }
        });

//loging out and returning to main activity.
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        //update details button pressed
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        //My list button pressed
        btnMylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    //Edit info button direct to questions activity
    public void moveToQuestionActivity(View view) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        startActivity(intent);
    }
}

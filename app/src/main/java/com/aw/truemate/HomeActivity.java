package com.aw.truemate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
//this activity folliw the case of successful registraion.
public class HomeActivity extends AppCompatActivity {

    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout = findViewById(R.id.logout);

        //loging out and returning to main activity.

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

    }

    //Edit info button direct to questions activity
    public void moveToQuestionActivity(View view) {
        Intent intent = new Intent(this, QuestionsActivity1.class);
        startActivity(intent);
    }
}

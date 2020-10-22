package com.aw.truemate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
//todo:fixing the back button
public class MyListShowActivity extends AppCompatActivity {
    ImageView Image;
    TextView Title,Describtion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_show);

        Image = findViewById(R.id.image);
        Title = findViewById(R.id.title);
        Describtion = findViewById(R.id.describtion);
        //int position = 0;

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        //get all the data from my list activity
        Intent intent = getIntent();

        Bundle bundle = this.getIntent().getExtras();
        int pic = bundle.getInt("image");
        String aTitle = intent.getStringExtra("title");
        String aDescription = intent.getStringExtra("description");

        Image.setImageResource(pic);
        Title.setText(aTitle);
        Describtion.setText(aDescription);

        actionBar.setTitle(aTitle);
    }
}
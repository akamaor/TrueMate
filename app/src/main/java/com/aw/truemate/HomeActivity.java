package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

//this activity foll0w the case of successful registraion.
public class HomeActivity extends AppCompatActivity {

    Button btnLogout;
    Button btnSwipe;
    Button btnUpdate;
    Button btnMylist;
    private ImageView imageView;

    private StorageReference mStorageRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId=user.getUid();

    Uri imageUri = null;
    private static final int GALLERY_REQUEST = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSwipe=findViewById(R.id.swipeButton);
        btnLogout = findViewById(R.id.logoutButton);
        btnUpdate = findViewById(R.id.updateButton);
        btnMylist=findViewById(R.id.mylistButton);
        imageView=findViewById(R.id.imageView);

        mStorageRef = FirebaseStorage.getInstance().getReference();

 //swiping
        btnSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SwipeActivity.class);
                startActivity(i);
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
                Intent i = new Intent(HomeActivity.this, MyListActivity.class);
                startActivity(i);
            }
        });


//Set onclick listener on ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open image gallery method
                getImageFromGallery();
            }
        });
    }

    private void getImageFromGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check if the intent was to pick image, was successful and an image was picked
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadPhoto(imageUri);
        }
   }
//uploading imageUri to firebase storage. the photo is in dir: images/userId (authentication id)
    private void uploadPhoto(Uri imageUri) {

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();
        StorageReference riversRef = mStorageRef.child("images/"+userId);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent=(100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage: "+(int) progressPercent+"%");
                    }
                });

    }

//     //Edit info button direct to questions activity
//     public void moveToQuestionActivity(View view) {
//         Intent intent = new Intent(this, QuestionsActivity.class);
//         startActivity(intent);

//     }
}

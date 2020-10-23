package com.aw.truemate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyListActivity extends AppCompatActivity {
    ListView listView;
  //example text for the list

    // todo: making array of all the users
    //public String mTitle[] = {"Facebook", "WhatsApp", "Twitter", "Instagram", "Youtube"};
    //public ArrayList<String> mTitle = new ArrayList();
    //public String mDescription[] = {"Facebook Description", "WhatsApp Description", "Twitter Description", "Instagram Description", "Youtube Description"};

    public List<String> lLikedList =new ArrayList<String>();
    public List<String> lTitle =new ArrayList<String>();
    public List<String> lDescription =new ArrayList<String>();
    public int mimages[] = {0, 0,0,0,0,0,0};
    //mTitle.add();




    //create userID
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    final String userID = mAuth.getCurrentUser().getUid();
    Source source = Source.CACHE;
    public FirebaseFirestore FB=FirebaseFirestore.getInstance();

    public DatabaseReference mDatabase;
    public CollectionReference docRef = FB.collection("users");
    public Object liked_list;
/*
    //get user id-> liked ->
    String mTitle[]={};
    String mDescription[]={};
    int mimages[]={};
*/
public MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        //adding example date for the test
        lTitle.add("Facebook");
        lTitle.add("WhatsApp");
        lTitle.add("Twitter");
        lTitle.add("Instagram");
        lDescription.add("Facebook Description");
        lDescription.add("WhatsApp Description");
        lDescription.add("Twitter Description");
        lDescription.add("Instagram Description");

        listView = findViewById(R.id.listView);

        adapter = new MyListAdapter(this, lTitle, lDescription, mimages);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if( position != -1 ) {
                    Intent intent = new Intent(getApplicationContext(), MyListShowActivity.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", mimages[position]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", lTitle.get(position));
                    intent.putExtra("description", lDescription.get(position));
                    // also put your position
                    intent.putExtra("position", ""+position);
                    startActivity(intent);

                }
            }
        });


        //todo: after loading the data load the other data with this liked list data
        //todo: make the list visible && updating the data inside
        //Load data from DataBase

        FirebaseFirestore FB=FirebaseFirestore.getInstance();
        docRef.document(userID).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override

            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document != null) {
                        liked_list= document.get("liked_list");
                        Toast.makeText(MyListActivity.this,"finish mission",Toast.LENGTH_SHORT).show();
 /*                       for (int i=0;i<2;i++){
                            docRef.document(userID).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document2 = task.getResult();
                                        if (document2 != null) {
                                            lTitle.add(document2.get("name").toString() + " " + document2.get("age").toString());
                                            lDescription.add(document2.get("city").toString() + " " + document2.get("neighborhood").toString());
                                        }
                                    }
                                }
                            });
                        }*/
                        //update the adapter
                        adapter.notifyDataSetChanged();
                    } else {//no doc
                    }
                } else {//fail somehow
                }
            }
        });

/*                        //adding new data like would happend when we add th DB
                        lTitle.add("Youtube");
                        lDescription.add("Youtube Description");
                        //update the adapter
                        adapter.notifyDataSetChanged();*/

    }





    //adapter
        class MyListAdapter extends ArrayAdapter<String> {


            private final List<String> rTitle;
            private final Context context;
            private final List<String> rDescribtion;
            private final int[] rImgs;

            MyListAdapter(@NonNull Context context, List<String> title, List<String> describtion, int imgs[]) {
                super(context, R.layout.mylist_row, R.id.title, title);
                this.context = context;
                this.rTitle = title;
                this.rDescribtion = describtion;
                this.rImgs = imgs;

            }

            @Override
            @NonNull
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mylist_row = layoutInflater.inflate(R.layout.mylist_row, parent, false);
                ImageView images = mylist_row.findViewById(R.id.image);
                TextView mytitle = mylist_row.findViewById(R.id.title);
                TextView mysubtitle = mylist_row.findViewById(R.id.describtion);

                images.setImageResource(rImgs[position]);
                mytitle.setText(rTitle.get(position));
                mysubtitle.setText(rDescribtion.get(position));
                return mylist_row;
            }

        }
/*
      public void getLikedList(){
//tring to make data with holding
          docRef.document(userID).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override

              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()) {
                      DocumentSnapshot document = task.getResult();
                      if (document != null) {
                          liked_list= document.get("liked_list");
                      } else {//no doc
                      }
                  } else {//fail somehow
                  }
              }
          });
        Toast.makeText(MyListActivity.this,"finish mission",Toast.LENGTH_SHORT).show();
        //return liked_list;
    }*/



}





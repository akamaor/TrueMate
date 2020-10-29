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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyListActivity extends AppCompatActivity {
    ListView listView;
  //example text for the list

    // todo: making array of all the users
    //public String mTitle[] = {"Facebook", "WhatsApp", "Twitter", "Instagram", "Youtube"};
    //public ArrayList<String> mTitle = new ArrayList();
    //public String mDescription[] = {"Facebook Description", "WhatsApp Description", "Twitter Description", "Instagram Description", "Youtube Description"};

    Firebase fb = new Firebase();
    Task<QuerySnapshot> usersFromFirebase;
    HashMap<String, userDetails> allUsersMap = new HashMap<>();


    public HashMap<String, userDetails> lLikedList = new HashMap<>();
    public List<String> lTitle =new ArrayList<>();
    public List<String> lDescription =new ArrayList<>();
    public int mimages[] = {0, 0,0,0,0,0,0};
    //mTitle.add();




    //create userID
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    final String userID = mAuth.getCurrentUser().getUid();
    Source source = Source.CACHE;
    public FirebaseFirestore FB=FirebaseFirestore.getInstance();

    public DatabaseReference mDatabase;
    public CollectionReference docRef = FB.collection("users");
    public DocumentReference docRefLL = FB.document("users/"+userID);
   // public Iterable<CollectionReference> collections =docRef.document(userID).get;

    public Object liked_list;
    public Object liked_listL;
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
//        lTitle.add("WhatsApp");
//        lTitle.add("Twitter");
//        lTitle.add("Instagram");
        lDescription.add("Facebook Description");

//        lDescription.add("WhatsApp Description");
//        lDescription.add("Twitter Description");
//        lDescription.add("Instagram Description");


        listView = findViewById(R.id.listView);


        getDataFromDB();

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

//        FirebaseFirestore FB=FirebaseFirestore.getInstance();
//        System.out.println("before listener");
//        docRef.document(userID).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                System.out.println("inside listener");
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    System.out.println("inside isSuccessful");
//                    if (document != null) {
//                        System.out.println("inside document != null");
//                        liked_list = document.get("liked_list");
//                        Toast.makeText(MyListActivity.this,"finish mission",Toast.LENGTH_SHORT).show();
// /*                       for (int i=0;i<2;i++){
//                            docRef.document(userID).get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot document2 = task.getResult();
//                                        if (document2 != null) {
//                                            lTitle.add(document2.get("name").toString() + " " + document2.get("age").toString());
//                                            lDescription.add(document2.get("city").toString() + " " + document2.get("neighborhood").toString());
//                                        }
//                                    }
//                                }
//                            });
//                        }*/
//                        //update the adapter
//                        adapter.notifyDataSetChanged();
//                    } else {//no doc
//                    }
//                } else {//fail somehow
//                }
//            }
//        });
//        System.out.println("after listener");
//
///*                        //adding new data like would happend when we add th DB
//                        lTitle.add("Youtube");
//                        lDescription.add("Youtube Description");
//                        //update the adapter
//                        adapter.notifyDataSetChanged();*/

    }

/*            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document != null) {
                        liked_list=document.get("liked_list");
                        Toast.makeText(MyListActivity.this,"finish mission"+lLikedList.get(0),Toast.LENGTH_SHORT).show();
                     for (int i=0;i<lLikedList.size();i++){
                            docRef.document(lLikedList.get(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                    if (task2.isSuccessful()) {
                                        DocumentSnapshot document2 = task2.getResult();
                                        if (document2 != null) {
                                        //Object DBmylistrow = document.geData();
                                            lTitle.add(document2.get("name").toString() + " " + document2.get("age").toString());
                                            lDescription.add(document2.get("city").toString() + " " + document2.get("neighborhood").toString());
                                        }
                                    }
                                }
                            });
                        }
                        //update the adapter
                        adapter.notifyDataSetChanged();
                    } else {//no doc
                    }
                } else {//fail somehow
*/
    private void getDataFromDB(){
        final Task<QuerySnapshot> allDocuments = fb.getAllDocumentFromCollection("users");
        allDocuments.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    usersFromFirebase = task;
                    allUsersMap = AllUsers.convertTaskToUserDetailsList(usersFromFirebase);
                    lLikedList = getLikedList();
                    updateMyList();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void updateMyList() {
        for(userDetails user : lLikedList.values()){
            lTitle.add(user.getName() + " " + user.getAge());
            lDescription.add(user.getGender() + " " + user.getCity()+ " " + user.getNeighborhood());
        }
    }

    private HashMap<String, userDetails> getLikedList() {
        HashMap<String, userDetails> likedUsersMap = new HashMap<>();
        userDetails currentUser = allUsersMap.get(userID);
        List<String> likedUsersID = currentUser.getLikedList();
        for(String key : likedUsersID){
            userDetails user = allUsersMap.get(key);
            likedUsersMap.put(key, user);
        }
        return likedUsersMap;
    }

//    private void convertTaskToUserDetailsList() {
//        for(QueryDocumentSnapshot document : usersFromFirebase.getResult()) {
//            userDetails user = new userDetails(
//                    document.get("user_id"),
//                    document.get("name"),
//                    document.get("email"),
//                    document.get("gender"),
//                    document.get("age"),
//                    document.get("city"),
//                    document.get("neighborhood"),
//                    document.get("roommate_number"),
//                    document.get("liked_list"));
//
//            allUsers.put((String)document.get("user_id"), user);
//        }
//    }


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





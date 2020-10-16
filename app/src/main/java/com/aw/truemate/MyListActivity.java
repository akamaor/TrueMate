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

public class MyListActivity extends AppCompatActivity {
    ListView listView;
  //example text for the list
    String mTitle[] = {"Facebook", "WhatsApp", "Twitter", "Instagram", "Youtube"};
    String mDescription[] = {"Facebook Description", "WhatsApp Description", "Twitter Description", "Instagram Description", "Youtube Description"};
    int mimages[] = {0, 0,0,0,0,0,0};
/*
//get user id-> liked ->
    String mTitle[]={};
    String mDescription[]={};
    int mimages[]={};

 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        listView = findViewById(R.id.listView);

        MyListAdapter adapter = new MyListAdapter(this,mTitle,mDescription,mimages);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if( position != 0 ) {
                    Intent intent = new Intent(getApplicationContext(), MyListShowActivity.class);
                    // this intent put our 0 index image to another activity
                    Bundle bundle = new Bundle();
                    bundle.putInt("image", mimages[position]);
                    intent.putExtras(bundle);
                    // now put title and description to another activity
                    intent.putExtra("title", mTitle[position]);
                    intent.putExtra("description", mDescription[position]);
                    // also put your position
                    intent.putExtra("position", ""+position);
                    startActivity(intent);
                }
            }
        });
    }
        class MyListAdapter extends ArrayAdapter<String> {


            private final String[] rTitle;
            private final Context context;
            private final String[] rDescribtion;
            private final int[] rImgs;

            MyListAdapter(@NonNull Context context, String title[], String describtion[], int imgs[]) {
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
                mytitle.setText(rTitle[position]);
                mysubtitle.setText(rDescribtion[position]);
                return mylist_row;

            }

        }

}
package com.example.hp.finalprojectsecurity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BackDoorActivity extends AppCompatActivity {
    ArrayList<User> users;
    ListView listView;
    Button show,hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_door);
        listView=findViewById(R.id.list);
        show=findViewById(R.id.show);
        hide=findViewById(R.id.hide);
        users=new ArrayList<>();
        Cursor cursor=MainActivity.database.Return_all_data();
        while (cursor.moveToNext()) {
            User user=new User();
            user.setName(cursor.getString(1)) ;
            Log.d("ttt",cursor.getString(3));
            user.setPhoone(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            users.add(user);
        }

        Adabter adabter=new Adabter(this.getApplicationContext(),users);
        listView.setAdapter(adabter);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.VISIBLE);
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.INVISIBLE);
            }
        });
    }
}


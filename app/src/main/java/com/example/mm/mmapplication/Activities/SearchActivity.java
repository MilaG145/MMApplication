package com.example.mm.mmapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.UsersAdapter;
import com.example.mm.mmapplication.Model.UsersListItem;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 22.07.2017.
 */

public class SearchActivity extends AppCompatActivity {
UsersAdapter adapter;
    TinyDB tinyDB;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        tinyDB=new TinyDB(getApplicationContext());
        User user=tinyDB.getObject("user",User.class);
        final ListView listView= (ListView) findViewById(R.id.searchListView);
        adapter= new UsersAdapter(SearchActivity.this, getApplicationContext());
        ArrayList<UsersListItem>lista=new ArrayList<UsersListItem>();
        ArrayList<Object>friendsO=tinyDB.getListObject("friends",User.class);
        final ArrayList<User>friends=new ArrayList<User>();
        ArrayList<Object> searched=tinyDB.getListObject("searched", User.class);
        for(Object f:friendsO){
            User u =(User)f;
            friends.add(u);
        }
        for(Object s:searched){
            User u=(User)s;
            boolean friend=friends.contains(u);
            lista.add(new UsersListItem(Long.parseLong(u.id.toString()),u.firstName+" "+u.lastName,!friend,u.email));
        }
        tinyDB.clear();
        tinyDB.putObject("user",user);
        tinyDB.putListObject("friends",friendsO);

        adapter.setItems(lista);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                UsersListItem selected= (UsersListItem) listView.getItemAtPosition(position);
                User compare=new User();
                compare.setId(Integer.parseInt(selected.getUserId().toString()));
                if (friends.contains(compare)){
                    Intent i =new Intent(SearchActivity.this,UserFriendPreview.class);
                    i.putExtra("userFriend",selected.getEmail());
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"First add this user as a friend",Toast.LENGTH_LONG).show();
                }




            }
        });
    }
}

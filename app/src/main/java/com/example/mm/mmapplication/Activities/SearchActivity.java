package com.example.mm.mmapplication.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mm.mmapplication.Model.UsersAdapter;
import com.example.mm.mmapplication.Model.UsersListItem;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 22.07.2017.
 */

public class SearchActivity extends AppCompatActivity {
UsersAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ListView listView= (ListView) findViewById(R.id.searchListView);
        adapter= new UsersAdapter(SearchActivity.this);
        ArrayList<UsersListItem>lista=new ArrayList<UsersListItem>();
        lista.add(new UsersListItem(10L,"mila gurova", true));
        lista.add(new UsersListItem(10L,"mila ", false));
        lista.add(new UsersListItem(10L,"test", true));
        adapter.setItems(lista);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("Clicked "+position);
            }
        });
    }
}

package com.example.mm.mmapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mm.mmapplication.Adapters.NotificationsAdapter;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Model.FriendRequest;
import com.example.mm.mmapplication.Model.Meeting;
import com.example.mm.mmapplication.Model.NotificationModel;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.categories.ActivityCategory;
import com.example.mm.mmapplication.Model.categories.NotificationCategory;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 25.07.2017.
 */

public class NotificationActivity extends AppCompatActivity implements NavigationFragment.CreateNavigationListener {
    NotificationsAdapter notificationsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        final ListView listView= (ListView) findViewById(R.id.lvNotifications);
        notificationsAdapter= new NotificationsAdapter(getApplicationContext());
        ArrayList<NotificationModel>lista= new ArrayList<NotificationModel>();

        FriendRequest friendRequest= new FriendRequest();
        User user=new User();
        user.setFirstName("Sender1");
        user.setLastName("Surname1");
        friendRequest.setSender(user);
        NotificationModel notificationModel=new NotificationModel();
        notificationModel.setCategory(NotificationCategory.FriendRequest);
        notificationModel.setFriendRequest(friendRequest);
        lista.add(notificationModel);

        Meeting meeting=new Meeting();
        meeting.setTitle("proben meeting");
        meeting.setDate("2017-7-28");
        meeting.setTimeFrom("20:30");
        meeting.setTimeTo("21:00");
        meeting.setActivityCategory(ActivityCategory.WORK);

        notificationModel=new NotificationModel();
        notificationModel.setCategory(NotificationCategory.Meeting);
        notificationModel.setMeeting(meeting);
        lista.add(notificationModel);

        notificationsAdapter.setItems(lista);
        listView.setAdapter(notificationsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                NotificationModel selected= (NotificationModel) listView.getItemAtPosition(position);
               if(selected.getCategory().equals(NotificationCategory.Meeting)){
                   Toast.makeText(view.getContext(),"Meeting kliknat", Toast.LENGTH_LONG).show();
               }
            }
        });

        getNotifications();
    }

    public void getNotifications(){

    }

    @Override
    public void createMyProfileClickedListener() {
        Intent i = new Intent(NotificationActivity.this, CreateActivityActivity.class);
        startActivity(i);
    }

    @Override
    public void createNotificationsClickedListener() {

    }

    @Override
    public void createCreateClickedListener() {
        Intent i = new Intent(NotificationActivity.this, CreateActivityActivity.class);
        startActivity(i);
    }

    @Override
    public void createChatClickedListener() {

    }

    @Override
    public void createEditClickedListener() {

    }
}

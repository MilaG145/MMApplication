package com.example.mm.mmapplication.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mm.mmapplication.Adapters.NotificationsAdapter;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Model.FriendRequest;
import com.example.mm.mmapplication.Model.Meeting;
import com.example.mm.mmapplication.Model.NotificationModel;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.categories.ActivityCategory;
import com.example.mm.mmapplication.Model.categories.NotificationCategory;
import com.example.mm.mmapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Win8.1 on 25.07.2017.
 */

public class NotificationActivity extends AppCompatActivity implements NavigationFragment.CreateNavigationListener {
    NotificationsAdapter notificationsAdapter;
    TinyDB tinyDB;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        tinyDB=new TinyDB(getApplicationContext());
        listView= (ListView) findViewById(R.id.lvNotifications);
        notificationsAdapter= new NotificationsAdapter(getApplicationContext());

        ArrayList<NotificationModel>lista= new ArrayList<NotificationModel>();

//        FriendRequest friendRequest= new FriendRequest();
//        User user=new User();
//        user.setFirstName("Sender1");
//        user.setLastName("Surname1");
//        friendRequest.setSender(user);
//        NotificationModel notificationModel=new NotificationModel();
//        notificationModel.setCategory(NotificationCategory.FriendRequest);
//        notificationModel.setFriendRequest(friendRequest);
//        lista.add(notificationModel);
//
//        Meeting meeting=new Meeting();
//        meeting.setTitle("proben meeting");
//        meeting.setDate("2017-7-28");
//        meeting.setTimeFrom("20:30");
//        meeting.setTimeTo("21:00");
//        meeting.setActivityCategory(ActivityCategory.WORK);
//
//        notificationModel=new NotificationModel();
//        notificationModel.setCategory(NotificationCategory.Meeting);
//        notificationModel.setMeeting(meeting);
//        lista.add(notificationModel);



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
        GetNotifications task= new GetNotifications();
        task.execute((Void)null);
    }


    public class GetNotifications extends AsyncTask<Void, Void, Boolean> {
        User reciever=tinyDB.getObject("user", User.class);
        ArrayList<NotificationModel>list= new ArrayList<NotificationModel>();
        GetNotifications() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/notifications/getAll/"+reciever.id;
            String data = null;
            String jsonStr = null;
            JSONObject jsonObj = null;
            boolean prv = false;
            boolean vtor = false;
            try {
                jsonStr = sh.makeServiceCall(url, data, "GET");

                //jsonObj = new JSONObject(jsonStr);
                JSONArray jsonArray = new JSONArray(jsonStr);
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        NotificationModel notificationModel=new NotificationModel();
                        notificationModel.setId(Long.parseLong(obj.getString("id")));
                        String category=obj.getString("category");
                        if(category.equals("FriendRequest")){
                            System.out.println("Ulava FR");
                            notificationModel.setCategory(NotificationCategory.FriendRequest);
                            JSONObject obj2=obj.getJSONObject("friendRequest");
                            FriendRequest friendRequest= new FriendRequest();
                            User sender=new User();
                            friendRequest.setId(Long.parseLong(obj2.getString("id")));
                            friendRequest.setReciever(reciever);
                            JSONObject obj3=obj2.getJSONObject("sender");
                            sender.setId(obj3.getInt("id"));
                            sender.setFirstName(obj3.getString("firstName"));
                            sender.setLastName(obj3.getString("lastName"));
                            friendRequest.setSender(sender);
                            notificationModel.setReceiver(reciever);
                            notificationModel.setFriendRequest(friendRequest);
                        }
                        else {
                            System.out.println("Ulava M");
                            notificationModel.setCategory(NotificationCategory.Meeting);
                            JSONObject obj2=obj.getJSONObject("meeting");
                            Meeting meeting=new Meeting();
                            meeting.setId(Long.parseLong(obj2.getString("id")));
                            meeting.setTitle(obj2.getString("title"));
                            meeting.setDate(obj2.getString("date"));
                            meeting.setTimeFrom(obj2.getString("timeFrom"));
                            meeting.setTimeTo(obj2.getString("timeTo"));
                            notificationModel.setReceiver(reciever);
                            notificationModel.setMeeting(meeting);

                        }
                        list.add(notificationModel);
                    }

                }

            } catch (Exception e) {

            }

            return jsonStr!=null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                notificationsAdapter.setItems(list);
                listView.setAdapter(notificationsAdapter);


            } else {

            }
        }
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

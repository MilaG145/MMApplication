package com.example.mm.mmapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Adapters.PreviewFriendAdapter;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.SearchFragment;
import com.example.mm.mmapplication.Model.PreviewModel;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.categories.ActivityCategory;
import com.example.mm.mmapplication.Model.categories.PreviewCategory;
import com.example.mm.mmapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Win8.1 on 24.07.2017.
 */

public class UserFriendPreview extends AppCompatActivity implements SearchFragment.CreateSearchListener {
    User user;
    User userFriend;
    TinyDB tinyDB;
    TextView userFriendName;
    TextView activities;
    TextView meetings;
    String userFriendId;
    String name;
    ListView lvActivities;
    ListView lvMeetings;
    PreviewFriendAdapter activitiesAdapter;
    PreviewFriendAdapter meetingsAdapter;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friend_preview);
        tinyDB = new TinyDB(getApplicationContext());
        user = tinyDB.getObject("user", User.class);
        userFriendName = (TextView) findViewById(R.id.userFriendTV);
        Intent intent = getIntent();
        userFriendId = intent.getStringExtra("userFriend");
        name=intent.getStringExtra("userFriendName");
        userFriendName.setText("Catch Up with "+ name.toUpperCase());
        GetUser getUserTask = new GetUser(userFriendId);
        lvActivities= (ListView) findViewById(R.id.lvActivities);
        lvMeetings = (ListView) findViewById(R.id.lvMeetings);
        activitiesAdapter= new PreviewFriendAdapter(getApplicationContext());
        meetingsAdapter= new PreviewFriendAdapter(getApplicationContext());
        context=getApplicationContext();
        meetings= (TextView) findViewById(R.id.tvFMeetings);
        activities= (TextView) findViewById(R.id.tvFActivities);
        getUserTask.execute((Void) null);

    }

    @Override
    public void createSearchListener(String text) {
        if (text.trim().isEmpty()) {
            Toast.makeText(UserFriendPreview.this, "Please enter a name", Toast.LENGTH_LONG).show();
        } else {
            SearchTask stask = new SearchTask(user.id, text);
            stask.execute((Void) null);

        }

    }

    public class GetUser extends AsyncTask<Void, Void, Boolean> {
        private final String id;
        User userTask = null;
        boolean vtor=false;
        boolean tret=false;
        ArrayList<PreviewModel> pmActivities=new ArrayList<PreviewModel>();
        ArrayList<PreviewModel> pmMeetings=new ArrayList<PreviewModel>();
        GetUser(String ids) {
            id = ids;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;
            JSONObject jsonObj = null;
            String url=Constants.IP_Adress+"/activities/all/"+id;
            try{
                jsonStr=sh.makeServiceCall(url,null,"GET");
                System.out.println(jsonStr);
                JSONArray jsonArray;
                if (new JSONArray(jsonStr).length()!=0){
                     jsonArray= new JSONArray(jsonStr);
                    pmActivities= new ArrayList<>();
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject obj=jsonArray.getJSONObject(i);
                        PreviewModel previewModel=new PreviewModel();
                        previewModel.setPreviewCategory(PreviewCategory.Activity);
                        StringBuilder date= new StringBuilder();
                        JSONObject objDate=obj.getJSONObject("date");
                        date.append(objDate.getString("dayOfMonth"));
                        date.append("/");
                        date.append(objDate.getString("month"));
                        date.append("/");
                        date.append(objDate.getString("year"));
                        date.append(" ");
                        date.append("(");
                        date.append(objDate.getString("dayOfWeek"));
                        date.append(")");
                        previewModel.setDate(date.toString());
                        previewModel.setTitle(obj.getString("title"));
                        previewModel.setId(obj.getString("id"));
                        StringBuilder time= new StringBuilder();
                        JSONObject objTimeF=obj.getJSONObject("timeFrom");
                        JSONObject objTimeT=obj.getJSONObject("timeTo");
                        String var;
                        var= objTimeF.getString("hour");
                        if(Integer.parseInt(var)<10){
                            time.append("0");
                        }
                        time.append(var);
                        time.append(":");
                        var= objTimeF.getString("minute");
                        if(Integer.parseInt(var)<10){
                            time.append("0");
                        }
                        time.append(var);
                        time.append("->");
                        var= objTimeT.getString("hour");
                        if(Integer.parseInt(var)<10){
                            time.append("0");
                        }
                        time.append(var);
                        time.append(":");
                        var= objTimeT.getString("minute");
                        if(Integer.parseInt(var)<10){
                            time.append("0");
                        }
                        time.append(var);
                        previewModel.setTime(time.toString());
                        pmActivities.add(previewModel);
                    }
                    vtor=true;
                }
            }catch (Exception e){

            }
            url=Constants.IP_Adress+"/meetings/getMeetings/"+id;
            try{
                jsonStr=sh.makeServiceCall(url,null,"GET");
                System.out.println(jsonStr);
                if(new JSONArray(jsonStr).length()!=0){
                    JSONArray jsonArray= new JSONArray(jsonStr);
                    pmMeetings= new ArrayList<>();
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject obj=jsonArray.getJSONObject(i);
                        PreviewModel previewModel=new PreviewModel();
                        previewModel.setPreviewCategory(PreviewCategory.Meeting);
                        StringBuilder date= new StringBuilder();
                        JSONObject objDate=obj.getJSONObject("date");
                        if(objDate!=null){
                            date.append(objDate.getString("dayOfMonth"));
                            date.append("/");
                            date.append(objDate.getString("month"));
                            date.append("/");
                            date.append(objDate.getString("year"));
                            date.append(" ");
                            date.append("(");
                            date.append(objDate.getString("dayOfWeek"));
                            date.append(")");
                        }
                        previewModel.setDate(date.toString());
                        previewModel.setTitle(obj.getString("title"));
                        previewModel.setId(obj.getString("id"));
                        String category=obj.getString("activityCategory");
                        if(ActivityCategory.SPORT.toString().equalsIgnoreCase(category))
                            previewModel.setActivityCategory(ActivityCategory.SPORT);
                        else if(ActivityCategory.WORK.toString().equalsIgnoreCase(category))
                            previewModel.setActivityCategory(ActivityCategory.WORK);
                        else if(ActivityCategory.SCHOOL.toString().equalsIgnoreCase(category))
                            previewModel.setActivityCategory(ActivityCategory.SCHOOL);
                        else
                            previewModel.setActivityCategory(ActivityCategory.OTHER);
                        StringBuilder time= new StringBuilder();
                        JSONObject objTimeF=obj.getJSONObject("timeFrom");
                        JSONObject objTimeT=obj.getJSONObject("timeTo");
                        String var;
                        if(objTimeF!=null && objTimeT!=null){
                            var= objTimeF.getString("hour");
                            if(Integer.parseInt(var)<10){
                                time.append("0");
                            }
                            time.append(var);
                            time.append(":");
                            var= objTimeF.getString("minute");
                            if(Integer.parseInt(var)<10){
                                time.append("0");
                            }
                            time.append(var);
                            time.append("->");
                            var= objTimeT.getString("hour");
                            if(Integer.parseInt(var)<10){
                                time.append("0");
                            }
                            time.append(var);
                            time.append(":");
                            var= objTimeT.getString("minute");
                            if(Integer.parseInt(var)<10){
                                time.append("0");
                            }
                            time.append(var);
                        }


                        previewModel.setTime(time.toString());
                        pmMeetings.add(previewModel);
                    }
                    tret=true;

                }
                }
                catch (Exception e){
                System.out.println(e.toString());
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                if(vtor){
                    activitiesAdapter.setItems(pmActivities);
                    lvActivities.setAdapter(activitiesAdapter);
                }
                else {
                    activities.setText(name+" Has no Activities");

                }
                if(tret){
                    meetingsAdapter.setItems(pmMeetings);
                    lvMeetings.setAdapter(meetingsAdapter);
                    lvMeetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            System.out.println("Ulava");
                            PreviewModel selected= (PreviewModel) lvMeetings.getItemAtPosition(position);
                            Intent i =new Intent(UserFriendPreview.this,MeetingPreviewActivity.class);
                            i.putExtra("meetingid",selected.getId());
                            i.putExtra("meetingTitle",selected.getTitle());
                            i.putExtra("timeFrom",selected.getTime());
                            i.putExtra("date",selected.getDate());
                            i.putExtra("category",selected.getActivityCategory().toString());

                            startActivity(i);
                        }
                    });
                }else {
                    meetings.setText(name+" Has no Meetings");
                }

            } else {
                Toast.makeText(context, "Couldn't load user", Toast.LENGTH_LONG).show();
                activities.setText(name.toUpperCase()+" Has no Activities");
                meetings.setText(name.toUpperCase()+" Has no Meetings");
            }
        }
    }

    public class SearchTask extends AsyncTask<Void, Void, Boolean> {
        Long userId;
        String search;
        ArrayList<Object> searched = null;

        SearchTask(Integer id, String s) {
            userId = Long.parseLong(id.toString());
            search = s;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/users/search";
            String data = null;
            String jsonStr = null;
            searched = new ArrayList<Object>();
            try {
                data = URLEncoder.encode("keyword", "UTF-8")
                        + "=" + URLEncoder.encode(search, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                jsonStr = sh.makeServiceCall(url, data, "POST");
                JSONArray jsonArray = new JSONArray(jsonStr);
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    User u = new User();
                    u = new User();
                    u.setId(obj.getInt("id"));
                    u.setEmail(obj.getString("email"));
                    u.setFirstName(obj.getString("firstName"));
                    u.setLastName(obj.getString("lastName"));
                    searched.add(u);
                }

            } catch (Exception e) {

            }

            return jsonStr != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                if (searched.isEmpty()) {
                    Toast.makeText(UserFriendPreview.this, "No results found", Toast.LENGTH_LONG).show();
                } else {
                    tinyDB.putListObject("searched", searched);
                    Intent i = new Intent(UserFriendPreview.this, SearchActivity.class);
                    startActivity(i);

                }

            } else {
                Toast.makeText(UserFriendPreview.this, "Could not search at this moment", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}

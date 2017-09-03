package com.example.mm.mmapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Adapters.PreviewAdapter;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Fragments.SearchFragment;
import com.example.mm.mmapplication.Model.Meeting;
import com.example.mm.mmapplication.Model.PreviewModel;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.UsersListItem;
import com.example.mm.mmapplication.Model.categories.ActivityCategory;
import com.example.mm.mmapplication.Model.categories.PreviewCategory;
import com.example.mm.mmapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Win8.1 on 16.07.2017.
 */

public class FirstScreenActivity extends AppCompatActivity implements SearchFragment.CreateSearchListener, NavigationFragment.CreateNavigationListener {

    TextView textView;
    String mail;
    User user;
    TinyDB tinyDB;
    ListView lvActivities;
    ListView lvMeetings;
    PreviewAdapter activitiesAdapter;
    PreviewAdapter meetingsAdapter;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(getApplicationContext());
        setContentView(R.layout.first_screen_activity);
        textView = (TextView) findViewById(R.id.UsertextView);
        mail = tinyDB.getString("email");
        user=tinyDB.getObject("user",User.class);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        textView.setText("Welcome " + user.firstName + " " + user.lastName);
        lvActivities= (ListView) findViewById(R.id.lvActivities);
        lvMeetings = (ListView) findViewById(R.id.lvMeetings);
        activitiesAdapter= new PreviewAdapter(getApplicationContext());
        meetingsAdapter= new PreviewAdapter(getApplicationContext());
            attemptTask();



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void attemptTask() {
        GetUser task = new GetUser();
        task.execute((Void) null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationFragment fragment = (NavigationFragment) getFragmentManager().findFragmentById(R.id.fraNavigation);
        fragment.myProfileClicked(null);

            attemptTask();



    }

    @Override
    public void createSearchListener(String text) {
        if (text.trim().isEmpty()) {
            Toast.makeText(FirstScreenActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
        } else {
            SearchTask stask = new SearchTask(user.id, text);
            stask.execute((Void) null);

        }
    }

    @Override
    public void createMyProfileClickedListener() {

    }

    @Override
    public void createNotificationsClickedListener() {
        Intent i = new Intent(FirstScreenActivity.this, NotificationActivity.class);
        startActivity(i);

    }

    @Override
    public void createCreateClickedListener() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create").setPositiveButton("Create Meeting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(FirstScreenActivity.this, MeetingActivity.class);
                startActivity(i);
            }
        }).setNegativeButton("Create Activity", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(FirstScreenActivity.this, CreateActivityActivity.class);
                startActivity(i);
            }
        }).setIcon(R.drawable.create_pic).setMessage("What do you want to create?").setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void createChatClickedListener() {
        Intent i = new Intent(FirstScreenActivity.this, ChatActivity.class);
        startActivity(i);

    }

    @Override
    public void createEditClickedListener() {
        Intent i = new Intent(FirstScreenActivity.this, EditProfileActivity.class);
        startActivity(i);
    }



    public class GetUser extends AsyncTask<Void, Void, Boolean> {

        ArrayList<Object> friends = null;
        ArrayList<PreviewModel> pmActivities=new ArrayList<PreviewModel>();
        ArrayList<PreviewModel> pmMeetings=new ArrayList<PreviewModel>();

        GetUser() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean prv= false;
            boolean vtor=false;
            boolean tret=false;
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/users/getFriends/" + user.id;
            String data = null;
            String jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(url, data, "GET");
                JSONArray jsonArray = new JSONArray(jsonStr);
                friends = new ArrayList<Object>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        User u = new User();
                        u = new User();
                        u.setId(obj.getInt("id"));
                        u.setEmail(obj.getString("email"));
                        u.setFirstName(obj.getString("firstName"));
                        u.setLastName(obj.getString("lastName"));
                        friends.add(u);
                    }
                    prv=true;
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            url=Constants.IP_Adress+"/activities/all/"+user.id;
            try{
                jsonStr=sh.makeServiceCall(url,null,"GET");
                JSONArray jsonArray= new JSONArray(jsonStr);
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

            }catch (Exception e){

            }
            url=Constants.IP_Adress+"/meetings/getMeetings/"+user.id;
            try{
                jsonStr=sh.makeServiceCall(url,null,"GET");
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

            }catch (Exception e){
                System.out.println(e.toString());
            }

            return prv&&vtor&&tret;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
               // intent.putExtra("USER", user);
                tinyDB.putListObject("friends", friends);
                activitiesAdapter.setItems(pmActivities);
                meetingsAdapter.setItems(pmMeetings);
                lvMeetings.setAdapter(meetingsAdapter);
                lvActivities.setAdapter(activitiesAdapter);


                lvMeetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        PreviewModel selected= (PreviewModel) lvMeetings.getItemAtPosition(position);
                        Intent i =new Intent(FirstScreenActivity.this,MeetingPreviewActivity.class);
                        i.putExtra("meetingid",selected.getId());
                        i.putExtra("meetingTitle",selected.getTitle());
                        i.putExtra("timeFrom",selected.getTime());
                        i.putExtra("date",selected.getDate());
                        i.putExtra("category",selected.getActivityCategory().toString());

                        startActivity(i);
                    }
                });


            } else {
                tinyDB.clear();
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                Intent intent = new Intent(FirstScreenActivity.this, LoginActivity.class);
                startActivity(intent);
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
                    Toast.makeText(FirstScreenActivity.this, "No results found", Toast.LENGTH_LONG).show();
                } else {
                    tinyDB.putListObject("searched", searched);
                    Intent i = new Intent(FirstScreenActivity.this, SearchActivity.class);
                    startActivity(i);

                }

            } else {
                Toast.makeText(FirstScreenActivity.this, "Could not search at this moment", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}

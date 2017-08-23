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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Fragments.SearchFragment;
import com.example.mm.mmapplication.Model.Meeting;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
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
    Intent intent;
    User userActivity;
    TinyDB tinyDB;


    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(getApplicationContext());
        setContentView(R.layout.first_screen_activity);
        textView = (TextView) findViewById(R.id.UsertextView);
        //intent = getIntent();
        mail = tinyDB.getString("email");
        //textView.setText("Welcome " + mail);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
            attemptTask();



    }


    private void attemptTask() {
        GetUser task = new GetUser(mail);
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
            SearchTask stask = new SearchTask(userActivity.id, text);
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
        private final String mEmail;
        ArrayList<Object> friends = null;
        User user = null;

        GetUser(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/users/getUserByEmail";
            String data = null;
            String jsonStr = null;
            JSONObject jsonObj = null;
            boolean prv = false;
            boolean vtor = false;

            try {
                data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(mEmail, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                jsonStr = sh.makeServiceCall(url, data, "POST");

                jsonObj = new JSONObject(jsonStr);
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonObj);
                if (jsonObj != null) {
                    System.out.println(jsonObj.getInt("id"));
                    user = new User();
                    user.setId(jsonObj.getInt("id"));
                    user.setEmail(jsonObj.getString("email"));
                    user.setFirstName(jsonObj.getString("firstName"));
                    user.setLastName(jsonObj.getString("lastName"));
                    prv = true;
                }

            } catch (Exception e) {

            }
            sh = new HttpHandler();
            url = Constants.IP_Adress + "/users/getFriends/" + user.id;
            data = null;
            jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(url, data, "GET");
                JSONArray jsonArray = new JSONArray(jsonStr);
                friends = new ArrayList<Object>();
                if (jsonArray != null) {
                    vtor = true;
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
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            return (vtor && prv);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
               // intent.putExtra("USER", user);
                textView.setText("Welcome " + user.firstName + " " + user.lastName);
                userActivity = user;
                tinyDB.putObject("user", userActivity);
                tinyDB.putListObject("friends", friends);


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
                    System.out.println("Iterira");
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

package com.example.mm.mmapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.SearchFragment;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
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
    String userFriendEmail;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friend_preview);
        tinyDB = new TinyDB(getApplicationContext());
        user = tinyDB.getObject("user", User.class);
        userFriendName = (TextView) findViewById(R.id.userFriendTV);
        Intent intent = getIntent();
        userFriendEmail = intent.getStringExtra("userFriend");
        GetUser getUserTask = new GetUser(userFriendEmail);
        context = getApplicationContext();
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
        private final String mEmail;
        User userTask = null;

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

                System.out.println(jsonObj.getInt("id"));
                userTask = new User();
                userTask.setId(jsonObj.getInt("id"));
                userTask.setEmail(jsonObj.getString("email"));
                userTask.setFirstName(jsonObj.getString("firstName"));
                userTask.setLastName(jsonObj.getString("lastName"));


            } catch (Exception e) {

            }

            return jsonObj != null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                userFriendName.setText(userTask.email);
            } else {
                Toast.makeText(context, "Couldn't load user", Toast.LENGTH_LONG).show();
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

package com.example.mm.mmapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Win8.1 on 16.07.2017.
 */

public class FirstScreenActivity extends AppCompatActivity {

    TextView textView;
    String mail;
    Intent intent;
    User userActivity;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen_activity);
        textView = (TextView) findViewById(R.id.UsertextView);
        intent = getIntent();
        mail = intent.getStringExtra("EXTRA_MESSAGE");
        //textView.setText("Welcome " + mail);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        Button logoutBtn= (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                Intent intent= new Intent(FirstScreenActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        Button createBtn= (Button) findViewById(R.id.createActivity);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(FirstScreenActivity.this, CreateActivityActivity.class);
                i.putExtra("USER",userActivity);
                startActivity(i);
            }
        });

        attemptTask();
    }

    private void attemptTask() {
        GetUser task = new GetUser(mail);
        task.execute((Void) null);

    }

    public class GetUser extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        User user=null;

        GetUser(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress+"/users/getUserByEmail";
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
                //JSONArray jsonArray= jsonObj.getJSONArray("");

                if(jsonObj!=null){
                System.out.println(jsonObj.getInt("id"));
                user=new User();
                user.setId(jsonObj.getInt("id"));
                user.setEmail(jsonObj.getString("email"));
                user.setFirstName(jsonObj.getString("firstName"));
                user.setLastName(jsonObj.getString("lastName"));
                }

            } catch (Exception e) {

            }

            return jsonObj != null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                intent.putExtra("USER",user);
                textView.setText("Welcome " + user.firstName+" "+user.lastName);
                userActivity=user;

            }
            else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                Intent intent= new Intent(FirstScreenActivity.this,LoginActivity.class);
                startActivity(intent);
            }

        }
    }
}

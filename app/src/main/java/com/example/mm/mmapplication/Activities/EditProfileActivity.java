package com.example.mm.mmapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Win8.1 on 30.07.2017.
 */

public class EditProfileActivity extends AppCompatActivity implements NavigationFragment.CreateNavigationListener {

    Button logout;
    TinyDB tinyDB;
    User user;
    TextView tvName;
    TextView tvLastName;
    TextView tvEmail;
    TextView tvPass;
    EditText etName;
    EditText etLastName;
    EditText etPass;
    EditText etEmal;
    Boolean bName;
    Boolean bLastName;
    Boolean bEmail;
    Boolean bPassword;
    String sName;
    String sLastName;
    String sEmail;
    String sPassword;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        logout = (Button) findViewById(R.id.btnlogout);
        tinyDB = new TinyDB(getApplicationContext());
        user = tinyDB.getObject("user", User.class);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();


        tvName = (TextView) findViewById(R.id.tvName);
        tvLastName = (TextView) findViewById(R.id.tvlastName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPass = (TextView) findViewById(R.id.tvPassword);

        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPass = (EditText) findViewById(R.id.etPass);
        etEmal = (EditText) findViewById(R.id.etEmail);

        tvName.setText(user.getFirstName());
        tvLastName.setText(user.getLastName());
        tvEmail.setText(user.getEmail());
        tvPass.setText(user.getPassword());

        bName = false;
        bLastName = false;
        bEmail = false;
        bPassword = false;

        Button save = (Button) findViewById(R.id.btnSave);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                tinyDB.clear();
                Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etName.getText().toString().trim().isEmpty()) {
                    bName = true;
                    sName = etName.getText().toString();
                }
                if (!etLastName.getText().toString().trim().isEmpty()) {
                    bLastName = true;
                    sLastName = etLastName.getText().toString();
                }

                if (!etEmal.getText().toString().trim().isEmpty()) {
                    bEmail = true;
                    sEmail = etEmal.getText().toString();
                }
                if (!etPass.getText().toString().trim().isEmpty()) {
                    bPassword = true;
                    sPassword = etPass.getText().toString();
                }
                EditTask editTask= new EditTask();
                editTask.execute((Void)null);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationFragment navigationFragment = (NavigationFragment) getFragmentManager().findFragmentById(R.id.fraNavigation);
        navigationFragment.editClicked(null);
    }

    @Override
    public void createMyProfileClickedListener() {
        Intent i = new Intent(EditProfileActivity.this, FirstScreenActivity.class);
        startActivity(i);
    }

    @Override
    public void createNotificationsClickedListener() {
        Intent i = new Intent(EditProfileActivity.this, NotificationActivity.class);
        startActivity(i);
    }

    @Override
    public void createCreateClickedListener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create").setPositiveButton("Create Meeting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(EditProfileActivity.this, MeetingActivity.class);
                startActivity(i);
            }
        }).setNegativeButton("Create Activity", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(EditProfileActivity.this, CreateActivityActivity.class);
                startActivity(i);
            }
        }).setIcon(R.drawable.create_pic).setMessage("What do you want to create?").setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void createChatClickedListener() {
        Intent i = new Intent(EditProfileActivity.this, ChatActivity.class);
        startActivity(i);
    }

    @Override
    public void createEditClickedListener() {

    }

    public class EditTask extends AsyncTask<Void, Void, Boolean> {
        User newUser;

        EditTask() {
            newUser = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();


            if (bName) {
                String url = Constants.IP_Adress + "/users/updateFirstName/" + user.getId();
                String data = null;
                String jsonStr = null;
                try {
                    data = URLEncoder.encode("firstName", "UTF-8")
                            + "=" + URLEncoder.encode(sName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    jsonStr = sh.makeServiceCall(url, data, "PUT");
                } catch (Exception e) {
                    return false;
                }
                if (jsonStr != null)
                    newUser.setFirstName(sName);
            }

            if (bLastName) {
                String url = Constants.IP_Adress + "/users/updateLastName/" + user.getId();
                String data = null;
                String jsonStr = null;
                try {
                    data = URLEncoder.encode("lastName", "UTF-8")
                            + "=" + URLEncoder.encode(sLastName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    jsonStr = sh.makeServiceCall(url, data, "PUT");
                } catch (Exception e) {
                    return false;
                }
                if (jsonStr != null)
                    newUser.setLastName(sLastName);
            }

            if (bEmail) {
                String url = Constants.IP_Adress + "/users/updateEmail/" + user.getId();
                String data = null;
                String jsonStr = null;
                try {
                    data = URLEncoder.encode("email", "UTF-8")
                            + "=" + URLEncoder.encode(sEmail, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    jsonStr = sh.makeServiceCall(url, data, "PUT");
                } catch (Exception e) {
                    return false;
                }
                if (jsonStr != null)
                    newUser.setEmail(sEmail);
            }
            if (bPassword) {
                String url = Constants.IP_Adress + "/users/updatePassword/" + user.getId();
                String data = null;
                String jsonStr = null;
                try {
                    data = URLEncoder.encode("password", "UTF-8")
                            + "=" + URLEncoder.encode(sPassword, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
                try {
                    jsonStr = sh.makeServiceCall(url, data, "PUT");
                } catch (Exception e) {
                    return false;
                }
                //newUser.setEmail(sEmail);
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Toast.makeText(EditProfileActivity.this, "Changes made", Toast.LENGTH_LONG).show();
                tinyDB.putObject("user",newUser);
            } else {
                Toast.makeText(EditProfileActivity.this, "Changes not made", Toast.LENGTH_LONG).show();
            }
            Intent i =new Intent(EditProfileActivity.this, FirstScreenActivity.class);
            startActivity(i);
        }

        @Override
        protected void onCancelled() {

        }
    }
}

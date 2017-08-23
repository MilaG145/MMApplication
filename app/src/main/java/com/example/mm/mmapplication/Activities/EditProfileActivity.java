package com.example.mm.mmapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.R;

/**
 * Created by Win8.1 on 30.07.2017.
 */

public class EditProfileActivity extends AppCompatActivity  implements NavigationFragment.CreateNavigationListener{

    Button logout;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    TinyDB tinyDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        logout= (Button) findViewById(R.id.btnlogout);
        tinyDB=new TinyDB(getApplicationContext());
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationFragment navigationFragment=(NavigationFragment) getFragmentManager().findFragmentById(R.id.fraNavigation);
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
}

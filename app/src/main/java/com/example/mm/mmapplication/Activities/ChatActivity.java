package com.example.mm.mmapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.mm.mmapplication.Adapters.ChatAdapter;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.R;

/**
 * Created by Win8.1 on 30.07.2017.
 */

public class ChatActivity extends AppCompatActivity implements NavigationFragment.CreateNavigationListener{

    ChatAdapter chatAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationFragment fragment = (NavigationFragment) getFragmentManager().findFragmentById(R.id.fraNavigation);
        fragment.chatClicked(null);
    }

    @Override
    public void createMyProfileClickedListener() {
        Intent i = new Intent(ChatActivity.this, FirstScreenActivity.class);
        startActivity(i);
    }

    @Override
    public void createNotificationsClickedListener() {
        Intent i = new Intent(ChatActivity.this, NotificationActivity.class);
        startActivity(i);
    }

    @Override
    public void createCreateClickedListener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create").setPositiveButton("Create Meeting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(ChatActivity.this, MeetingActivity.class);
                startActivity(i);
            }
        }).setNegativeButton("Create Activity", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(ChatActivity.this, CreateActivityActivity.class);
                startActivity(i);
            }
        }).setIcon(R.drawable.create_pic).setMessage("What do you want to create?").setCancelable(true);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void createChatClickedListener() {

    }

    @Override
    public void createEditClickedListener() {
        Intent i = new Intent(ChatActivity.this, EditProfileActivity.class);
        startActivity(i);
    }
}

package com.example.mm.mmapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mm.mmapplication.Adapters.ChatAdapter;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Model.ChatListItem;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 30.07.2017.
 */

public class ChatActivity extends AppCompatActivity implements NavigationFragment.CreateNavigationListener {

    ChatAdapter chatAdapter;
    TinyDB tinyDB;
    ArrayList<ChatListItem> lista;
    User user;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tinyDB = new TinyDB(getApplicationContext());
        user = tinyDB.getObject("user", User.class);
        listView = (ListView) findViewById(R.id.lvChat);
        chatAdapter = new ChatAdapter(ChatActivity.this);
        lista = new ArrayList<ChatListItem>();
        GetItems getItemstask=new GetItems();
        getItemstask.execute((Void)null);

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


    public class GetItems extends AsyncTask<Void, Void, Boolean> {
        ArrayList<ChatListItem> list = new ArrayList<ChatListItem>();

        GetItems() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/chat/getForUser/" + user.id;
            String data = null;
            String jsonStr = null;
            JSONObject jsonObj = null;
            boolean prv = false;
            boolean vtor = false;
            ArrayList<String> listaId = new ArrayList<>();
            try {
                jsonStr = sh.makeServiceCall(url, null, "GET");

                //jsonObj = new JSONObject(jsonStr);
                JSONArray jsonArray = new JSONArray(jsonStr);
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        listaId.add(obj.getString("id"));

                    }

                }

            } catch (Exception e) {


            }
            for (int i = 0; i < listaId.size(); i++) {
                url = Constants.IP_Adress + "/meetings/getMeetingTitle/" + listaId.get(i);
                try {
                    jsonStr = sh.makeServiceCall(url, null, "GET");
                    ChatListItem chatListItem = new ChatListItem();
                    chatListItem.setId(listaId.get(i));
                    chatListItem.setName(jsonStr);
                    prv = true;
                    lista.add(chatListItem);
                } catch (Exception e) {
                    prv = false;
                }
            }


            return prv;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                chatAdapter.setItems(lista);
                listView.setAdapter(chatAdapter);

            } else {

            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}

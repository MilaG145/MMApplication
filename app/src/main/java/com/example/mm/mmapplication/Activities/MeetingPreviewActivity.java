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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Adapters.UserSimpleAdapter;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Model.PreviewModel;
import com.example.mm.mmapplication.Model.UsersListItem;
import com.example.mm.mmapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 30.08.2017.
 */

public class MeetingPreviewActivity extends AppCompatActivity {

    ListView listView;
    TextView title;
    TextView from;
    TextView to;
    TextView category;
    UserSimpleAdapter userSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_preview);
        listView= (ListView) findViewById(R.id.lvInvitedUsers);
        title= (TextView) findViewById(R.id.tvMeetingTitle);
        from= (TextView) findViewById(R.id.tvTimeFrom);
        category= (TextView) findViewById(R.id.tvMeetingCategory);
        Intent intent =getIntent();
        String meetingid= intent.getStringExtra("meetingid");
        title.setText(intent.getStringExtra("meetingTitle"));
        from.setText(intent.getStringExtra("timeFrom"));
        category.setText(intent.getStringExtra("category"));
        userSimpleAdapter=new UserSimpleAdapter(getApplicationContext());
        GetUsers getUsersTask=new GetUsers(meetingid);
        getUsersTask.execute((Void)null);

    }


    public class GetUsers extends AsyncTask<Void, Void, Boolean> {
        String meetingId;
        ArrayList<UsersListItem> lista = null;
        GetUsers(String id) {
            meetingId = id;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/meetings/getUsers/"+meetingId;
            String jsonStr = null;
            lista = new ArrayList<UsersListItem>();
            try {
                jsonStr = sh.makeServiceCall(url, null, "GET");
                JSONArray jsonArray = new JSONArray(jsonStr);
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String firstname=obj.getString("firstName");
                    String lastname=obj.getString("lastName");
                    String id= obj.getString("id");
                    UsersListItem u=new UsersListItem(Long.parseLong(id),firstname+" "+lastname,true,"none");
                    lista.add(u);
                }

            } catch (Exception e) {

            }

            return jsonStr != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
               userSimpleAdapter.setItems(lista);
                listView.setAdapter(userSimpleAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        UsersListItem selected= (UsersListItem) listView.getItemAtPosition(position);
                        Intent i =new Intent(MeetingPreviewActivity.this,UserFriendPreview.class);
                        i.putExtra("userFriend",selected.getUserId().toString());
                        i.putExtra("userFriendName",selected.getFullName());

                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(MeetingPreviewActivity.this, "Could not load users", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }



}

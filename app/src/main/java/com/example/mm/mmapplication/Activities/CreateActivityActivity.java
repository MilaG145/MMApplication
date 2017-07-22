package com.example.mm.mmapplication.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.CreateViewFragment;
import com.example.mm.mmapplication.Model.ActivityModel;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Win8.1 on 18.07.2017.
 */

public class CreateActivityActivity extends AppCompatActivity implements CreateViewFragment.CreateListener {

    TextView tekst;
    Intent intent;
    User user;
    private CreateActivityTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_activity);
        tekst = (TextView) findViewById(R.id.textView2);
        CreateViewFragment createViewFragment = new CreateViewFragment();
        intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");

        // setFragment(createViewFragment);

    }

    public void setFragment(Fragment f) {
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragmentContainer) == null) {
            fm.beginTransaction().add(R.id.fragmentContainer, f).commit();
        }

    }

    @Override
    public void create(ActivityModel activityModel) {
        if (mAuthTask != null) {
            return;
        }
        mAuthTask = new CreateActivityTask(activityModel);
        mAuthTask.execute((Void) null);
        //tekst.setText(text);
    }


    public class CreateActivityTask extends AsyncTask<Void, Void, Boolean> {

        private final ActivityModel activityModel;

        CreateActivityTask(ActivityModel activityModel1) {
            activityModel = activityModel1;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/activities/create";
            String data = null;
            String jsonStr = null;
            JSONObject jsonObj = null;
            try {
                data = URLEncoder.encode("user_id", "UTF-8")
                        + "=" + URLEncoder.encode(user.id.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("title", "UTF-8") + "="
                        + URLEncoder.encode(activityModel.title, "UTF-8");
                data += "&" + URLEncoder.encode("ac", "UTF-8") + "="
                        + URLEncoder.encode(activityModel.activityCategory.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("at", "UTF-8") + "="
                        + URLEncoder.encode(activityModel.activityTime.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8") + "="
                        + URLEncoder.encode(activityModel.date, "UTF-8");
                data += "&" + URLEncoder.encode("timeFrom", "UTF-8") + "="
                        + URLEncoder.encode(activityModel.timeFrom, "UTF-8");
                data += "&" + URLEncoder.encode("timeTo", "UTF-8") + "="
                        + URLEncoder.encode(activityModel.timeTo, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);

            try {
                jsonStr = sh.makeServiceCall(url, data, "POST");
                jsonObj = new JSONObject(jsonStr);
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);
                //JSONArray jsonArray= jsonObj.getJSONArray("");

            } catch (Exception e) {

            }


            return jsonObj != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;


            if (success) {
                Intent i = new Intent(CreateActivityActivity.this, FirstScreenActivity.class);
                Toast.makeText(CreateActivityActivity.this,"Activity Created", Toast.LENGTH_LONG).show();
                i.putExtra("EXTRA_MESSAGE",user.email);
                startActivity(i);


            } else {
                Toast.makeText(CreateActivityActivity.this,"Activity Creation Failed", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }


}

package com.example.mm.mmapplication.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Fragments.NavigationFragment;
import com.example.mm.mmapplication.Model.ActivityModel;
import com.example.mm.mmapplication.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Win8.1 on 22.08.2017.
 */

public class MeetingActivity extends AppCompatActivity implements NavigationFragment.CreateNavigationListener {

    Button createBtn;
    EditText titleET;
    Spinner meetingCategorySpinner;
    String meetingCategory;
    Button dateBtn;
    Button btnFrom;
    Button btnTo;
    String stringtimeFrom;
    String stringtimeTo;
    String stringdateFrom;
    int yearS;
    int monthS;
    int dayS;
    int hourFrom;
    int hourTo;
    int minutesFrom;
    int minutesTo;
    Date d;
    CreateMeetingTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        createBtn = (Button) findViewById(R.id.meetingCreateBtn);
        dateBtn = (Button) findViewById(R.id.meetingDateBn);
        titleET = (EditText) findViewById(R.id.meetingTitleET);
        // this.activity = getActivity();

        meetingCategorySpinner = (Spinner) findViewById(R.id.meetingCategorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getApplicationContext(),
                R.array.activity_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingCategorySpinner.setAdapter(adapter);
        meetingCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                meetingCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createClicked(view);
            }
        });
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateClicked(view);
            }
        });

        btnFrom = (Button) findViewById(R.id.btnMeetingFrom);
        btnTo = (Button) findViewById(R.id.btnMeetingTo);

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromClicked(view);
            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClicked(view);
            }
        });
    }

    void fromClicked(View view) {
        int hour = 00;
        int minute = 00;
        final TimePickerDialog timeFrom;
        timeFrom = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                hourFrom = selectedHour;
                minutesFrom = selectedMinute;
                stringtimeFrom=hourFrom+":"+minutesFrom;

            }
        }, hour, minute, true);
        timeFrom.show();


    }

    void toClicked(View view) {
        int hour = 00;
        int minute = 00;
        TimePickerDialog timeTo;
        timeTo = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hourTo = selectedHour;
                minutesTo = selectedMinute;
                stringtimeTo=hourTo+":"+minutesTo;

            }
        }, hour, minute, true);
        timeTo.show();

    }

    void dateClicked(View view) {
        Calendar calendar = Calendar.getInstance();
        int nYear = calendar.get(Calendar.YEAR);
        int nDay = calendar.get(Calendar.DAY_OF_MONTH);
        int nMonth = calendar.get(Calendar.MONTH);

        DatePickerDialog dialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                yearS = year;
                monthS = month;
                dayS = day;
                stringdateFrom=yearS + "-" + monthS + "-" + dayS;
            }
        }, nYear, nMonth, nDay);
        dialog.show();
    }
    void createClicked(View view){
        if (stringdateFrom!=null && stringtimeTo!=null && stringtimeFrom!=null && titleET!=null && meetingCategory!=null){
            if(mAuthTask!=null){
                return;
            }
            else {
                mAuthTask = new CreateMeetingTask(titleET.getText().toString(), stringdateFrom, stringtimeTo, stringtimeFrom,meetingCategory);
                mAuthTask.execute((Void) null);
            }
        }else{
            Toast.makeText(view.getContext(),"Fill in all the fields",Toast.LENGTH_LONG).show();
        }

    }

    public class CreateMeetingTask extends AsyncTask<Void, Void, Boolean> {

        private final String title;
        private final  String date;
        private final  String tFrom;
        private final String tTo;
        private final String category;

        CreateMeetingTask(String title, String date, String tFrom, String tTo, String category) {
            this.title=title;
            this.date=date;
            this.tFrom=tFrom;
            this.tTo=tTo;
            this.category=category;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/meetings/create";
            String data = null;
            String jsonStr = null;
            JSONObject jsonObj = null;
            try {
                data = URLEncoder.encode("title", "UTF-8")
                        + "=" + URLEncoder.encode(title, "UTF-8");
                data += "&" + URLEncoder.encode("ac", "UTF-8") + "="
                        + URLEncoder.encode(category, "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8") + "="
                        + URLEncoder.encode(date, "UTF-8");
                data += "&" + URLEncoder.encode("timeFrom", "UTF-8") + "="
                        + URLEncoder.encode(tFrom, "UTF-8");
                data += "&" + URLEncoder.encode("timeTo", "UTF-8") + "="
                        + URLEncoder.encode(tTo, "UTF-8");
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
                Intent i = new Intent(MeetingActivity.this, FirstScreenActivity.class);
                Toast.makeText(MeetingActivity.this, "Meeting Created", Toast.LENGTH_LONG).show();
                //i.putExtra("EXTRA_MESSAGE", user.email);
                startActivity(i);


            } else {
                Toast.makeText(MeetingActivity.this, "Meeting Creation Failed", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

    @Override
    public void createMyProfileClickedListener() {
        Intent i = new Intent(MeetingActivity.this, FirstScreenActivity.class);
        startActivity(i);
    }

    @Override
    public void createNotificationsClickedListener() {
        Intent i = new Intent(MeetingActivity.this, NotificationActivity.class);
        startActivity(i);
    }

    @Override
    public void createCreateClickedListener() {

    }

    @Override
    public void createChatClickedListener() {
        Intent i = new Intent(MeetingActivity.this, ChatActivity.class);
        startActivity(i);
    }

    @Override
    public void createEditClickedListener() {
        Intent i = new Intent(MeetingActivity.this, EditProfileActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationFragment fragment = (NavigationFragment) getFragmentManager().findFragmentById(R.id.fraNavigation);
        fragment.createClicked(null);
    }
}

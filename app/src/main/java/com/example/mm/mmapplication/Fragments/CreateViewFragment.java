package com.example.mm.mmapplication.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mm.mmapplication.Model.ActivityModel;
import com.example.mm.mmapplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Win8.1 on 18.07.2017.
 */

public class CreateViewFragment extends Fragment {

    Activity activity;
    Button createBtn;
    EditText titleET;
    Spinner activityCategorySpinner;
    Spinner activityTimeSpinner;
    String activityCategory;
    String activityTime;
    Button dateBtn;
    Button btnFrom;
    Button btnTo;
    int yearS;
    int monthS;
    int dayS;
    int hourFrom;
    int hourTo;
    int minutesFrom;
    int minutesTo;
    Date d;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_view, container, false);
        createBtn = (Button) view.findViewById(R.id.createBtn);
        dateBtn = (Button) view.findViewById(R.id.dateBn);
        titleET = (EditText) view.findViewById(R.id.titleET);
        this.activity = getActivity();

        activityCategorySpinner = (Spinner) view.findViewById(R.id.activity_category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.activity_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityCategorySpinner.setAdapter(adapter);
        activityCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityCategory = adapterView.getItemAtPosition(i).toString();
                //System.out.println("Odbrano e"+activityCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        activityTimeSpinner = (Spinner) view.findViewById(R.id.activity_time_spinner);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.activity_time, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityTimeSpinner.setAdapter(timeAdapter);
        activityTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityTime = adapterView.getItemAtPosition(i).toString();
                //System.out.println("Odbrano e"+activityTime);
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

        btnFrom = (Button) view.findViewById(R.id.btnFrom);
        btnTo = (Button) view.findViewById(R.id.btnTo);

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


        return view;


    }

    void fromClicked(View view) {
        int hour = 00;
        int minute = 00;
        TimePickerDialog timeFrom;
        timeFrom = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                hourFrom = selectedHour;
                minutesFrom = selectedMinute;

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
                //Date t = new GregorianCalendar(0,0,0,selectedHour,selectedMinute).getTime();
                minutesTo = selectedMinute;

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
                d = new GregorianCalendar(year, month, day).getTime();
                System.out.println(d);

            }
        }, nYear, nMonth, nDay);
        dialog.show();
    }

    void createClicked(View view) {
        try {
            ActivityModel activityModel = new ActivityModel();
            activityModel.setTitle(titleET.getText().toString());
            activityModel.setActivityCategory(activityCategory);
            activityModel.setActivityTime(activityTime);
            activityModel.setTimeFrom(hourFrom + ":" + minutesFrom);
            activityModel.setTimeTo(hourTo + ":" + minutesTo);
            if(monthS==0||dayS==0){
                Toast.makeText(view.getContext(),"Fill in Date field", Toast.LENGTH_LONG).show();
            }

            activityModel.setDate(yearS + "-" + monthS + "-" + dayS);
            //Date dm=new GregorianCalendar(year,month,day,hourFrom,minutesFrom).getTime();
            ((CreateListener) activity).create(activityModel);
        } catch (ClassCastException cce) {

        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public interface CreateListener {
        public void create(ActivityModel text);
    }


}

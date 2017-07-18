package com.example.mm.mmapplication.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.mm.mmapplication.Fragments.CreateViewFragment;
import com.example.mm.mmapplication.R;

/**
 * Created by Win8.1 on 18.07.2017.
 */

public class CreateActivityActivity extends AppCompatActivity implements CreateViewFragment.CreateListener{

    TextView tekst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_activity);
        tekst= (TextView) findViewById(R.id.textView2);
        CreateViewFragment createViewFragment = new CreateViewFragment();
       // setFragment(createViewFragment);

    }

    public void setFragment(Fragment f){
        FragmentManager fm = getFragmentManager();
        if(fm.findFragmentById(R.id.fragmentContainer)==null){
            fm.beginTransaction().add(R.id.fragmentContainer,f).commit();
        }

    }
    @Override
    public void create(String text) {
        tekst.setText(text);
    }
}

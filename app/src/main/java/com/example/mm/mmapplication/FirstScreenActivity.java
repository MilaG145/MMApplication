package com.example.mm.mmapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Win8.1 on 16.07.2017.
 */

public class FirstScreenActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen_activity );
        textView= (TextView) findViewById(R.id.UsertextView);
        Intent intent=getIntent();
        String mail=intent.getStringExtra("EXTRA_MESSAGE");
        textView.setText("Welcome "+ mail);

    }
}

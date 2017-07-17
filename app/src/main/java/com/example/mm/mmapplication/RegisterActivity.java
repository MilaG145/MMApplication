package com.example.mm.mmapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by Win8.1 on 17.07.2017.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText pass;
    EditText repass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        firstName= (EditText) findViewById(R.id.regName);
        lastName= (EditText) findViewById(R.id.regLastName);
        email= (EditText) findViewById(R.id.regEmail);
        pass= (EditText) findViewById(R.id.RegPassword);
        repass= (EditText) findViewById(R.id.ReRegPassword);
    }
}

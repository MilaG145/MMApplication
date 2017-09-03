package com.example.mm.mmapplication.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mm.mmapplication.Activities.FirstScreenActivity;
import com.example.mm.mmapplication.Activities.HttpHandler;
import com.example.mm.mmapplication.Activities.LoginActivity;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Win8.1 on 17.07.2017.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText pass;
    EditText repass;
    UserLoginTask mAuthTask;
    private View mProgressView;
    private View mRegisterFormView;
    private Intent intent;
    User user;
    TinyDB tinyDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mProgressView = findViewById(R.id.login_progress_register);
        mRegisterFormView = findViewById(R.id.email_register_form);
        firstName = (EditText) findViewById(R.id.regName);
        lastName = (EditText) findViewById(R.id.regLastName);
        email = (EditText) findViewById(R.id.regEmail);
        pass = (EditText) findViewById(R.id.RegPassword);
        repass = (EditText) findViewById(R.id.ReRegPassword);
        tinyDB=new TinyDB(getApplicationContext());


        Button register = (Button) findViewById(R.id.RegisterBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeIntent(view);
                attemptRegister();
            }
        });
    }

    public void initializeIntent(View view) {
        intent = new Intent(this, FirstScreenActivity.class);
    }

    void attemptRegister() {
        firstName.setError(null);
        lastName.setError(null);
        email.setError(null);
        pass.setError(null);
        repass.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (email.getText().toString().length() < 2 || !email.getText().toString().contains("@") || !email.getText().toString().contains(".com")) {
            email.setError("Enter valid email");
            cancel = true;
            focusView = email;
        } else if (firstName.getText().toString().length() < 2) {
            firstName.setError("Enter full First Name");
            cancel = true;
            focusView = firstName;

        } else if (lastName.getText().toString().length() < 2) {
            lastName.setError("Enter full Last Name");
            cancel = true;
            focusView = lastName;
        } else if (pass.getText().toString().length() < 6) {
            pass.setError("Enter password with 6 or more characters");
            cancel = true;
            focusView = pass;
        } else if (!pass.getText().toString().equals(repass.getText().toString())) {
            repass.setError("The two password fields don't match");
            cancel = true;
            focusView = repass;
        }
        if (cancel) {

            focusView.requestFocus();

        } else {
           showProgress(true);
            mAuthTask = new UserLoginTask(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),pass.getText().toString());
            mAuthTask.execute((Void) null);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mfirstname;
        private final String mlastname;
        JSONObject jsonObj;


        UserLoginTask(String firstname,String lastname,String email, String password) {
            mEmail = email;
            mPassword = password;
            mfirstname=firstname;
            mlastname=lastname;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
               HttpHandler sh = new HttpHandler();
//            //String url = "http://192.168.0.106:8080/users/login?email=mila@yahoo.com&password=pass";
              String url = Constants.IP_Adress+"/users/new";
              String data = null;
              String jsonStr = null;
            try {
                data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(mEmail, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode(mPassword, "UTF-8");
                data += "&" + URLEncoder.encode("firstName", "UTF-8") + "="
                        + URLEncoder.encode(mfirstname, "UTF-8");
                data += "&" + URLEncoder.encode("lastName", "UTF-8") + "="
                        + URLEncoder.encode(mlastname, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

           // Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);
//
            try {
                jsonStr = sh.makeServiceCall(url, data, "POST");
                // Simulate network access.
                jsonObj = new JSONObject(jsonStr);
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);
                //JSONArray jsonArray= jsonObj.getJSONArray("");
                //Thread.sleep(2000);
                user=new User();
                user.setId(jsonObj.getInt("id"));
                user.setEmail(jsonObj.getString("email"));
                user.setFirstName(jsonObj.getString("firstName"));
                user.setLastName(jsonObj.getString("lastName"));

            } catch (Exception e) {

            }
//            catch (JSONException e) {
//                e.printStackTrace();
////
//
//            // TODO: register the new account here.
//            System.out.println(Boolean.valueOf(jsonStr));

            return jsonObj!=null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //mAuthTask = null;
            showProgress(false);

            if (success) {

                intent.putExtra("EXTRA_MESSAGE", mEmail);
                tinyDB.putString("email",mEmail);
                tinyDB.putObject("user",user);
                startActivity(intent);
                finish();

            } else {
                email.setError(getString(R.string.user_exist));
                email.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            // mAuthTask = null;
            showProgress(false);
        }
    }


}

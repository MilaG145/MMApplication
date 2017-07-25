package com.example.mm.mmapplication.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Activities.HttpHandler;
import com.example.mm.mmapplication.Activities.LoginActivity;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.UsersListItem;
import com.example.mm.mmapplication.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Win8.1 on 22.07.2017.
 */

public class UsersAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<UsersListItem> items;
    private TinyDB tinyDB;
    private Context context;
    public UsersAdapter(Context c, Context c1) {
        layoutInflater = LayoutInflater.from(c);
        context=c1;
        this.items = new ArrayList<UsersListItem>();
        tinyDB = new TinyDB(c);
    }

    public void setItems(ArrayList<UsersListItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.users_list_item_layout, viewGroup, false);
            holder = new Holder();
            holder.fullname = (TextView) view.findViewById(R.id.fullNameTV);
            holder.button = (ImageButton) view.findViewById(R.id.addRemoveBtn);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final UsersListItem i = (UsersListItem) getItem(position);
        holder.fullname.setText(i.getFullName());
        if (!i.getFriend()) {
            holder.button.setImageResource(R.drawable.remove_friend);

        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = tinyDB.getObject("user", User.class);
                if (!i.getFriend()) {
                    System.out.println("Sent request from" + user.firstName + " to" + i.getFullName());
                    RemoveFriendTask removeFriendTask=new RemoveFriendTask(user,i);
                    removeFriendTask.execute((Void)null);


                } else {


                    System.out.println("Remove frined from" + user.firstName + " to" + i.getFullName());
                    AddFriendTask addFriendTask=new AddFriendTask(user,i);
                    addFriendTask.execute((Void)null);
                }

            }
        });
        return view;
    }

    static class Holder {
        TextView fullname;
        ImageButton button;
    }

    public class AddFriendTask extends AsyncTask<Void, Void, Boolean> {

        User u1;
        UsersListItem u2;

        AddFriendTask(User u11, UsersListItem u22) {
            u1 = u11;
            u2 = u22;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/friendRequest/create";
            String data = null;
            String jsonStr = null;
            try {
                data = URLEncoder.encode("userSenderId", "UTF-8")
                        + "=" + URLEncoder.encode(u1.id.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("userReceiverId", "UTF-8") + "="
                        + URLEncoder.encode(u2.getUserId().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                jsonStr = sh.makeServiceCall(url, data, "POST");
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);


            } catch (Exception e) {

            }

            return jsonStr != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Toast.makeText(context,"Friend request to "+u2.getFullName()+" sent",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context,"Friend request to "+u2.getFullName()+" wasn't sent",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(context,"Friend request to "+u2.getFullName()+" wasn't sent",Toast.LENGTH_LONG).show();
        }
    }


    public class RemoveFriendTask extends AsyncTask<Void, Void, Boolean> {

        User u1;
        UsersListItem u2;

        RemoveFriendTask(User u11, UsersListItem u22) {
            u1 = u11;
            u2 = u22;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Constants.IP_Adress + "/users/unfriend/"+u1.id.toString()+"/"+u2.getUserId().toString();
            String data = null;
            String jsonStr = null;
            try {
                data = URLEncoder.encode("id1", "UTF-8")
                        + "=" + URLEncoder.encode(u1.id.toString(), "UTF-8");
                data += "&" + URLEncoder.encode("id2", "UTF-8") + "="
                        + URLEncoder.encode(u2.getUserId().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                jsonStr = sh.makeServiceCall(url, data, "DELETE");
                Log.i(LoginActivity.class.getSimpleName(), "Response from url: " + jsonStr);


            } catch (Exception e) {

            }

            return jsonStr != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {


            if (success) {
                Toast.makeText(context,"Friend: "+u2.getFullName()+" removed",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context,"Friend "+u2.getFullName()+" wasn't removed",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(context,"Friend "+u2.getFullName()+" wasn't removed",Toast.LENGTH_LONG).show();
        }
    }
}

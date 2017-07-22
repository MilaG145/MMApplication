package com.example.mm.mmapplication.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 22.07.2017.
 */

public class UsersAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<UsersListItem> items;

    public UsersAdapter(Context c){
        layoutInflater=LayoutInflater.from(c);
        this.items=new ArrayList<UsersListItem>();
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
            view = layoutInflater.inflate(R.layout.users_list_item_layout,viewGroup, false);
            holder=new Holder();
            holder.fullname = (TextView) view.findViewById(R.id.fullNameTV);
            holder.button= (ImageButton) view.findViewById(R.id.addRemoveBtn);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Button clicked");
                }
            });
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        UsersListItem i = (UsersListItem) getItem(position);
        holder.fullname.setText(i.getFullName());
       // holder.time.setText(i.getTime());
        return view;
    }

    static class Holder{
        TextView fullname;
        ImageButton button;
    }
}

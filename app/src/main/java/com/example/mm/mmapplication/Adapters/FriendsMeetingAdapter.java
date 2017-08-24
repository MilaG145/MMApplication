package com.example.mm.mmapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.UsersListItem;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 24.08.2017.
 */

public class FriendsMeetingAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<UsersListItem> items;
    private TinyDB tinyDB;
    private Context context;
    public FriendsMeetingAdapter(Context c) {
        layoutInflater = LayoutInflater.from(c);
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
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class Holder {
        TextView fullname;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.friends_meeting_list_item, viewGroup, false);
            holder = new Holder();
            holder.fullname = (TextView) view.findViewById(R.id.fullNameTV);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final UsersListItem p = (UsersListItem) getItem(i);
        holder.fullname.setText(p.getFullName());
        return view;
    }
}

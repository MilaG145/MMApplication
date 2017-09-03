package com.example.mm.mmapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mm.mmapplication.Model.ChatListItem;
import com.example.mm.mmapplication.Model.PreviewModel;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 03.09.2017.
 */

public class ChatAdapter extends BaseAdapter {


    TinyDB tinyDB;
    User user;
    private LayoutInflater layoutInflater;
    private ArrayList<ChatListItem> items;
    private Context context;

    public ChatAdapter(Context c) {
        layoutInflater = LayoutInflater.from(c);
        items = new ArrayList<ChatListItem>();
        context=c;

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

    public void setItems(ArrayList<ChatListItem> items) {
        this.items = items;
    }

    static class Holder {
        TextView name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.user_simple_preview, viewGroup, false);
            holder = new Holder();
            holder.name = (TextView) view.findViewById(R.id.tvFullNameUser);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        final ChatListItem chatListItem = (ChatListItem) getItem(i);
        holder.name.setText(chatListItem.getName());
        return view;
    }
}

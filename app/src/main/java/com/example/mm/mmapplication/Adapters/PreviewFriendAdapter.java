package com.example.mm.mmapplication.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Activities.HttpHandler;
import com.example.mm.mmapplication.Constants;
import com.example.mm.mmapplication.Model.PreviewModel;
import com.example.mm.mmapplication.Model.TinyDB;
import com.example.mm.mmapplication.Model.User;
import com.example.mm.mmapplication.Model.categories.PreviewCategory;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 28.08.2017.
 */

public class PreviewFriendAdapter extends BaseAdapter {

    TinyDB tinyDB;
    User user;
    private LayoutInflater layoutInflater;
    private ArrayList<PreviewModel> items;
    private Context context;

    public PreviewFriendAdapter(Context c) {
        layoutInflater = LayoutInflater.from(c);
        items = new ArrayList<PreviewModel>();
        context=c;
        tinyDB = new TinyDB(c);
        user = tinyDB.getObject("user", User.class);

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

    public void setItems(ArrayList<PreviewModel> items) {
        this.items = items;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.preview_list_item, viewGroup, false);
            holder = new Holder();
            holder.title = (TextView) view.findViewById(R.id.tvTitle);
            holder.time = (TextView) view.findViewById(R.id.tvTime);
            holder.date = (TextView) view.findViewById(R.id.tvDate);

            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        final PreviewModel previewModel = (PreviewModel) getItem(i);
        holder.title.setText(previewModel.getTitle());
        holder.time.setText(previewModel.getTime());
        holder.date.setText(previewModel.getDate());



        return view;
    }

    static class Holder {
        TextView title;
        TextView time;
        TextView date;
    }
}

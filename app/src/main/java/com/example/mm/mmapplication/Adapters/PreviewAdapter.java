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

public class PreviewAdapter extends BaseAdapter {

    TinyDB tinyDB;
    User user;
    private LayoutInflater layoutInflater;
    private ArrayList<PreviewModel> items;
    private Context context;

    public PreviewAdapter(Context c) {
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
            holder.delete = (ImageButton) view.findViewById(R.id.btnDelete);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }
        final PreviewModel previewModel = (PreviewModel) getItem(i);
        holder.delete.setImageResource(R.drawable.acceptnotification);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete deleteTask = new Delete(previewModel);
                deleteTask.execute((Void)null);
            }
        });
        holder.title.setText(previewModel.getTitle());
        holder.time.setText(previewModel.getTime());
        holder.date.setText(previewModel.getDate());



        return view;
    }

    static class Holder {
        TextView title;
        TextView time;
        TextView date;
        ImageButton delete;
    }

    public class Delete extends AsyncTask<Void, Void, Boolean> {
        PreviewModel previewModel;

        Delete(PreviewModel previewModel1) {
            previewModel = previewModel1;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = null;
            System.out.println(previewModel.getId());
            if (previewModel.equals(PreviewCategory.Meeting)) {
                url = Constants.IP_Adress + "/meetings/delete/" + previewModel.getId();

            } else if (previewModel.equals(PreviewCategory.Activity)) {
                url = Constants.IP_Adress + "/activities/delete/" + user.id + "/" + previewModel.getId();
            }
            String jsonStr = null;
            try {
                jsonStr = sh.makeServiceCall(url, null, "DELETE");
                System.out.println(jsonStr);

            } catch (Exception e) {

            }

            return jsonStr != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                Toast.makeText(context, "You deleted the " + previewModel.getPreviewCategory() + " " + previewModel.getTitle(), Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context,"Action didn't succeed",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(context,"Action didn't succeed",Toast.LENGTH_LONG).show();

        }
    }
}

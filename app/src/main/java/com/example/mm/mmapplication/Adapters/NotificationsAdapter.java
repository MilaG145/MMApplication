package com.example.mm.mmapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mm.mmapplication.Model.Meeting;
import com.example.mm.mmapplication.Model.NotificationModel;
import com.example.mm.mmapplication.Model.categories.NotificationCategory;
import com.example.mm.mmapplication.R;

import java.util.ArrayList;

/**
 * Created by Win8.1 on 25.07.2017.
 */

public class NotificationsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<NotificationModel> items;
    private Context context;

    public NotificationsAdapter(Context c){
        layoutInflater=LayoutInflater.from(c);
        items=new ArrayList<NotificationModel>();

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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.notification_list_item_layout, viewGroup, false);
            holder = new Holder();
            holder.notificationText = (TextView) view.findViewById(R.id.tvNotification);
            holder.btnAccept = (ImageButton) view.findViewById(R.id.btnAcceptNotification);
            holder.btnDecline= (ImageButton) view.findViewById(R.id.btnDeclineNotification);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final NotificationModel i = (NotificationModel) getItem(position);
        holder.btnAccept.setImageResource(R.drawable.declinenotification);
        holder.btnDecline.setImageResource(R.drawable.acceptnotification);
        if(i.getCategory().equals(NotificationCategory.FriendRequest)){
            holder.notificationText.setText(view.getResources().getString(R.string.friend_request)+" "+i.getFriendRequest().getSender().firstName+" "+i.getFriendRequest().getSender().lastName);
            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Friend request accepted", Toast.LENGTH_LONG).show();
                }
            });
            holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Friend request declined", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(i.getCategory().equals(NotificationCategory.Meeting)){
            Meeting meeting=i.getMeeting();
            holder.notificationText.setText(meeting.getTitle()+" at "+meeting.getTimeFrom()+" "+meeting.getDate());
            holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Meeting accepted", Toast.LENGTH_LONG).show();
                }
            });
            holder.btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Meeting declined", Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }

    public void setItems(ArrayList<NotificationModel> items) {
        this.items = items;
    }

    static class Holder{
        TextView notificationText;
        ImageButton btnAccept;
        ImageButton btnDecline;

    }
}

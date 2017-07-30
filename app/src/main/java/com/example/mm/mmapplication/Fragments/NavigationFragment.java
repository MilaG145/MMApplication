package com.example.mm.mmapplication.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mm.mmapplication.R;

/**
 * Created by Win8.1 on 30.07.2017.
 */

public class NavigationFragment extends Fragment {
    ImageButton btnMyProfile;
    ImageButton btnNotifications;
    ImageButton btnCreate;
    ImageButton btnChat;
    ImageButton btnEdit;
    Activity activity;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_navigation, container, false);

        btnMyProfile = (ImageButton) view.findViewById(R.id.btnMyProfile);
        btnMyProfile.setBackgroundResource(R.color.common_google_signin_btn_text_dark_default);
        btnNotifications = (ImageButton) view.findViewById(R.id.btnNotifications);
        btnCreate = (ImageButton) view.findViewById(R.id.btnCreate);
        btnChat = (ImageButton) view.findViewById(R.id.btnChat);
        btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
        activity = getActivity();
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myProfileClicked(view);
            }
        });
        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationsClicked(view);
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClicked(view);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatClicked(view);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClicked(view);
            }
        });
        return view;
    }

    //@android:color/transparent
    public void myProfileClicked(View view) {
        btnMyProfile.setBackgroundResource(R.color.common_google_signin_btn_text_dark_default);
        btnNotifications.setBackgroundColor(Color.TRANSPARENT);
        btnCreate.setBackgroundColor(Color.TRANSPARENT);
        btnChat.setBackgroundColor(Color.TRANSPARENT);
        btnEdit.setBackgroundColor(Color.TRANSPARENT);
        ((CreateNavigationListener) activity).createMyProfileClickedListener();
    }

    public void notificationsClicked(View view) {
        btnMyProfile.setBackgroundColor(Color.TRANSPARENT);
        btnNotifications.setBackgroundResource(R.color.common_google_signin_btn_text_dark_default);
        btnCreate.setBackgroundColor(Color.TRANSPARENT);
        btnChat.setBackgroundColor(Color.TRANSPARENT);
        btnEdit.setBackgroundColor(Color.TRANSPARENT);
        ((CreateNavigationListener) activity).createNotificationsClickedListener();
    }

    public void createClicked(View view) {
        btnMyProfile.setBackgroundColor(Color.TRANSPARENT);
        btnNotifications.setBackgroundColor(Color.TRANSPARENT);
        btnCreate.setBackgroundResource(R.color.common_google_signin_btn_text_dark_default);
        btnChat.setBackgroundColor(Color.TRANSPARENT);
        btnEdit.setBackgroundColor(Color.TRANSPARENT);
        ((CreateNavigationListener) activity).createCreateClickedListener();
    }

    public void chatClicked(View view) {
        btnMyProfile.setBackgroundColor(Color.TRANSPARENT);
        btnNotifications.setBackgroundColor(Color.TRANSPARENT);
        btnCreate.setBackgroundColor(Color.TRANSPARENT);
        btnChat.setBackgroundResource(R.color.common_google_signin_btn_text_dark_default);
        btnEdit.setBackgroundColor(Color.TRANSPARENT);
        ((CreateNavigationListener) activity).createChatClickedListener();
    }

    public void editClicked(View view) {
        btnMyProfile.setBackgroundColor(Color.TRANSPARENT);
        btnNotifications.setBackgroundColor(Color.TRANSPARENT);
        btnCreate.setBackgroundColor(Color.TRANSPARENT);
        btnChat.setBackgroundColor(Color.TRANSPARENT);
        btnEdit.setBackgroundResource(R.color.common_google_signin_btn_text_dark_default);
        ((CreateNavigationListener) activity).createEditClickedListener();
    }

    public interface CreateNavigationListener {
        void createMyProfileClickedListener();

        void createNotificationsClickedListener();

        void createCreateClickedListener();

        void createChatClickedListener();

        void createEditClickedListener();
    }

}

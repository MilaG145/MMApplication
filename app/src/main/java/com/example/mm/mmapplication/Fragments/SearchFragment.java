package com.example.mm.mmapplication.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mm.mmapplication.R;

/**
 * Created by Win8.1 on 23.07.2017.
 */

public class SearchFragment extends Fragment {
    Activity activity;
    EditText searchET;
    ImageButton searchBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        searchET= (EditText) view.findViewById(R.id.searchET);
        searchBtn= (ImageButton) view.findViewById(R.id.searchBtn);
        this.activity=getActivity();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchClicked(view);
            }
        });
        return view;
    }
    public void searchClicked(View view){
        ((CreateSearchListener) activity).createSearchListener(searchET.getText().toString());
    }


    public interface CreateSearchListener {
        public void createSearchListener(String text);
    }
}

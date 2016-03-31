package com.example.vutruong.groupproject2.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vutruong.groupproject2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private Button btnCall, btnAbout;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
    }

    public void setView(View view) {
        btnAbout = (Button) view.findViewById(R.id.btnAbout);
        btnCall = (Button) view.findViewById(R.id.btnCall);

        btnCall.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAbout:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment()).addToBackStack(null).commit();
                break;
            case R.id.btnCall:
                Fragment fragment = getFragmentManager().findFragmentByTag("listPeople");
                if (fragment == null) {
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new ListPeopleFragment(), "listPeople").addToBackStack(null).commit();
                }
                break;
        }
    }
}

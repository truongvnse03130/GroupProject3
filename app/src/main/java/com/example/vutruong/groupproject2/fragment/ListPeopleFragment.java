package com.example.vutruong.groupproject2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vutruong.groupproject2.R;
import com.example.vutruong.groupproject2.adapter.ListPeopleAdapter;
import com.example.vutruong.groupproject2.entity.Person;
import com.example.vutruong.groupproject2.utilities.HttpGetApiTask;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by VuTruong on 25/03/2016.
 */
public class ListPeopleFragment extends Fragment implements HttpGetApiTask.SendingBackDataInterface {

    private static final String SAVE_LIST = "saveStateOfList";

    private ArrayList<Person> listData;
    private ListView listView;
    private ListPeopleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_people, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);

        if ((savedInstanceState != null ? savedInstanceState.getParcelableArrayList(SAVE_LIST) : null) == null) {
            final HttpGetApiTask ApiGetTask = new HttpGetApiTask();
            ApiGetTask.setDataInstance(this);
            ApiGetTask.execute("https://api.myjson.com/bins/53x82");
        } else {
            listData = savedInstanceState.getParcelableArrayList(SAVE_LIST);
            if (getContext() != null) {
                adapter = new ListPeopleAdapter(listData, getContext());
            }
            listView.setAdapter(adapter);
        }
    }

    public void setView(View view) {
        listView = (ListView) view.findViewById(R.id.listview);
    }

    @Override
    public void sendData(JSONArray jsonArray) {
        if (jsonArray != null) {
            listData = Person.fromJson(jsonArray);
            if (getContext() != null) {
                adapter = new ListPeopleAdapter(listData, getContext());
            }
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_LIST, listData);
    }
}

package com.example.vutruong.groupproject2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vutruong.groupproject2.R;
import com.example.vutruong.groupproject2.adapter.ListPeopleAdapter;
import com.example.vutruong.groupproject2.entity.Person;
import com.example.vutruong.groupproject2.utilities.HttpGetApiTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VuTruong on 25/03/2016.
 */
public class ListPeopleFragment extends Fragment implements HttpGetApiTask.CallbackDataInterface {

    private static final String SAVE_LIST = "saveStateOfList";
    private static final String DATA_URL = "https://api.myjson.com/bins/53x82";
//    private static final String DATA_URL = "https://api.myjson.com/bins/3werk";
//    private static final String DATA_URL = "https://api.myjson.com/bins/26gts";

    private ArrayList<Person> listData;
    private ListView listView;
    private ListPeopleAdapter adapter;
    private ProcessDialogFragment dialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        callApi();
        return true;
    }

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
            callApi();
        } else {
            listData = savedInstanceState.getParcelableArrayList(SAVE_LIST);
            if (getContext() != null) {
                adapter = new ListPeopleAdapter(listData, getContext());
            }
            listView.setAdapter(adapter);
        }
    }

    private void setView(View view) {
        listView = (ListView) view.findViewById(R.id.listview);
    }

    private void callApi() {
        final HttpGetApiTask GetApiTask = new HttpGetApiTask();
        GetApiTask.setDataInstance(this);
        GetApiTask.execute(DATA_URL);
    }

    @Override
    public void sendData(JSONArray jsonArray) {
        listView.setVisibility(View.VISIBLE);
        if (jsonArray != null) {
            Gson gson = new Gson();
            listData = gson.fromJson(jsonArray.toString(), new TypeToken<List<Person>>() {
            }.getType());
            if (getContext() != null) {
                adapter = new ListPeopleAdapter(listData, getContext());
            }
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void sendError(Exception ex) {

        dismissDialog();
    }

    @Override
    public void showDialog() {
        dialogFragment = ProcessDialogFragment.newInstance("Project 3", "Loading...");
        dialogFragment.show(getFragmentManager(), "showProcessDialog");
    }

    @Override
    public void dismissDialog() {
        if (dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_LIST, listData);
    }
}

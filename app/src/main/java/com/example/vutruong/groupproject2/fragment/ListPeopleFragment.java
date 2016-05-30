package com.example.vutruong.groupproject2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vutruong.groupproject2.R;
import com.example.vutruong.groupproject2.adapter.ListPeopleAdapter;
import com.example.vutruong.groupproject2.entity.Person;
import com.example.vutruong.groupproject2.utilities.ContentFrame;
import com.example.vutruong.groupproject2.utilities.HttpGetApiTask;
import com.example.vutruong.groupproject2.utilities.LayoutSwitcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VuTruong on 25/03/2016.
 */
public class ListPeopleFragment extends Fragment implements HttpGetApiTask.CallbackDataInterface, LayoutSwitcher.RetryButtonListener {

    private static final String SAVE_LIST = "saveStateOfList";
    private static final String DATA_URL = "https://api.myjson.com/bins/53x82";
//    private static final String DATA_URL = "https://api.myjson.com/bins/3werk";
//    private static final String DATA_URL = "https://api.myjson.com/bins/26gts";

    private ArrayList<Person> listData;
    private ListView listView;
    private ListPeopleAdapter adapter;
    private ViewGroup mDataView;
    private LayoutSwitcher mLayoutSwitcher;


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
        ContentFrame contentFrame = new ContentFrame(getContext());
        contentFrame.setDataLayout(inflater, R.layout.list_people,
                R.id.frame_container);
        mDataView = contentFrame.getDataLayout();
        mLayoutSwitcher = new LayoutSwitcher(contentFrame,
                R.id.frame_container, R.id.error_layout,
                R.id.loading_layout, this, LayoutSwitcher.DATA_MODE);

        return contentFrame;
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
        switchToLoading();
        final HttpGetApiTask GetApiTask = new HttpGetApiTask();
        GetApiTask.setDataInstance(this);
        GetApiTask.execute(DATA_URL);
    }

    @Override
    public void sendData(JSONArray jsonArray) {
        switchToData();
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
        switchToError(ex.toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_LIST, listData);
    }

    @Override
    public void onRetry() {
        callApi();
    }

    protected void switchToData() {
        if (mLayoutSwitcher != null)
            mLayoutSwitcher.switchToDataMode();
    }

    protected void switchToError(String msg) {
        if (mLayoutSwitcher != null) {
            mLayoutSwitcher.switchToErrorMode(msg);
        }
    }

    protected void switchToLoading() {
        if (mLayoutSwitcher != null)
            mLayoutSwitcher.switchToLoadingDelayed(350);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataView = null;
        mLayoutSwitcher = null;
    }
}

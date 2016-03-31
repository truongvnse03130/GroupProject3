package com.example.vutruong.groupproject2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vutruong.groupproject2.R;
import com.example.vutruong.groupproject2.entity.Person;

import java.util.ArrayList;

/**
 * Created by VuTruong on 30/03/2016.
 */
public class ListPeopleAdapter extends ArrayAdapter {

    private ArrayList<Person> data;
    private final Context context;

    public ListPeopleAdapter(ArrayList<Person> data, Context context) {
        super(context, R.layout.person_item, data);
        this.context = context;
        this.data = data;
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView role;
        protected TextView project;
        protected ImageView avatar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.person_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.role = (TextView) convertView.findViewById(R.id.role);
            holder.project = (TextView) convertView.findViewById(R.id.project);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Person person = data.get(position);
        holder.name.setText("Name: " +person.getName());
        holder.role.setText("Role: " +person.getProject().getRole());
        holder.project.setText("Project: "+person.getProject().getName());
        if (person.getGender() == 0) {
            holder.avatar.setImageResource(R.drawable.female);
        } else {
            holder.avatar.setImageResource(R.drawable.male);
        }

        return convertView;
    }
}

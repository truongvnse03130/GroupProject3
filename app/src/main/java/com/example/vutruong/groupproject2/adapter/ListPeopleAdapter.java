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
import com.example.vutruong.groupproject2.entity.Project;

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

        Person person = data.get(position);
        Project project = data.get(position).getProject();
        if (person != null) {
            if (person.getName() != null) {
                holder.name.setText("Name: " + person.getName());
            } else {
                holder.name.setText("No name");
            }

            if (person.getGender() != null) {
                if (!person.isMale()) {
                    holder.avatar.setImageResource(R.drawable.female);
                } else {
                    holder.avatar.setImageResource(R.drawable.male);
                }
            } else {
                holder.avatar.setImageResource(0);
            }

            if (project != null) {
                holder.project.setText("Project: " + project.getName());
                holder.role.setText("Role: " + project.getRole());
            } else {
                holder.project.setText("No project");
                holder.role.setText("No role");
            }
        } else {
            holder.avatar.setImageResource(0);
            holder.name.setText("No data");
            holder.project.setText("No data");
            holder.role.setText("No data");
        }

        return convertView;
    }

}

package com.example.vutruong.groupproject2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by VuTruong on 28/03/2016.
 */
public class Person implements Parcelable {
    private String name;
    private int gender;
    private Project project;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public static Person fromJson(JSONObject jsonObject) {
        Person person = new Person();
        try {
            person.setName(jsonObject.getString("name"));
            person.setGender(jsonObject.getInt("gender"));
            JSONObject obj = jsonObject.getJSONObject("projects");
            Project project = new Project();
            project.setName(obj.getString("name"));
            project.setRole(obj.getString("role"));
            person.setProject(project);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static ArrayList<Person> fromJson(JSONArray jsonArray) {
        JSONObject personJson;
        ArrayList<Person> persons = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                personJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Person person = Person.fromJson(personJson);
            if (person != null) {
                persons.add(person);
            }
        }
        return persons;
    }

    @Override
    public String toString() {
        return "Person{" +
                "project=" + project +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.gender);
        dest.writeParcelable(this.project, flags);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.gender = in.readInt();
        this.project = in.readParcelable(Project.class.getClassLoader());
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}

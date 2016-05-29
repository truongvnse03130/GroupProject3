package com.example.vutruong.groupproject2.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by VuTruong on 28/03/2016.
 */
public class Person implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("projects")
    private Project project;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isMale() {
        return gender.equals("0");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeParcelable(this.project, flags);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.gender = in.readString();
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

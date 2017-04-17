package com.example.zane.easyrouter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zane on 2017/4/17.
 * Email: zanebot96@gmail.com
 * Blog: zane96.github.io
 */

public class Person implements Parcelable {
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
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

package com.example.lab5;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//Model oceny

public class Marks implements Serializable {

    private String id;
    private int value;

    public Marks(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}

package com.codecool.wallhaven.model;

import java.lang.reflect.Field;

public class BaseModel {

    protected int id;
    protected String name;

    public BaseModel(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

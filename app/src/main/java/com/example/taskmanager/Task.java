package com.example.taskmanager;

/**
 * Created by 15031759 on 26/5/2017.
 */

public class Task {

    private int id;
    private String name;
    private String description;

    public Task(int id, String name, String description){
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

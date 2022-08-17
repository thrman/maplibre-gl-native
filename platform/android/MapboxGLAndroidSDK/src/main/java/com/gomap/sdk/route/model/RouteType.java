package com.gomap.sdk.route.model;

public enum RouteType {

    walking(1,"walking"),
    driving(2,"driving"),
    cycling(3,"cycling");

    private int id;
    private String  name;

    RouteType(int id ,String name){
        this.id = id;
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

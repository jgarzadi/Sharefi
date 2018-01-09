package com.sharefi.srcit.sharefi.models;

/**
 * Created by juan.garza on 23/08/2017.
 */

public class Connection {
    private Integer id;
    private String name;
    private String passcode;

    public Connection(){}

    public Connection(Integer id, String name, String passcode) {
        this.id = id;
        this.name = name;
        this.passcode = passcode;
    }

    public Connection(String name, String passcode) {
        this.name = name;
        this.passcode = passcode;
    }

    public Integer getId() { return id; }

    public void setId(Integer id){ this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
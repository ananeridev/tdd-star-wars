package com.anabneri.tddtarwars.models;

public class Jedi {

    private int id;
    private String name;
    private int strenght;
    private int version;

    public Jedi() { }

    public Jedi(int id, String name, int strenght, int version) {
        this.id = id;
        this.name = name;
        this.strenght = strenght;
        this.version = version;
    }

    public Jedi(String name, int strenght) {
        this.name = name;
        this.strenght = strenght;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStrenght() {
        return strenght;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

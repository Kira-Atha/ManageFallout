package be.huygebaert.gestionfallout.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bag implements Serializable {
    private int id;
    private int caps;
    private ArrayList<Item> items;

    public Bag(){
        items = new ArrayList<Item>();
    }

    public Bag(int caps){
        this.caps = caps;
        items = new ArrayList<Item>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaps() {
        return caps;
    }

    public void setCaps(int caps) {
        this.caps = caps;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
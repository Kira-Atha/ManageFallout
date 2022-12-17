package be.huygebaert.gestionfallout.Models;

import java.util.ArrayList;

public class Bag {
    private int id;
    private int caps;
    private ArrayList<TypeItem> items;

    public Bag(){}

    public Bag(int caps){
        this.caps = caps;
        items = new ArrayList<TypeItem>();
    }
}
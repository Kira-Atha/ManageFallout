package be.huygebaert.gestionfallout.Models;

public class Item {
    private int id;
    private String name;
    private String effect;
    private double weight;
    private double maxWeight;
    private double price;
    private int quantity;
    private TypeItem type;


    public Item(){}
    public Item(String name){
        this.name = name;
    }
}

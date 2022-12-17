package be.huygebaert.gestionfallout.Models;

public class Ability {
    private int id;
    private String name;
    private int level;
    private String effect;

    public Ability(){}
    public Ability(String name, int level, String effect){
        this.name = name;
        this.level = level;
        this.effect = effect;
    }
}

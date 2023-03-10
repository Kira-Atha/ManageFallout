package be.huygebaert.gestionfallout.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import be.huygebaert.gestionfallout.AccessDB.DAOSkill;
import be.huygebaert.gestionfallout.Tools.Fallout;

public class Skill implements Serializable {
    private int id;
    private String name;
    private int level;
    private boolean personalAsset;
    private static DAOSkill daoSkill = new DAOSkill(Fallout.getAppContext());

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
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public boolean isPersonalAsset() {
        return personalAsset;
    }
    public void setPersonalAsset(boolean personalAsset) {
        this.personalAsset = personalAsset;
    }

    public Skill(){ }
    public Skill(int id,String name, int level,boolean personalAsset){
        this.id = id;
        this.name = name;
        this.level = level;
        this.personalAsset = personalAsset;
    }

    public void levelUpSkill(int maxLevel){
        if(this.getLevel() < maxLevel){
            setLevel(this.getLevel()+1);
        }
    }


    public static ArrayList<Skill> findAll(){
        return daoSkill.findAll();
    }
    public static Skill find(int id){
        return daoSkill.find(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}

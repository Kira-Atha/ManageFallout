package be.huygebaert.gestionfallout.Models;

import static java.util.List.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import be.huygebaert.gestionfallout.AccessDB.DAOPlayer;
import be.huygebaert.gestionfallout.AccessDB.DAOSkill;
import be.huygebaert.gestionfallout.Fallout;

public class Skill implements Serializable {
    private int id;
    private String name;
    private int level;
    private int maxLevel = 6;
    private boolean personalAsset;
    //private static String[] allSkills = {"Armes à énergie[PER]","Armes de corps à corps[FOR]","Armes légères[AGI]","Armes lourdes[END]","Athlétisme[FOR]","Crochetage[PER]","Discours[CHR]","Discrétion[AGI]","Explosifs[PER]","Mains nues[FOR]","Médecine[INT]","Pilotage[PER]","Projectiles[AGI]","Réparation[INT]","Sciences[INT]","Survie[END]","Troc[CHR]"};
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

    public void levelUpSkill(){
        if(this.getLevel() < maxLevel){
            setLevel(this.getLevel()+1);
        }
    }


    public static ArrayList<Skill> findAll(){

        //DAO
        /*
        List<Skill> allSkills_list = new ArrayList<Skill>();
        for(int i=0;i<allSkills.length;i++){
            allSkills_list.add(new Skill(0,allSkills[i],0,false));
        }*/
        return daoSkill.findAll();
    }
}

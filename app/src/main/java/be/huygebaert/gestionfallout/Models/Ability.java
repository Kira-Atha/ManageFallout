package be.huygebaert.gestionfallout.Models;

import java.io.Serializable;
import java.util.ArrayList;

import be.huygebaert.gestionfallout.AccessDB.DAOAbility;
import be.huygebaert.gestionfallout.AccessDB.DAOPlayer;
import be.huygebaert.gestionfallout.Tools.Fallout;

public class Ability implements Serializable {
    private int id;
    private String name;
    private int levelMaxAbility,levelUpAbility,levelMinPlayer,bonusPersonalAsset,bonusSPECIAL;
    private String description;
    private int[] bonusSPECIALtab;
    private int[] preSPECIALtab;
    private static DAOAbility daoAbility = new DAOAbility(Fallout.getAppContext());

    public Ability(){}
    public Ability(int id,String name, int levelMaxAbility, int levelUpAbility, int levelMinPlayer,String description,int bonusFOR,int bonusPER,int bonusEND,int bonusCHR,int bonusINT,int bonusAGI,int bonusCHA,int bonusSPECIAL,int bonusPersonalAsset,int preFOR,int prePER,int preEND,int preCHR,int preINT,int preAGI,int preCHA){
        this.id = id;
        this.name = name;
        this.levelMaxAbility = levelMaxAbility;
        this.levelUpAbility= levelUpAbility;
        this.levelMinPlayer = levelMinPlayer;
        this.description = description;
        this.bonusPersonalAsset = bonusPersonalAsset;
        this.bonusSPECIAL = bonusSPECIAL;

        this.bonusPersonalAsset = bonusPersonalAsset;
        this.bonusSPECIALtab = new int[7];
        this.bonusSPECIALtab[0]=bonusFOR;
        this.bonusSPECIALtab[1]=bonusPER;
        this.bonusSPECIALtab[2]=bonusEND;
        this.bonusSPECIALtab[3]=bonusCHR;
        this.bonusSPECIALtab[4]=bonusINT;
        this.bonusSPECIALtab[5]=bonusAGI;
        this.bonusSPECIALtab[6]=bonusCHA;

        this.preSPECIALtab = new int[7];
        this.preSPECIALtab[0]=preFOR;
        this.preSPECIALtab[1]=prePER;
        this.preSPECIALtab[2]=preEND;
        this.preSPECIALtab[3]=preCHR;
        this.preSPECIALtab[4]=preINT;
        this.preSPECIALtab[5]=preAGI;
        this.preSPECIALtab[6]=preCHA;
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
    public int getLevelMaxAbility() {
        return levelMaxAbility;
    }
    public void setLevelMaxAbility(int levelMaxAbility) {
        this.levelMaxAbility = levelMaxAbility;
    }
    public int getLevelUpAbility() {
        return levelUpAbility;
    }
    public void setLevelUpAbility(int levelUpAbility) {
        this.levelUpAbility = levelUpAbility;
    }
    public int getLevelMinPlayer() {
        return levelMinPlayer;
    }
    public void setLevelMinPlayer(int levelMinPlayer) {
        this.levelMinPlayer = levelMinPlayer;
    }
    public int getBonusPersonalAsset() {
        return bonusPersonalAsset;
    }
    public void setBonusPersonalAsset(int bonusPersonalAsset) {
        this.bonusPersonalAsset = bonusPersonalAsset;
    }
    public int getBonusSPECIAL() {
        return bonusSPECIAL;
    }
    public void setBonusSPECIAL(int bonusSPECIAL) {
        this.bonusSPECIAL = bonusSPECIAL;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int[] getBonusSPECIALtab() {
        return bonusSPECIALtab;
    }
    public void setBonusSPECIALtab(int[] bonusSPECIALtab) {
        this.bonusSPECIALtab = bonusSPECIALtab;
    }
    public int[] getPreSPECIALtab() {
        return preSPECIALtab;
    }
    public void setPreSPECIALtab(int[] preSPECIALtab) {
        this.preSPECIALtab = preSPECIALtab;
    }


    public static ArrayList<Ability> findAll(){
        return daoAbility.findAll();
    }

}

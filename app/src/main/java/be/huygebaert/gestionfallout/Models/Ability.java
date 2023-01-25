package be.huygebaert.gestionfallout.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Ability(List<String> attributes){
        this.id = 0;
        this.name = attributes.get(0);
        this.description = attributes.get(1);
        this.levelMaxAbility = Integer.parseInt(attributes.get(2));
        this.levelUpAbility= Integer.parseInt(attributes.get(3));
        this.levelMinPlayer = Integer.parseInt(attributes.get(4));
        this.bonusPersonalAsset = Integer.parseInt(attributes.get(5));
        this.bonusSPECIAL = Integer.parseInt(attributes.get(6));

        this.bonusSPECIALtab = new int[7];
        this.bonusSPECIALtab[0]=Integer.parseInt(attributes.get(7));
        this.bonusSPECIALtab[1]=Integer.parseInt(attributes.get(8));
        this.bonusSPECIALtab[2]=Integer.parseInt(attributes.get(9));
        this.bonusSPECIALtab[3]=Integer.parseInt(attributes.get(10));
        this.bonusSPECIALtab[4]=Integer.parseInt(attributes.get(11));
        this.bonusSPECIALtab[5]=Integer.parseInt(attributes.get(12));
        this.bonusSPECIALtab[6]=Integer.parseInt(attributes.get(13));

        this.preSPECIALtab = new int[7];
        this.preSPECIALtab[0]=Integer.parseInt(attributes.get(14));
        this.preSPECIALtab[1]=Integer.parseInt(attributes.get(15));
        this.preSPECIALtab[2]=Integer.parseInt(attributes.get(16));
        this.preSPECIALtab[3]=Integer.parseInt(attributes.get(17));
        this.preSPECIALtab[4]=Integer.parseInt(attributes.get(18));
        this.preSPECIALtab[5]=Integer.parseInt(attributes.get(19));
        this.preSPECIALtab[6]=Integer.parseInt(attributes.get(20));
    }

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
    public static boolean createAllAbilitiesOnce(ArrayList<String> excelValues){
        return daoAbility.createAllAbilitiesOnce(excelValues);
    }
    public boolean createAbility(){
        return daoAbility.create(this);
    }

}

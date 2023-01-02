package be.huygebaert.gestionfallout.Models;

public class Ability {
    private int id;
    private String name;
    private int currLevel;
    private int maxLevel;
    private int upLevel;
    private String description;
    private int bonusPersonalAsset;
    private int[] bonusSPECIALtab;

    public Ability(){}
    public Ability(String name, int currlevel, String description,int bonusFOR,int bonusPER,int bonusEND,int bonusCHR,int bonusINT,int bonusAGI,int bonusCHA,int bonusPersonalAsset){
        this.name = name;
        this.currLevel = currlevel;
        this.description = description;
        this.bonusPersonalAsset = bonusPersonalAsset;
        this.bonusSPECIALtab = new int[7];
        this.bonusSPECIALtab[0]=bonusFOR;
        this.bonusSPECIALtab[1]=bonusPER;
        this.bonusSPECIALtab[2]=bonusEND;
        this.bonusSPECIALtab[3]=bonusCHR;
        this.bonusSPECIALtab[4]=bonusINT;
        this.bonusSPECIALtab[5]=bonusAGI;
        this.bonusSPECIALtab[6]=bonusCHA;
    }
}

package be.huygebaert.gestionfallout.Models;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.huygebaert.gestionfallout.AccessDB.DAOPlayer;
import be.huygebaert.gestionfallout.Fallout;
import be.huygebaert.gestionfallout.Form;

public class Player implements Serializable {
    private int id;
    private String pseudo;
    private String race;
    private int level = 1;
    private int exp=0;
    private int strong=5;
    private int endurance=5;
    private int perception=5;
    private int charisma=5;
    private int intelligence=5;
    private int agility=5;
    private int luck=5;
    private int initiative=0;
    private int def_ra = 0;
    private int ra_curr = 0;
    private int hpMax=0;
    private int hpCurr=0;
    private int pa=2;
    private int maxExp=0;
    private int def = 1;
    private int maxAsset = 3;
    private List<Skill> playerSkills;
    private static DAOPlayer daoPlayer = new DAOPlayer(Fallout.getAppContext());

    public Player(){
        this.pseudo ="choisir";
        this.race = "choisir";
        this.hpCurr = hpMax;
        playerSkills = Skill.findAll();
    }
    public Player(String pseudo, String race, int level, int strong, int perception, int endurance,int charisma, int intelligence, int agility, int luck,int expCurr){
        this.pseudo = pseudo;
        this.race = race;
        this.level = level;
        this.strong = strong;
        this.perception = perception;
        this.endurance = endurance;
        this.charisma = charisma;
        this.intelligence = intelligence;
        this.agility = agility;
        this.luck = luck;
        this.exp = expCurr;
        playerSkills = new ArrayList<Skill>();
        this.hpMax = getHpMax();
        this.initiative = getInitiative();
        //RECUP PA BDD
    }
    public Player(int id,String pseudo, String race, int level, int strong, int perception, int endurance,int charisma, int intelligence, int agility, int luck,int expCurr){
        this.pseudo = pseudo;
        this.race = race;
        this.level = level;
        this.strong = strong;
        this.perception = perception;
        this.endurance = endurance;
        this.charisma = charisma;
        this.intelligence = intelligence;
        this.agility = agility;
        this.luck = luck;
        this.exp = expCurr;
        playerSkills = new ArrayList<Skill>();
        this.hpMax = getHpMax();
        this.initiative = getInitiative();
    }
    public String getPseudo() {
        return pseudo;
    }
    public int getDef_ra() {
        return def_ra;
    }
    public void setDef_ra(int def_ra) {
        this.def_ra = def_ra;
    }
    public int getRa_curr() {
        return ra_curr;
    }
    public void setRa_curr(int ra_curr) {
        this.ra_curr = ra_curr;
    }
    public String getRace() {
        return race;
    }
    public int getLevel() {
        return level;
    }
    public int getExp() {
        return exp;
    }
    public int getStrong() {
        return strong;
    }
    public int getPerception() {
        return perception;
    }
    public int getEndurance() {
        return endurance;
    }
    public int getCharisma() {
        return charisma;
    }
    public int getIntelligence() {
        return intelligence;
    }
    public int getAgility() {
        return agility;
    }
    public int getLuck() {
        return luck;
    }
    public int getInitiative() {
        initiative = this.agility + this.perception;
        return initiative;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getHpMax() {
        setHpMax(this.getLuck()+this.getEndurance() + (this.getLevel()-1));
        return hpMax;
    }
    public int getHpCurr(){
        return hpCurr;
    }
    public int getPa() {
        return pa;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public void setRace(String race) {
        this.race = race;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public void setStrong(int strong) {
        this.strong = strong;
    }
    public void setPerception(int perception) {
        this.perception = perception;
    }
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setLuck(int luck) {
        this.luck = luck;
    }
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    public void setHpMax(int hp) {
        this.hpMax = hp;
    }
    public void setHpCurr(int hp){
        this.hpCurr = hp;
    }
    public void setPa(int pa) {
        this.pa = pa;
    }
    public int getMaxExp() {
        //int[] expScale = {0,100,300,600,1000,1500,2100,2800,3600,4500,0,0,7800,9100,10500,12000,13600,15300,17100,19000,21000};
        this.maxExp = ((this.getLevel()+1) * this.getLevel()/2)*100;
        return maxExp;
    }
    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }
    public int getDef() {
        if(agility>=9){
            setDef(2);
        }
        return def;
    }
    public void setDef(int def) {
        this.def = def;
    }
    public void earnExp(int earnedExp){
        int totalExp = this.getExp()+earnedExp;
        if(totalExp >= this.getMaxExp()){
            System.out.println("Level up");
            int remainingExp = totalExp - this.getMaxExp();
            this.levelUp();
            this.setExp(remainingExp);
            this.setMaxExp(this.getMaxExp());

            //Si plusieurs niveaux pris
            while(remainingExp >= this.getMaxExp()){
                if(this.getLevel()<=20) {
                    remainingExp -= this.getMaxExp();
                    this.levelUp();
                    this.setExp(remainingExp);
                    this.setMaxExp(this.getMaxExp());
                }
            }
        }else{
            this.setExp(totalExp);
        }
    }
    public void levelUp(){
        this.setLevel(this.getLevel()+1);
    }
    public void takingDamage(int damage){
        int newHp = this.getHpCurr() - damage;
        if(newHp < 0){
            newHp = 0;
        }
        this.setHpCurr(newHp);
    }
    public void getHealed(int heal){
        int newHp = this.getHpCurr() + heal;
        if(newHp > this.getHpMax()){
            newHp = this.getHpMax();
        }
        this.setHpCurr(newHp);
    }
    public void receivePA(int pa){
        if(this.pa < 6){
            if(this.pa + pa >=6 ){
                this.setPa(6);
            }else{
                this.setPa(this.pa+pa);
            }
        }
    }
    public boolean usePA(int pa){
        if(this.pa - pa >=0){
            this.pa = this.pa - pa;
            return true;
        }else{
            return false;
        }
    }
    public static int[] launchDice(int numberOfDice, int lowerBound, int upperBound){
        Random random = new Random();
        int [] launchesDice = new int[numberOfDice];
        for(int i = 0 ; i< numberOfDice; i++){
            launchesDice[i] = random.nextInt(upperBound-lowerBound)+lowerBound;
        }
        return launchesDice;
    }
    public int getMaxAsset() {
        return maxAsset;
    }
    public void setMaxAsset(int maxAsset) {
        this.maxAsset = maxAsset;
    }
    public List<Skill> getPlayerSkills() {
        return playerSkills;
    }
    public void setPlayerSkills(List<Skill> playerSkills) {
        this.playerSkills = playerSkills;
    }


    public boolean choosePersonalAsset(Skill skill){
        int countPersonalAsset = 0;

        for (Skill sk:this.getPlayerSkills()) {
            if(sk.isPersonalAsset()){
                countPersonalAsset+=1;
                System.out.println("Total personnal asset = "+countPersonalAsset);
            }
        }
        if(countPersonalAsset < maxAsset ){
            this.makePersonalAsset(skill);
            System.out.println("C'est possible");
            return true;
        }
        return false;
    }
    public void makePersonalAsset(Skill skill){
        this.getPlayerSkills().get(skill.getId()).isPersonalAsset();
    }
    public int[] getDice20Results(int[] launchesDice,int num_skill, int num_SPECIAL,int complication){
        int[] results = new int[3];

        int[] SPECIAL = this.getSPECIALTab();
        int count_failure = 0;
        int count_critical = 0;
        int count_success = 0;
        for(int i = 0; i< launchesDice.length;i++){
            if(launchesDice[i]==1){
                count_critical+=1;
            }else if(launchesDice[i] >= 20-complication){
                count_failure+=1;
            }else{
                if(launchesDice[i] <= SPECIAL[num_SPECIAL] + playerSkills.get(num_skill).getLevel()){
                    count_success+=1;
                }
            }
        }
        if(playerSkills.get(num_skill).isPersonalAsset() && count_success>0 || playerSkills.get(num_skill).isPersonalAsset() && count_critical>0 ){
            count_failure=0;
        }

        results[0] = count_critical;
        results[1] = count_success;
        results[2] = count_failure;

        System.out.println("Coup critique = "+count_critical);
        System.out.println("Echec critique = "+count_failure);
        System.out.println("Réussite = "+count_success);

        if(playerSkills.get(num_skill).isPersonalAsset()){
            count_critical = count_critical * 2;
        }
        this.receivePA(count_critical);
        return results;
    }

    public int[] getSPECIALTab(){
        int[] SPECIAL = {getStrong(),getPerception(),getEndurance(),getCharisma(),getIntelligence(),getAgility(),getLuck()};
        return SPECIAL;
    }

    public int getStock_skill_points(){
        //Ne pas oublier les points générés par les abilités lorsque ça sera implémanté
        return this.getLevel()+3;
    }

    public boolean useStock_skill_points(Skill skill){
        int used_point = getUsed_stock_skill_points();
        System.out.println(used_point);
        int total_point = getStock_skill_points();
        System.out.println(total_point);
        if(total_point>used_point){
            this.playerSkills.get(skill.getId()).levelUpSkill();
            return true;
        }
        return false;
    }

     public int getUsed_stock_skill_points(){
        int total_used=0;
        for(Skill sk : this.getPlayerSkills()){
            total_used+=sk.getLevel();
        }
        return total_used;
    }

///TRY DAO

    public boolean create(){
        return daoPlayer.create(this);
    }

    public boolean delete( ){
        return daoPlayer.delete(this);
    }

    public static Player find(int id){
        return daoPlayer.find(id);
    }

    public static List<Player> findAll(){
        return daoPlayer.findAll();
    }
}
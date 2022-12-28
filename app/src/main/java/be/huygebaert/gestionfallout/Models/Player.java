package be.huygebaert.gestionfallout.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.huygebaert.gestionfallout.AccessDB.DAOPlayer;
import be.huygebaert.gestionfallout.AccessDB.DAOSkill;
import be.huygebaert.gestionfallout.Tools.Fallout;

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
    private int initiative;
    private int def_ra = 0;
    private int ra_curr = 0;
    private int hpMax;
    private int hpCurr;
    private int paCurr;

    private int maxExp=0;
    private int def = 1;
    private int maxAsset = 3;
    private List<Skill> playerSkills;
    private static DAOPlayer daoPlayer = new DAOPlayer(Fallout.getAppContext());
    private static DAOSkill daoSkill = new DAOSkill(Fallout.getAppContext());

    public Player(){
        this.pseudo ="choisir";
        this.race = "choisir";
        this.hpMax = getHpMax();
        this.initiative = getInitiative();
        this.hpCurr = hpMax;
        playerSkills = Skill.findAll();
    }
    public Player(int id,String pseudo, String race, int level, int strong, int perception, int endurance,int charisma, int intelligence, int agility, int luck,int expCurr,int hpCurr,int paCurr){
        this.id = id;
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

        if(findPlayerSkills().size()>0){
            playerSkills = findPlayerSkills();
        }else{
            System.out.println("Pas de skill pour ce joueur en db");
            playerSkills = Skill.findAll();
        }
        this.hpMax = getHpMax();
        this.initiative = getInitiative();
        this.hpCurr = hpCurr;
        this.paCurr = paCurr;
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
    public int getPaCurr() {
        return paCurr;
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
        update();
    }
    public void setPerception(int perception) {
        this.perception = perception;
        update();
    }
    public void setEndurance(int endurance) {
        this.endurance = endurance;
        update();
    }
    public void setCharisma(int charisma) {
        this.charisma = charisma;
        update();
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        update();
    }
    public void setAgility(int agility) {
        this.agility = agility;
        update();
    }
    public void setLuck(int luck) {
        this.luck = luck;
        update();
    }
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    public void setHpMax(int hp) {
        this.hpMax = hp;
    }
    public void setHpCurr(int hp) {
        this.hpCurr = hp;
        update();
    }
    public void setPaCurr(int pa) {
        this.paCurr = pa;
        update();
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
            //System.out.println("Level up");
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
        update();
    }
    public void levelUp(){
        this.setLevel(this.getLevel()+1);
        this.getHealed(1);
        this.getStock_skill_points();
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
        if(this.paCurr < 6){
            if(this.paCurr + pa >=6 ){
                this.setPaCurr(6);
            }else{
                this.setPaCurr(this.paCurr+pa);
            }
        }
    }
    public boolean usePA(int pa){
        if(this.paCurr - pa >=0){
            this.paCurr = this.paCurr - pa;
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
        if(this.getCurrentPersonalAsset() < maxAsset ){
            this.makePersonalAsset(skill);
            //System.out.println("C'est possible");
            daoSkill.update(skill);
            return true;
        }
        return false;
    }
    public int getCurrentPersonalAsset() {
        int countPersonalAsset = 0;

        for (Skill sk : this.getPlayerSkills()) {
            if (sk.isPersonalAsset()) {
                countPersonalAsset += 1;
            }
        }
        //System.out.println(countPersonalAsset);
        return countPersonalAsset;
    }
    public void makePersonalAsset(Skill skill){
        this.getPlayerSkills().get(skill.getId()-1).setPersonalAsset(true);
        //skill.update();
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
        return (this.getLevel()+3)-1;
    }

    public boolean useStock_skill_points(Skill skill){
        int used_point = getUsed_stock_skill_points();
        //System.out.println(used_point);
        int total_point = getStock_skill_points();
        //System.out.println(total_point);
        if(total_point>used_point){
            this.playerSkills.get(skill.getId()-1).levelUpSkill();
            //System.out.println("L'id du skill " +skill.getName()+" est : "+String.valueOf(skill.getId()));
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

    public boolean create(){
        boolean success = daoPlayer.create(this);
        daoSkill.createPlayerSkills(this);
        return success;
    }

    public boolean update(){
        return daoPlayer.update(this);
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

    private List<Skill> findPlayerSkills(){
        return daoSkill.findPlayerSkills(this);
    }

}
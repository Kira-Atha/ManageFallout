package be.huygebaert.gestionfallout.AccessDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import be.huygebaert.gestionfallout.Models.Player;
import be.huygebaert.gestionfallout.Models.Skill;

public class DAOSkill extends DAO<Skill> {

    public DAOSkill(@Nullable Context context) {
        super(context);
    }


    //Create player skill for each character
    @Override
    public boolean create(Skill skill) {
        return false;
    }

    @Override
    public boolean delete(Skill obj) {
        return false;
    }

    @Override
    public boolean update(Skill obj) {
        return false;
    }

    @Override
    public Skill find(int id) {
        return null;
    }

    @Override
    public ArrayList<Skill> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Skill skill = null;
        ArrayList<Skill> allSkills = new ArrayList<Skill>();
        String request = "SELECT * FROM Skill";
        Cursor c = db.rawQuery(request,null);

        while(c.moveToNext()) {
            skill = new Skill(c.getInt(0),c.getString(1),0,0);
            allSkills.add(skill);
        }
        return allSkills;
    }

    public ArrayList<Skill> findPlayerSkills(Player player){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Skill> playerSkills = new ArrayList<Skill>();
        Skill skill  = null;

        String query =  "SELECT Skill.IdSkill,Skill.NameSkill,Player_Skill.LevelSkill,Player_Skill.PersonalAssetSkill " +
                        "from Player_Skill INNER JOIN Skill " +
                        "ON Player_Skill.IdSkill = Skill.IdSkill " +
                        "WHERE IdPlayer=?"+player.getId();
        Cursor c = db.rawQuery(query,null);

        while(c.moveToNext()){
            skill = new Skill(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3));
            playerSkills.add(skill);
        }
        return playerSkills;
    }

    //For the first creation -> Create player skill in order to increase their level
    public void createPlayerSkills(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int i = 0; i<player.getPlayerSkills().size();i++){
            values.put("IdSkill",player.getPlayerSkills().get(i).getId());
            values.put("IdPlayer",player.getId());
            values.put("LevelSkill",0);
            values.put("PersonalAssetSkill",0);
            db.insert("Player_Skill",null,values);
        }
        db.close();
    }

    public boolean updatePlayerSkill(){
        return false;
    }
}

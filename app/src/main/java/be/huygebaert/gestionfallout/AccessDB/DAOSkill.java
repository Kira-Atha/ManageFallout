package be.huygebaert.gestionfallout.AccessDB;

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

    @Override
    public boolean create(Skill obj) {
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
            skill = new Skill(c.getInt(0),c.getString(1),0,false);
            allSkills.add(skill);
        }
        return allSkills;
    }
}

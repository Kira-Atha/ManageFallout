package be.huygebaert.gestionfallout.AccessDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import be.huygebaert.gestionfallout.Models.Ability;
import be.huygebaert.gestionfallout.Models.Player;

public class DAOAbility extends DAO<Ability> {

    public DAOAbility(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean create(Ability obj) {
        return false;
    }

    @Override
    public boolean delete(Ability obj) {
        return false;
    }

    @Override
    public boolean update(Ability obj) {
        return false;
    }

    @Override
    public Ability find(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query("Ability",new String[]{"IdAbility","NameAbility","LevelMaxAbility","LevelUpAbility","LevelMinPlayer","DescriptionAbility","BonusFOR","BonusPER","BonusEND","BonusCHR","BonusINT","BonusAGI","BonusCHA","BonusSPECIAL","BonusPersonalAsset","PreFOR","PrePER","PreEND","PreCHR","PreINT","PreAGI","PreCHA"},"IdAbility=?",new String[]{String.valueOf(id)},null,null,null);
        c.moveToFirst();
        Ability ability = new Ability(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),c.getInt(4),c.getString(5),c.getInt(6),c.getInt(7),c.getInt(8),c.getInt(9),c.getInt(10),c.getInt(11),c.getInt(12),c.getInt(13),c.getInt(14),c.getInt(15),c.getInt(16),c.getInt(17),c.getInt(18),c.getInt(19),c.getInt(20),c.getInt(21));

        return ability;
    }

    @Override
    public ArrayList<Ability> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Ability ability = null;
        ArrayList<Ability> allAbilities = new ArrayList<Ability>();
        String request = "SELECT * FROM Ability";
        Cursor c = db.rawQuery(request,null);

        while(c.moveToNext()){
            ability = new Ability(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),c.getInt(4),c.getString(5),c.getInt(6),c.getInt(7),c.getInt(8),c.getInt(9),c.getInt(10),c.getInt(11),c.getInt(12),c.getInt(13),c.getInt(14),c.getInt(15),c.getInt(16),c.getInt(17),c.getInt(18),c.getInt(19),c.getInt(20),c.getInt(21));
            allAbilities.add(ability);

        }
        return allAbilities;
    }

    public boolean choosePlayerAbility(Player player,Ability ability){
        return false;
    }

    public ArrayList<Ability> findPlayerAbilities(Player player){
        return null;
    }
}

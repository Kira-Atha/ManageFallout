package be.huygebaert.gestionfallout.AccessDB;

import android.content.ContentValues;
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
    public boolean create(Ability ability) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NameAbility",ability.getName());
        values.put("LevelMaxAbility",ability.getLevelMaxAbility());
        values.put("LevelUpAbility",ability.getLevelUpAbility());
        values.put("LevelMinPlayer",ability.getLevelMinPlayer());
        values.put("DescriptionAbility",ability.getDescription());
        values.put("BonusFOR",ability.getBonusSPECIALtab()[0]);
        values.put("BonusPER",ability.getBonusSPECIALtab()[1]);
        values.put("BonusEND",ability.getBonusSPECIALtab()[2]);
        values.put("BonusCHR",ability.getBonusSPECIALtab()[3]);
        values.put("BonusINT",ability.getBonusSPECIALtab()[4]);
        values.put("BonusAGI",ability.getBonusSPECIALtab()[5]);
        values.put("BonusCHA",ability.getBonusSPECIALtab()[6]);
        values.put("BonusSPECIAL",ability.getBonusSPECIAL());
        values.put("BonusPersonalAsset",ability.getBonusPersonalAsset());
        values.put("PreFOR",ability.getPreSPECIALtab()[0]);
        values.put("PrePER",ability.getPreSPECIALtab()[1]);
        values.put("PreEND",ability.getPreSPECIALtab()[2]);
        values.put("PreCHR",ability.getPreSPECIALtab()[3]);
        values.put("PreINT",ability.getPreSPECIALtab()[4]);
        values.put("PreAGI",ability.getPreSPECIALtab()[5]);
        values.put("PreCHA",ability.getPreSPECIALtab()[0]);

        long newId = db.insert("Ability",null,values);
        if(newId!=0){
            db.close();
            ability.setId((int)newId);
            return true;
        }
        db.close();
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

    public boolean createAllAbilitiesOnce(ArrayList<String> excelValues){
        if(Ability.findAll().size()==0){
            int countCreate = 0;
            Ability ability;
            int countOfParam = 21;
            for(int i=countOfParam+1;i<excelValues.size()-countOfParam;i +=countOfParam+1){
                ability = new Ability(excelValues.subList(i,i+21));
                ability.createAbility();
                System.out.println(++countCreate+" has been created !");
            }
            return true;
        }
        return false;
    }
}

package be.huygebaert.gestionfallout.AccessDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import be.huygebaert.gestionfallout.Fallout;
import be.huygebaert.gestionfallout.Models.Player;

public class DAOPlayer extends DAO<Player> {

    public DAOPlayer(@Nullable Context context) {
        super(context);
    }

    public boolean create(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("PseudoPlayer", player.getPseudo());
        values.put("RacePlayer", player.getRace());
        values.put("LevelPlayer", player.getLevel());
        values.put("StrongPlayer", player.getStrong());
        values.put("EndurancePlayer", player.getEndurance());
        values.put("PerceptionPlayer", player.getPerception());
        values.put("CharismaPlayer", player.getCharisma());
        values.put("IntelligencePlayer", player.getIntelligence());
        values.put("AgilityPlayer", player.getAgility());
        values.put("LuckPlayer", player.getLuck());
        values.put("ExpCurrPlayer", player.getExp());
        values.put("HpCurrPlayer",player.getHpCurr());
        values.put("PaCurrPlayer",player.getPaCurr());

        if (db.insert("Player", null, values) > 0) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public boolean update(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("PseudoPlayer", player.getPseudo());
        values.put("RacePlayer", player.getRace());
        values.put("LevelPlayer", player.getLevel());
        values.put("StrongPlayer", player.getStrong());
        values.put("EndurancePlayer", player.getEndurance());
        values.put("PerceptionPlayer", player.getPerception());
        values.put("CharismaPlayer", player.getCharisma());
        values.put("IntelligencePlayer", player.getIntelligence());
        values.put("AgilityPlayer", player.getAgility());
        values.put("LuckPlayer", player.getLuck());
        values.put("ExpCurrPlayer", player.getExp());
        values.put("HpCurrPlayer",player.getHpCurr());
        values.put("PaCurrPlayer",player.getPaCurr());

        if (db.update("Player", values, "IdPlayer =?", new String[]{String.valueOf(player.getId())}) > 0) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public boolean delete(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (db.delete("Player", "IdPlayer =?", new String[]{String.valueOf(player.getId())}) > 0) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public Player find(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query("Player", new String[]{"IdPlayer", "PseudoPlayer", "RacePlayer", "LevelPlayer", "StrongPlayer", "PerceptionPlayer", "EndurancePlayer", "CharismaPlayer", "IntelligencePlayer", "AgilityPlayer", "LuckPlayer", "ExpCurrPlayer","HpCurrPlayer","PaCurrPlayer"}, "IdPlayer=?", new String[]{String.valueOf(id)}, null, null, null);
        c.moveToFirst();
        Player player = new Player(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3),c.getInt(4),c.getInt(5),c.getInt(6),c.getInt(7),c.getInt(8),c.getInt(9),c.getInt(10),c.getInt(11),c.getInt(12),c.getInt(13));
        return player;
    }

    public ArrayList<Player> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Player player = null;
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        String request = "SELECT * FROM Player";
        Cursor c = db.rawQuery(request,null);

        while(c.moveToNext()) {
            player = new Player(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8), c.getInt(9), c.getInt(10),c.getInt(11),c.getInt(12),c.getInt(13));
            allPlayers.add(player);
        }
        return allPlayers;
    }
}

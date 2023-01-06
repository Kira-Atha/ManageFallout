package be.huygebaert.gestionfallout.AccessDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import be.huygebaert.gestionfallout.Models.Note;
import be.huygebaert.gestionfallout.Models.Player;

public class DAONote extends DAO<Note>{
    public DAONote(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean create(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TitleNote", note.getTitle());
        values.put("DescriptionNote",note.getDescription());
        values.put("IdPlayer",note.getPlayer().getId());
        long newId = db.insert("Note",null,values);
        if(newId !=1){
            db.close();
            note.setId((int)newId);
            return true;
        }
        db.close();
        return false;
    }

    @Override
    public boolean delete(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        if(db.delete("Note","IdNote=?",new String[]{String.valueOf(note.getId())})>0){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    @Override
    public boolean update(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TitleNote", note.getTitle());
        values.put("DescriptionNote",note.getDescription());

        if(db.update("Note",values,"IdNote=?",new String[]{String.valueOf(note.getId())})>0){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    @Override
    public Note find(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("Note",new String[]{"IdNote","TitleNote","DescriptionNote","IdPlayer"},"IdNote=?",new String[]{String.valueOf(id)},null,null,null);
        c.moveToFirst();

        Note note = new Note(c.getInt(0),c.getString(1),c.getString(2),Player.find(c.getInt(3)));

        return note;
    }

    @Override
    public ArrayList<Note> findAll() {
        return null;
    }

    public ArrayList<Note> findPlayerNote(Player player){
        return null;
    }
}

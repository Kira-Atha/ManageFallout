package be.huygebaert.gestionfallout.Models;

import java.io.Serializable;

import be.huygebaert.gestionfallout.AccessDB.DAOAbility;
import be.huygebaert.gestionfallout.AccessDB.DAONote;
import be.huygebaert.gestionfallout.Tools.Fallout;

public class Note implements Serializable {
    private int id;
    private String title;
    private String description;
    private Player player;
    private static DAONote daoNote = new DAONote(Fallout.getAppContext());

    public Note(){}
    public Note(int id,String title,String description,Player player){
        this.id = id;
        this.title = title;
        this.description = description;
        this.player = player;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean create(){
        return daoNote.create(this);
    }
    public boolean delete(){
        return daoNote.delete(this);
    }
    public boolean update(){
        return daoNote.update(this);
    }
    public Note find(){
        return daoNote.find(this.getId());
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}

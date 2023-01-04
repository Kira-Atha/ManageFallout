package be.huygebaert.gestionfallout.AccessDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> extends SQLiteOpenHelper {

    public DAO(@Nullable Context context) {
        super(context, "FalloutDatabase", null, 1);
    }

    //sqlitedatabase contient méthodes pour exécuter méthodes SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Créer les tables. Possible d'utiliser TEXT, INTEGER, BLOB, REAL


        db.execSQL("CREATE TABLE Player (\n" +
                "    IdPlayer           INTEGER   PRIMARY KEY,\n" +
                "    PseudoPlayer       TEXT (50),\n" +
                "    RacePlayer         TEXT (50),\n" +
                "    LevelPlayer        INTEGER,\n" +
                "    StrongPlayer       INTEGER,\n" +
                "    PerceptionPlayer   INTEGER,\n" +
                "    EndurancePlayer    INTEGER,\n" +
                "    CharismaPlayer     INTEGER,\n" +
                "    IntelligencePlayer INTEGER,\n" +
                "    AgilityPlayer      INTEGER,\n" +
                "    LuckPlayer         INTEGER,\n" +
                "    ExpCurrPlayer      INTEGER,\n" +
                "    HpCurrPlayer       INTEGER,\n" +
                "    PaCurrPlayer       INTEGER\n" +
                ");"
        );

        db.execSQL("CREATE TABLE Skill (\n" +
                "    IdSkill   INTEGER PRIMARY KEY,\n" +
                "    NameSkill TEXT\n" +
                ");"
        );

        db.execSQL("CREATE TABLE Player_Skill (\n" +
                "    IdSkill            INTEGER     REFERENCES Skill (IdSkill) ON DELETE CASCADE\n" +
                "                                                              ON UPDATE CASCADE,\n" +
                "    IdPlayer           INTEGER     REFERENCES Player (IdPlayer) ON DELETE CASCADE\n" +
                "                                                                ON UPDATE CASCADE,\n" +
                "    LevelSkill         INTEGER,\n" +
                "    PersonalAssetSkill INTEGER (1),\n" +
                "    PRIMARY KEY (\n" +
                "        IdSkill,\n" +
                "        IdPlayer\n" +
                "    )\n" +
                ");"
        );
        //Ajouter tous les skills disponibles !

        ContentValues values = new ContentValues();
        String[] skills_name = {"Armes à énergie", "Armes de corps à corps", "Armes légères", "Armes lourdes", "Athlétisme", "Crochetage", "Discours", "Discrétion", "Explosifs", "Mains nues", "Médecine", "Pilotage", "Projectiles", "Réparation", "Sciences", "Survie", "Troc"};

        for (String name : skills_name) {
            values.put("NameSkill", name);
            db.insert("Skill", null, values);
        }
        db.execSQL("CREATE TABLE Ability (\n" +
                "    IdAbility          NUMERIC PRIMARY KEY,\n" +
                "    NameAbility        TEXT,\n" +
                "    LevelMaxAbility    NUMERIC,\n" +
                "    LevelUpAbility     NUMERIC,\n" +
                "    LevelMinPlayer     NUMERIC,\n" +
                "    DescriptionAbility TEXT,\n" +
                "    BonusFOR           INTEGER,\n" +
                "    BonusPER           NUMERIC,\n" +
                "    BonusEND           NUMERIC,\n" +
                "    BonusCHR           NUMERIC,\n" +
                "    BonusINT           NUMERIC,\n" +
                "    BonusAGI           NUMERIC,\n" +
                "    BonusCHA           NUMERIC,\n" +
                "    BonusPersonalAsset NUMERIC\n" +
                ");\n");

        db.execSQL("CREATE TABLE Player_Ability (\n" +
                "    IdAbility        NUMERIC REFERENCES Ability (IdAbility) ON DELETE CASCADE\n" +
                "                                                            ON UPDATE CASCADE,\n" +
                "    IdPlayer         NUMERIC REFERENCES Player (IdPlayer) ON DELETE CASCADE\n" +
                "                                                          ON UPDATE CASCADE,\n" +
                "    CurrLevelAbility NUMERIC,\n" +
                "    PRIMARY KEY (\n" +
                "        IdAbility,\n" +
                "        IdPlayer\n" +
                "    )\n" +
                ");\n");

    }

    //Exécuter requête qui modifie la structure de la db dans le téléphone utilisateur
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Player");
        db.execSQL("DROP TABLE IF EXISTS Skill");
        db.execSQL("DROP TABLE IF EXISTS Player_Skill");
        onCreate(db);
    }

    public abstract boolean create(T obj);

    public abstract boolean delete(T obj);

    public abstract boolean update(T obj);

    public abstract T find(int id);

    public abstract ArrayList<T> findAll();
}
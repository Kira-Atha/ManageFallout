package be.huygebaert.gestionfallout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.huygebaert.gestionfallout.Models.Player;
import be.huygebaert.gestionfallout.Models.Skill;
import be.huygebaert.gestionfallout.Tools.AdapterSkill;

public class Form extends AppCompatActivity {
    private Intent intent;
    private Player player;
    private EditText totalDice, et_pseudo;
    private TextView tv_def_curr,tv_pa_curr,tv_exp_curr, tv_level_curr, tv_race_choosen, tv_def_ra_curr, tv_strong, tv_perception, tv_endurance, tv_charisma, tv_intelligence, tv_agility, tv_luck,tv_hp_curr;
    private AlertDialog.Builder alert;
    private final int maxPa = 6,defaultPa =2;

    //anonymous
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_abilities:
                    intent = new Intent(Form.this, Abilities.class);
                    startActivity(intent);
                    break;
                case R.id.button_bag:
                    intent = new Intent(Form.this, Bag.class);
                    startActivity(intent);
                    break;
                case R.id.button_notes:
                    intent = new Intent(Form.this, Notes.class);
                    startActivity(intent);
                    break;
                case R.id.tv_race_choosen:
                    String[] races = {"Confrérie de l'Acier", "Goule", "Super mutant", "Mister Handy", "Survivant", "Habitant de l'abri"};
                    alert = new AlertDialog.Builder(Form.this);
                    alert.setTitle("Race du personnage");
                    alert.setItems(races, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String race = races[i];
                            if (races[i].length() > 4) {
                                race = races[i].substring(0, 3);
                            }
                            tv_race_choosen.setText(race);
                            player.setRace(race);
                            if (race.equals("Super mutant".substring(0, 3)) || race.equals("Goule".substring(0, 3)) || race.equals("Mister Handy".substring(0, 3))) {
                                tv_def_ra_curr.setText("IMMU");
                            } else {
                                tv_def_ra_curr.setText(String.valueOf(player.getDef_ra()));
                            }
                        }
                    });
                    alert.show();
                    //send to db
                    break;
                case R.id.button_exp_add:
                    EditText add_exp = new EditText(Form.this);
                    add_exp.setText("0");
                    add_exp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert = new AlertDialog.Builder(Form.this);
                    alert = new AlertDialog.Builder(Form.this);
                    alert.setMessage("Entrer l'expérience à ajouter");
                    alert.setTitle("Gain d'expérience");
                    alert.setView(add_exp);
                    alert.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String exp_value = add_exp.getText().toString();
                            player.earnExp(Integer.parseInt(exp_value));
                            reloadFormActivity();
                            /*
                            tv_exp_curr.setText(new StringBuilder().append(player.getExp()).append("/").append(player.getMaxExp()).toString());
                            tv_level_curr.setText(String.valueOf(player.getLevel()));
                            tv_hp_curr.setText(new StringBuilder().append(player.getHpCurr()).append("/").append(player.getHpMax()).toString());

                             */
                        }
                    });
                    alert.show();
                    //send to db XP/lvl
                    break;
                case R.id.button_hp_minus:
                    EditText minus_hp = new EditText(Form.this);
                    minus_hp.setText("1");
                    minus_hp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert = new AlertDialog.Builder(Form.this);
                    alert.setMessage("Insérer dégât(s) subi(s)");
                    alert.setTitle("Subir");
                    alert.setView(minus_hp);
                    alert.setPositiveButton("Subir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String hp_value = minus_hp.getText().toString();
                            player.takingDamage(Integer.parseInt(hp_value));
                            tv_hp_curr.setText(new StringBuilder().append(player.getHpCurr()).append("/").append(player.getHpMax()).toString());
                        }
                    });
                    alert.show();
                    break;
                case R.id.button_hp_add:
                    EditText add_hp = new EditText(Form.this);
                    add_hp.setText("1");
                    add_hp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert = new AlertDialog.Builder(Form.this);
                    alert.setMessage("Insérer soin reçu");
                    alert.setTitle("Guérir");
                    alert.setView(add_hp);
                    alert.setPositiveButton("Soigner", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String hp_value = add_hp.getText().toString();
                            player.getHealed(Integer.parseInt(hp_value));
                            tv_hp_curr.setText(new StringBuilder().append(player.getHpCurr()).append("/").append(player.getHpMax()).toString());
                        }
                    });
                    alert.show();
                    break;
                case R.id.button_pa_minus:
                    EditText minus_pa = new EditText(Form.this);
                    minus_pa.setText("1");
                    minus_pa.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert = new AlertDialog.Builder(Form.this);
                    alert.setMessage("Insérer le nombre de PA utilisé(s)");
                    alert.setTitle("Utiliser PA");
                    alert.setView(minus_pa);
                    alert.setPositiveButton("Utiliser", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String pa_value = minus_pa.getText().toString();
                            if(!player.usePA(Integer.parseInt(pa_value))){
                                Toast.makeText(getApplication().getBaseContext(),R.string.notEnoughPA,Toast.LENGTH_LONG).show();
                            }
                            tv_pa_curr.setText(new StringBuilder().append(player.getPaCurr()).append("/").append(maxPa).toString());
                        }
                    });
                    alert.show();
                    break;
                case R.id.button_pa_add:
                    EditText add_pa = new EditText(Form.this);
                    add_pa.setText("1");
                    add_pa.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert = new AlertDialog.Builder(Form.this);
                    alert.setMessage("Insérer le nombre de PA gagné(s)");
                    alert.setTitle("Ajouter PA");
                    alert.setView(add_pa);
                    alert.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String pa_value = add_pa.getText().toString();
                            player.receivePA(Integer.parseInt(pa_value));
                            tv_pa_curr.setText(new StringBuilder().append(player.getPaCurr()).append("/").append(maxPa).toString());
                        }
                    });
                    alert.show();
                    break;
                case R.id.button_pa_reset:
                    tv_pa_curr.setText(new StringBuilder().append(defaultPa).append("/").append(maxPa).toString());
                    player.setPa(defaultPa);
                    break;
                case R.id.button_dice_6:
                    totalDice = new EditText(Form.this);
                    totalDice.setText("1");
                    totalDice.setInputType(InputType.TYPE_CLASS_NUMBER);

                    alert = new AlertDialog.Builder(Form.this);
                    alert.setMessage("Choisir le nombre de dès 6 à lancer");
                    alert.setTitle("Lancer dé");
                    alert.setView(totalDice);
                    alert.setPositiveButton("Lancer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int numberOfDice = Integer.parseInt(totalDice.getText().toString());
                            int[] launchesDice = Player.launchDice(numberOfDice,1,7);
                            int totalDamage=0;
                            int totalEffect=0;
                            String effect = "";
                            for(int j = 0; j<launchesDice.length; j++){
                                System.out.println(launchesDice[j]);
                                switch(launchesDice[j]){
                                    case 1:
                                        totalDamage+=1;
                                        break;
                                    case 2:
                                        totalDamage+=2;
                                        break;
                                    case 5:
                                        case 6: {
                                            totalEffect+=1;
                                            if(totalEffect>1){
                                                effect = totalEffect+" effets d'arme peuvent être ajoutés !\n";
                                            }else{
                                                effect = "1 effet d'arme peut être ajouté ! \n";
                                            }
                                            totalDamage += 1;
                                            break;
                                        }
                                }
                            }
                            alert = new AlertDialog.Builder(Form.this);
                            String message="";
                            if(totalDamage>1){
                                message = totalDamage+" dégâts totaux à infliger !\n";
                            }else{
                                message = totalDamage+" dégât total à infliger !\n";
                            }
                            if(!effect.equals("")){
                                message+=effect;
                            }
                            alert.setMessage(message);
                            alert.setTitle("Résultat lancer");
                            alert.show();
                        }
                    });
                    alert.show();
                    break;
                case R.id.button_dice_20:
                    intent = new Intent(Form.this, Dice20.class);
                    intent.putExtra("player",player);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        if((Player)this.getIntent().getSerializableExtra("player") == null){
            player = new Player();
        }else{
            player = (Player)this.getIntent().getSerializableExtra("player");
        }

        //buttons navigables
        Button button_abilities = findViewById(R.id.button_abilities);
        button_abilities.setOnClickListener(onClickListener);
        Button button_bag = findViewById(R.id.button_bag);
        button_bag.setOnClickListener(onClickListener);
        Button button_notes = findViewById(R.id.button_notes);
        button_notes.setOnClickListener(onClickListener);

        //pseudo
        et_pseudo = findViewById(R.id.et_pseudo);
        et_pseudo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(et_pseudo.getText().length()>10){
                    et_pseudo.setText(et_pseudo.getText().toString().substring(0,9));
                    Toast.makeText(getApplication().getBaseContext(),R.string.pseudoTooLong,Toast.LENGTH_LONG).show();
                }
                player.setPseudo(et_pseudo.getText().toString());
                player.create();

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_pseudo.setText(player.getPseudo());

        //race
        tv_race_choosen = findViewById(R.id.tv_race_choosen);
        tv_race_choosen.setOnClickListener(onClickListener);
        tv_race_choosen.setText(player.getRace());

        //level-xp
        tv_level_curr = findViewById(R.id.tv_level_curr);
        tv_level_curr.setText(String.valueOf(player.getLevel()));
        tv_exp_curr = findViewById(R.id.tv_exp_curr);

        Button button_exp_add = findViewById(R.id.button_exp_add);
        button_exp_add.setOnClickListener(onClickListener);
        tv_exp_curr.setText(new StringBuilder().append(player.getExp()).append("/").append(player.getMaxExp()).toString());

        //hp
        tv_hp_curr = findViewById(R.id.tv_hp_curr);
        tv_hp_curr.setText(new StringBuilder().append(player.getHpCurr()).append("/").append(player.getHpMax()).toString());

        Button button_hp_add = findViewById(R.id.button_hp_add);
        button_hp_add.setOnClickListener(onClickListener);
        Button button_hp_minus = findViewById(R.id.button_hp_minus);
        button_hp_minus.setOnClickListener(onClickListener);

        //ini
        TextView tv_ini_curr = findViewById(R.id.tv_ini_curr);
        tv_ini_curr.setText(String.valueOf(player.getInitiative()));

        //pa
        tv_pa_curr = findViewById(R.id.tv_pa_curr);
        tv_pa_curr.setText(new StringBuilder().append(player.getPaCurr()).append("/").append(maxPa).toString());

        Button button_pa_add = findViewById(R.id.button_pa_add);
        button_pa_add.setOnClickListener(onClickListener);
        Button button_pa_minus = findViewById(R.id.button_pa_minus);
        button_pa_minus.setOnClickListener(onClickListener);
        Button button_pa_reset = findViewById(R.id.button_pa_reset);
        button_pa_reset.setOnClickListener(onClickListener);

        //def
        tv_def_ra_curr = findViewById(R.id.tv_def_ra_curr);
        if( (Player)this.getIntent().getSerializableExtra("player") != null){
            if (player.getRace().equals("Super mutant".substring(0, 3)) || player.getRace().equals("Goule".substring(0, 3)) || player.getRace().equals("Mister Handy".substring(0, 3))) {
                tv_def_ra_curr.setText("IMMU");
            } else {
                tv_def_ra_curr.setText(String.valueOf(player.getDef_ra()));
            }
        }else{
            tv_def_ra_curr.setText(String.valueOf(player.getDef_ra()));
        }

        tv_def_curr = findViewById(R.id.tv_def_curr);
        tv_def_curr.setText(String.valueOf(player.getDef()));
        //SPECIAL
        tv_strong = findViewById(R.id.tv_strong);
        tv_strong.setText(new StringBuilder().append(tv_strong.getText()).append(" ").append(String.valueOf(player.getStrong())));

        tv_perception = findViewById(R.id.tv_perception);
        tv_perception.setText(new StringBuilder().append(tv_perception.getText()).append(" ").append(String.valueOf(player.getPerception())).toString());

        tv_endurance = findViewById(R.id.tv_endurance);
        tv_endurance.setText(new StringBuilder().append(tv_endurance.getText()).append(" ").append(String.valueOf(player.getEndurance())).toString());

        tv_charisma = findViewById(R.id.tv_charisma);
        tv_charisma.setText(new StringBuilder().append(tv_charisma.getText()).append(" ").append(String.valueOf(player.getCharisma())).toString());

        tv_intelligence = findViewById(R.id.tv_intelligence);
        tv_intelligence.setText(new StringBuilder().append(tv_intelligence.getText()).append(" ").append(String.valueOf(player.getIntelligence())).toString());

        tv_agility = findViewById(R.id.tv_agility);
        tv_agility.setText(new StringBuilder().append(tv_agility.getText()).append(" ").append(String.valueOf(player.getAgility())).toString());

        tv_luck = findViewById(R.id.tv_luck);
        tv_luck.setText(new StringBuilder().append(tv_luck.getText()).append(" ").append(String.valueOf(player.getLuck())).toString());

        //grid view
        //GridView gridSPECIAL = findViewById(R.id.gridSPECIAL);
        //String[] SPECIAL_tv = { tv_strong.getText().toString(),tv_perception.getText().toString(),tv_endurance.getText().toString(),tv_charisma.getText().toString(),tv_intelligence.getText().toString(),tv_agility.getText().toString(),tv_luck.getText().toString()};
        //gridSPECIAL.setAdapter(new TextViewAdapter(this,SPECIAL_tv));

//SKILLS
        ListView list_skills = findViewById(R.id.list_skills);
        AdapterSkill adapter = new AdapterSkill(Form.this,0,player);

        list_skills.setAdapter(adapter);
        //dices
        Button button_dice_6 = findViewById(R.id.button_dice_6);
        button_dice_6.setOnClickListener(onClickListener);
        Button button_dice_20 = findViewById(R.id.button_dice_20);
        button_dice_20.setOnClickListener(onClickListener);
    }

    public void reloadFormActivity(){
        intent = new Intent(Form.this,Form.class);
        intent.putExtra("player",player);
        startActivity(intent);
    }
}
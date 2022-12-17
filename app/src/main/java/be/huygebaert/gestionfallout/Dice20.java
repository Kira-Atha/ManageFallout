package be.huygebaert.gestionfallout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import be.huygebaert.gestionfallout.Models.Player;
import be.huygebaert.gestionfallout.Models.Skill;

public class Dice20 extends AppCompatActivity {
    private Intent intent;
    private EditText totalDice;
    private String skill_name,SPECIAL_name;
    private int num_skill, num_SPECIAL, num_complication;
    private Spinner sp_SPECIALChoice, sp_skillChoice,sp_complication;
    private Player player;
    private AlertDialog.Builder alert;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int numberOfDice = Integer.parseInt(totalDice.getText().toString());
            int[] launchesDice = Player.launchDice(numberOfDice, 1, 21);
       //name
            skill_name = sp_skillChoice.getSelectedItem().toString();
            SPECIAL_name = sp_SPECIALChoice.getSelectedItem().toString();

        //position
            num_skill = sp_skillChoice.getSelectedItemPosition();
            num_SPECIAL = sp_SPECIALChoice.getSelectedItemPosition();
            num_complication = sp_complication.getSelectedItemPosition();

            System.out.println(num_skill+" "+skill_name);
            System.out.println(num_SPECIAL+ " " +SPECIAL_name);
            System.out.println("Complication level :" + num_complication);

            for(int i = 0; i< launchesDice.length; i++){
                System.out.println(i+ " " + launchesDice[i]);
            }
            int results[]=player.getDice20Results(launchesDice,num_skill,num_SPECIAL,num_complication);

            alert = new AlertDialog.Builder(Dice20.this);
            String message="";

            Skill skillChosen = player.getPlayerSkills().get(num_skill);

            message+="Vous avez lancé "+numberOfDice+" dé(s) \n";
            message+="Compétence: "+skill_name+"\n";
            message+="Statistique : "+SPECIAL_name+"\n";
            message+="("+skillChosen.getLevel()+"+"+player.getSPECIALTab()[num_SPECIAL]+")\n";
            message+="Niveau de complication : "+String.valueOf(num_complication+1)+"\n";

            if(skillChosen.isPersonalAsset()){
                message+="C'était votre atout !\n";
                message+="Vous recevez "+ results[0]*2 + " PA supplémentaire(s)\n";
            }else{
                message+="Ce n'était pas votre atout !\n";
                message+="Vous recevez "+ results[0] + " PA supplémentaire(s)\n";
            }

            if(results[0] > 1){
                message +=results[0]+" coups critiques !\n";
            }else{
                message +=results[0]+" coup critique !\n";
            }
            if(results[1] > 1){
                message +=results[1]+" réussites !\n";
            }else{
                message +=results[1]+" réussite !\n";
            }
            if(results[2] > 1){
                message +=results[2]+ " échecs critiques !\n";
            }else{
                message +=results[2]+ " échec critique !\n";
            }

            alert.setMessage(message);
            alert.setTitle("Résultats du lancer");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent = new Intent(Dice20.this,Form.class);
                    intent.putExtra("player",player);
                    startActivity(intent);
                }
            });
            alert.show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice20);
        player = (Player)this.getIntent().getSerializableExtra("player");

        totalDice = new EditText(Dice20.this);
        totalDice.setText("1");
        totalDice.setInputType(InputType.TYPE_CLASS_NUMBER);

        String[] specials = {"Force","Perception","Endurance","Charisme","Intelligence","Agilité","Chance"};
        sp_SPECIALChoice = new Spinner(Dice20.this);
        final ArrayAdapter<String> adapterSpecials = new ArrayAdapter<String>(Dice20.this, android.R.layout.simple_spinner_item, specials);
        sp_SPECIALChoice.setAdapter(adapterSpecials);

        //TODO get skills from DB
        List<Skill> skills = Skill.findAll();
        String[] skills_name = new String[skills.size()];
        for (int i=0; i<skills.size();i++) {
            skills_name[i] = skills.get(i).getName();
        }

        sp_skillChoice = new Spinner(Dice20.this);
        final ArrayAdapter<String> adapterSkills = new ArrayAdapter<String>(Dice20.this, android.R.layout.simple_spinner_item, skills_name);
        sp_skillChoice.setAdapter(adapterSkills);

        sp_complication = new Spinner(Dice20.this);
        String[] complicationsLevel={"1","2","3","4","5"};
        final ArrayAdapter<String> adapterComplications = new ArrayAdapter<String>(Dice20.this, android.R.layout.simple_spinner_item, complicationsLevel);
        sp_complication.setAdapter(adapterComplications);

        LinearLayout manyViewLayout = new LinearLayout(Dice20.this);
        manyViewLayout.setOrientation(LinearLayout.VERTICAL);

        TextView tv_dice = new TextView(Dice20.this,null);
        tv_dice.setText(R.string.choice_dice);
        TextView choice_skill = new TextView(Dice20.this,null);
        choice_skill.setText(R.string.choice_skill);
        TextView choice_SPECIAL = new TextView(Dice20.this,null);
        choice_SPECIAL.setText(R.string.choice_SPECIAL);
        TextView choice_complication = new TextView(Dice20.this);
        choice_complication.setText(R.string.choice_complication);
        Button button_launch_dice_20 = new Button(Dice20.this,null);
        button_launch_dice_20.setText(R.string.launch);
        button_launch_dice_20.setOnClickListener(onClickListener);
        manyViewLayout.addView(tv_dice);
        manyViewLayout.addView(totalDice);
        manyViewLayout.addView(choice_skill);
        manyViewLayout.addView(sp_skillChoice);
        manyViewLayout.addView(choice_SPECIAL);
        manyViewLayout.addView(sp_SPECIALChoice);
        manyViewLayout.addView(choice_complication);
        manyViewLayout.addView(sp_complication);
        manyViewLayout.addView(button_launch_dice_20);
        setContentView(manyViewLayout);
    }
}
package be.huygebaert.gestionfallout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
    private int num_skill=-1, num_SPECIAL, num_complication;
    private Spinner sp_SPECIALChoice, sp_skillChoice,sp_complication;
    private Player player;
    private AlertDialog.Builder alert;
    private List<Skill> skills;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View view) {
            int numberOfDice = Integer.parseInt(totalDice.getText().toString());
            int[] launchesDice = Player.launchDice(numberOfDice, 1, 21);
       //name
            if(num_skill==-1){
                skill_name = sp_skillChoice.getSelectedItem().toString();
                SPECIAL_name = sp_SPECIALChoice.getSelectedItem().toString();

                //position
                num_skill = sp_skillChoice.getSelectedItemPosition();
                num_SPECIAL = sp_SPECIALChoice.getSelectedItemPosition();
            }else{
                skill_name = Skill.find(num_skill).getName();

                switch(num_skill) {
                    case 2:
                    case 5:
                    case 10:
                        SPECIAL_name = "Force";
                        num_SPECIAL=1;
                        break;
                    case 1:
                    case 6:
                    case 9:
                    case 12:
                        SPECIAL_name = "Perception";
                        num_SPECIAL=2;
                        break;
                    case 4:
                    case 16:
                        SPECIAL_name = "Endurance";
                        num_SPECIAL=3;
                        break;
                    case 7 :
                    case 17:
                        SPECIAL_name = "Charisme";
                        num_SPECIAL=4;
                        break;
                    case 11:
                    case 14:
                    case 15:
                        SPECIAL_name = "Intelligence";
                        num_SPECIAL=5;
                        break;
                    case 3:
                    case 8:
                    case 13:
                        SPECIAL_name = "Agilit??";
                        num_SPECIAL=6;
                        break;
                }
                // Search on list 0->16
                num_SPECIAL-=1;
                num_skill-=1;
            }
            num_complication = sp_complication.getSelectedItemPosition();

            /*
            System.out.println(num_skill+" "+skill_name);
            System.out.println(num_SPECIAL+ " " +SPECIAL_name);
            System.out.println("Complication level :" + num_complication);

            for(int i = 0; i< launchesDice.length; i++){
                System.out.println(i+ " " + launchesDice[i]);
            }

             */
            int results[]=player.getDice20Results(launchesDice,num_skill,num_SPECIAL,num_complication);

            alert = new AlertDialog.Builder(Dice20.this);
            String message="";

            Skill skillChosen = new Skill();

            for(int i = 0;i<player.getPlayerSkills().size();i++){
                if(player.getPlayerSkills().get(i).equals(skills.get(num_skill))){
                    skillChosen = player.getPlayerSkills().get(i);
                    //System.out.println("PAREIL");
                }
            }

            message+="Vous avez lanc?? "+numberOfDice+" d??(s) \n";
            message+="Comp??tence: "+skill_name+"\n";
            message+="Statistique : "+SPECIAL_name+"\n";
            message+="("+skillChosen.getLevel()+"+"+player.getSPECIALTab()[num_SPECIAL]+")\n";
            message+="Niveau de complication : "+String.valueOf(num_complication+1)+"\n";

            if(skillChosen.isPersonalAsset()){
                message+="C'??tait votre atout !\n";
                message+="Vous recevez "+ results[0]*2 + " PA suppl??mentaire(s)\n";
            }else{
                message+="Ce n'??tait pas votre atout !\n";
                message+="Vous recevez "+ results[0] + " PA suppl??mentaire(s)\n";
            }

            if(results[0] > 1){
                message +=results[0]+" coups critiques !\n";
            }else{
                message +=results[0]+" coup critique !\n";
            }
            if(results[1] > 1){
                message +=results[1]+" r??ussites !\n";
            }else{
                message +=results[1]+" r??ussite !\n";
            }
            if(results[2] > 1){
                message +=results[2]+ " ??checs critiques !\n";
            }else{
                message +=results[2]+ " ??chec critique !\n";
            }

            alert.setMessage(message);
            alert.setTitle("R??sultats du lancer");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent = new Intent(Dice20.this,Sheet.class);
                    intent.putExtra("player",player);
                    startActivity(intent);
                    Dice20.this.finish();
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
        LinearLayout manyViewLayout = new LinearLayout(Dice20.this);
        manyViewLayout.setOrientation(LinearLayout.VERTICAL);

        skills = Skill.findAll();

        if(this.getIntent().getSerializableExtra("num_skill")==null){
            String[] specials = {"Force","Perception","Endurance","Charisme","Intelligence","Agilit??","Chance"};
            sp_SPECIALChoice = new Spinner(Dice20.this);
            final ArrayAdapter<String> adapterSpecials = new ArrayAdapter<String>(Dice20.this, android.R.layout.simple_spinner_item, specials);
            sp_SPECIALChoice.setAdapter(adapterSpecials);

            String[] skills_name = new String[skills.size()];
            for (int i=0; i<skills.size();i++) {
                skills_name[i] = skills.get(i).getName();
            }
            sp_skillChoice = new Spinner(Dice20.this);
            final ArrayAdapter<String> adapterSkills = new ArrayAdapter<String>(Dice20.this, android.R.layout.simple_spinner_item, skills_name);
            sp_skillChoice.setAdapter(adapterSkills);
        }else{
            num_skill = (int) this.getIntent().getSerializableExtra("num_skill");
            //System.out.println("DICE 20 CONTEXT => "+num_skill);
        }

        sp_complication = new Spinner(Dice20.this);
        String[] complicationsLevel={"1","2","3","4","5"};
        final ArrayAdapter<String> adapterComplications = new ArrayAdapter<String>(Dice20.this, android.R.layout.simple_spinner_item, complicationsLevel);
        sp_complication.setAdapter(adapterComplications);

        TextView tv_dice = new TextView(Dice20.this,null);
        tv_dice.setText(R.string.choice_dice);

        TextView choice_skill = null;
        TextView choice_complication = null;
        TextView choice_SPECIAL = null;

        if(this.getIntent().getSerializableExtra("num_skill")==null) {
            choice_skill = new TextView(Dice20.this,null);
            choice_skill.setText(R.string.choice_skill);
            choice_SPECIAL = new TextView(Dice20.this,null);
            choice_SPECIAL.setText(R.string.choice_SPECIAL);
            manyViewLayout.addView(choice_skill);
            manyViewLayout.addView(sp_skillChoice);
            manyViewLayout.addView(choice_SPECIAL);
            manyViewLayout.addView(sp_SPECIALChoice);
        }
        choice_complication = new TextView(Dice20.this);
        choice_complication.setText(R.string.choice_complication);
        Button button_launch_dice_20 = new Button(Dice20.this,null);
        button_launch_dice_20.setText(R.string.launch);
        button_launch_dice_20.setOnClickListener(onClickListener);
        manyViewLayout.addView(tv_dice);
        manyViewLayout.addView(totalDice);
        manyViewLayout.addView(choice_complication);
        manyViewLayout.addView(sp_complication);
        manyViewLayout.addView(button_launch_dice_20);
        setContentView(manyViewLayout);
    }
}
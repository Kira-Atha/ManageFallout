package be.huygebaert.gestionfallout.Tools;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import be.huygebaert.gestionfallout.Models.Player;
import be.huygebaert.gestionfallout.Models.Skill;
import be.huygebaert.gestionfallout.R;

public class AdapterSkill extends ArrayAdapter<Skill> {
    private Player player;
    private Activity activity;
    private List<Skill> allSkills;
    private static LayoutInflater inflater = null;
    private ViewHolder holder;
    private View vi;

    public AdapterSkill (Activity activity, int textViewResourceId,Player player) {
        super(activity, textViewResourceId, player.getPlayerSkills());
        try {
            this.activity = activity;
            this.allSkills = player.getPlayerSkills();

            Collections.sort(allSkills,new Comparator<Skill>(){
                public int compare(Skill s1, Skill s2){
                    Integer n1 = Integer.valueOf(s1.getLevel());
                    Integer n2  = Integer.valueOf(s2.getLevel());
                    return n2.compareTo(n1);
                }
            });

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.player = player;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public Skill getItem(Skill position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView tv_skill_name;
        public TextView tv_skill_level;
        public CheckBox box_personalAsset;
        public TextView add_skill;
    }
//TODO : Régler problème de check box...
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getView(final int position, View convertView, ViewGroup parent) {
        vi = convertView;

        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.item_skill, null);
                holder = new ViewHolder();

                holder.tv_skill_name = (TextView) vi.findViewById(R.id.tv_skill_name);
                holder.tv_skill_level = (TextView) vi.findViewById(R.id.tv_skill_level);
                holder.box_personalAsset = (CheckBox) vi.findViewById(R.id.box_personalAsset);
                if(allSkills.get(position).isPersonalAsset()){
                    holder.box_personalAsset.setChecked(true);
                }
                holder.add_skill = (TextView) vi.findViewById(R.id.add_skill);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            Skill skillChosen = allSkills.get(position);


            holder.tv_skill_name.setText(allSkills.get(position).getName());
            holder.tv_skill_level.setText(String.valueOf(allSkills.get(position).getLevel()));
            holder.box_personalAsset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    if(isChecked){
                        if(player.choosePersonalAsset(skillChosen)){
                            notifyDataSetChanged();
                            show_checkbox();
                        }else{
                            holder.box_personalAsset.setChecked(false);
                            Toast.makeText(Fallout.getAppContext(),R.string.problemPersonalAsset,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        player.unchoosePersonalAsset(skillChosen);
                        notifyDataSetChanged();
                        show_checkbox();
                    }
                }
            });
            show_checkbox();

            holder.add_skill.setText("+");
            holder.add_skill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //System.out.println("HEY");
                    player.useStock_skill_points(skillChosen);
                    notifyDataSetChanged();
                    show_button_plus();
                }
            });
            show_button_plus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return vi;
    }
    public void show_button_plus(){
        if(player.getUsed_stock_skill_points()==player.getStock_skill_points()){
            holder.add_skill.setVisibility(View.INVISIBLE);
        }else{
            holder.add_skill.setVisibility(View.VISIBLE);
        }
    }
    public void show_checkbox(){
        if(player.getCurrentPersonalAsset()==player.getMaxAsset()){
            //System.out.println("Check box invisible");
            holder.box_personalAsset.setEnabled(false);
        }
    }
}
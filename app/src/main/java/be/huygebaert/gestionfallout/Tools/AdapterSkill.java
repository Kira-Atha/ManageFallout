package be.huygebaert.gestionfallout.Tools;

import android.app.Activity;
import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.huygebaert.gestionfallout.Models.Player;
import be.huygebaert.gestionfallout.Models.Skill;
import be.huygebaert.gestionfallout.R;

public class AdapterSkill extends ArrayAdapter<Skill> {
    private Player player;
    private Activity activity;
    private List<Skill> allSkills;
    private static LayoutInflater inflater = null;
    private ViewHolder holder;

    public AdapterSkill (Activity activity, int textViewResourceId,Player player) {
        super(activity, textViewResourceId, player.getPlayerSkills());
        try {
            this.activity = activity;
            this.allSkills = player.getPlayerSkills();
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.item_skill, null);
                holder = new ViewHolder();

                holder.tv_skill_name = (TextView) vi.findViewById(R.id.tv_skill_name);
                holder.tv_skill_level = (TextView) vi.findViewById(R.id.tv_skill_level);
                holder.box_personalAsset = (CheckBox) vi.findViewById(R.id.box_personalAsset);
                holder.add_skill = (TextView) vi.findViewById(R.id.add_skill);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            Skill skillChosen = allSkills.get(position);

            holder.tv_skill_name.setText(allSkills.get(position).getName());
            holder.tv_skill_level.setText(String.valueOf(allSkills.get(position).getLevel()));
            if(allSkills.get(position).isPersonalAsset()){
                holder.box_personalAsset.isChecked();
            }
            holder.box_personalAsset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(player.choosePersonalAsset(skillChosen)){
                        holder.box_personalAsset.isChecked();
                    }
                }
            });
            holder.add_skill.setText("+");
            holder.add_skill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("HEY");
                    player.useStock_skill_points(skillChosen);
                    holder.tv_skill_level.setText(String.valueOf(skillChosen.getLevel()));
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
}
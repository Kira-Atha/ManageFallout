package be.huygebaert.gestionfallout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import be.huygebaert.gestionfallout.Models.Player;

public class ChooseCharacter extends AppCompatActivity {
    private Intent intent;
    private Player playerChoosen;
    private int idPlayer;
    public List<Player> allPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        setChooseCharacterLayout();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadActivity();
    }

    public void setChooseCharacterLayout(){
        allPlayers = Player.findAll();
        Button[] buttonsCharacters = new Button[allPlayers.size()];
        LinearLayout layout_in_scroll = findViewById(R.id.layout_choose_sheet);
        for(int i = 0;i< buttonsCharacters.length;i++){
            String pseudo = allPlayers.get(i).getPseudo();
            buttonsCharacters[i] = new Button(ChooseCharacter.this);
            buttonsCharacters[i].setText(pseudo);
            buttonsCharacters[i].setId(allPlayers.get(i).getId());
            layout_in_scroll.addView(buttonsCharacters[i]);
            buttonsCharacters[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idPlayer = view.getId();
                    intent = new Intent(ChooseCharacter.this, Sheet.class);
                    playerChoosen = Player.find(idPlayer);
                    intent.putExtra("player",playerChoosen);
                    startActivity(intent);
                }
            });
        }
        Button button_new_sheet = findViewById(R.id.button_new_sheet);
        button_new_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ChooseCharacter.this,Sheet.class);
                startActivity(intent);
            }
        });
    }

    public void reloadActivity(){
        intent = new Intent(ChooseCharacter.this,ChooseCharacter.class);
        startActivity(intent);
    }
}
package be.huygebaert.gestionfallout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;

import be.huygebaert.gestionfallout.Models.Player;

public class ChooseCharacter extends AppCompatActivity {
    private Intent intent;
    private Player playerChoosen;
    private int idPlayer;
    private List<Player> allPlayers;

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch(view.getId()){
                case R.id.button_new_sheet:
                    intent = new Intent(ChooseCharacter.this,Form.class);
                    startActivity(intent);
                    break;
                default :
                    intent = new Intent(ChooseCharacter.this, Form.class);
                    playerChoosen = Player.find(idPlayer);
                    intent.putExtra("player",playerChoosen);
                    startActivity(intent);
                    break;
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);

        allPlayers = Player.findAll();
        Button[] buttonsCharacters = new Button[allPlayers.size()];
        LinearLayout layout_in_scroll = findViewById(R.id.layout_choose_sheet);
        for(int i = 0;i< buttonsCharacters.length;i++){
            String pseudo = allPlayers.get(i).getPseudo();
            buttonsCharacters[i] = new Button(ChooseCharacter.this);
            buttonsCharacters[i].setText(pseudo);
            idPlayer = allPlayers.get(i).getId();
            buttonsCharacters[i].setOnClickListener(clickListener);
            layout_in_scroll.addView(buttonsCharacters[i]);
        }
        Button button_new_sheet = findViewById(R.id.button_new_sheet);
        button_new_sheet.setOnClickListener(clickListener);
    }
}
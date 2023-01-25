package be.huygebaert.gestionfallout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.huygebaert.gestionfallout.Models.Ability;
import be.huygebaert.gestionfallout.Models.Player;
import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class ChooseCharacter extends AppCompatActivity {
    private Intent intent;
    private Player playerChoosen;
    private int idPlayer;
    public List<Player> allPlayers;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        setChooseCharacterLayout();
        createExcelAbilities();

        //ESPACE TEST
        for(Ability abi : Ability.findAll()){
            System.out.println(abi.getName());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadActivity();
    }

    public void setChooseCharacterLayout() {
        allPlayers = Player.findAll();
        Button[] buttonsCharacters = new Button[allPlayers.size()];
        LinearLayout layout_in_scroll = findViewById(R.id.layout_choose_sheet);
        for (int i = 0; i < buttonsCharacters.length; i++) {
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
                    intent.putExtra("player", playerChoosen);
                    startActivity(intent);
                }
            });
        }
        Button button_new_sheet = findViewById(R.id.button_new_sheet);
        button_new_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ChooseCharacter.this, Sheet.class);
                startActivity(intent);
            }
        });
    }

    public void reloadActivity() {
        intent = new Intent(ChooseCharacter.this, ChooseCharacter.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createExcelAbilities() {
        ArrayList<String> contents = new ArrayList<String>();
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("Abilities.xls");
            WorkbookSettings settings = new WorkbookSettings();
            settings.setEncoding("Cp1252");
            Workbook workBook = Workbook.getWorkbook(is,settings);

            jxl.Sheet sheet = workBook.getSheet(0);
            int row = sheet.getRows();
            int column = sheet.getColumns();

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    Cell cell = sheet.getCell(j, i);
                    if (cell.getContents().equals("")) {
                        contents.add("0");
                    } else {
                        contents.add(cell.getContents());
                    }
                }
            }
            Ability.createAllAbilitiesOnce(contents);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
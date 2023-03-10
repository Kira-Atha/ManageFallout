package be.huygebaert.gestionfallout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bag extends AppCompatActivity {
    private Intent intent;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_abilities:
                    intent = new Intent(Bag.this, Abilities.class);
                    startActivity(intent);
                    break;
                case R.id.button_notes:
                    intent = new Intent(Bag.this, Notes.class);
                    startActivity(intent);
                    break;
                case R.id.button_form:
                    intent = new Intent(Bag.this, Sheet.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        Button button_abilities = findViewById(R.id.button_abilities);
        button_abilities.setOnClickListener(listener);
        Button button_notes = findViewById(R.id.button_notes);
        button_notes.setOnClickListener(listener);
        Button button_form = findViewById(R.id.button_form);
        button_form.setOnClickListener(listener);
    }
}
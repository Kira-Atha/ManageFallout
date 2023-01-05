package be.huygebaert.gestionfallout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notes extends AppCompatActivity {
    private Intent intent;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_abilities:
                    intent = new Intent(Notes.this, Abilities.class);
                    startActivity(intent);
                    break;
                case R.id.button_bag:
                    intent = new Intent(Notes.this, Bag.class);
                    startActivity(intent);
                    break;
                case R.id.button_form:
                    intent = new Intent(Notes.this, Sheet.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Button button_abilities = findViewById(R.id.button_abilities);
        button_abilities.setOnClickListener(listener);
        Button button_bag = findViewById(R.id.button_bag);
        button_bag.setOnClickListener(listener);
        Button button_form = findViewById(R.id.button_form);
        button_form.setOnClickListener(listener);
    }
}
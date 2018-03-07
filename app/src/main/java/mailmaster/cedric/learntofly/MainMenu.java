package mailmaster.cedric.learntofly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Cedric on 04.03.2018.
 * This is the MainMenu which is launched upon starting the app.
 * All it contains is a background and a Button to go to the shop.
 */

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);


        ImageButton play = findViewById(R.id.btnPlay);

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this,Shop.class);
                startActivity(intent);
            }
        });
    }

}

package mailmaster.cedric.learntofly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by cedric.schoeni on 06.03.2018.
 */

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);


        ImageButton play = (ImageButton) findViewById(R.id.btnPlay);

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

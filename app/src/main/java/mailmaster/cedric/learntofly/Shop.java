package mailmaster.cedric.learntofly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Created by cedric.schoeni on 06.03.2018.
 */

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial

        ImageButton play = (ImageButton) findViewById(R.id.imageButton);

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Shop.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

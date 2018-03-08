package mailmaster.cedric.learntofly;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import mailmaster.cedric.learntofly.view.Renderer;

/**
 * Created by cedric.schoeni on 03.03.2018
 * This is the main Activity
 * It will create a new View and add it to the gridLayout
 * in this view the Renderer will display all information
 */
public class MainActivity extends AppCompatActivity {

    private Context context;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        gridLayout = findViewById(R.id.gridLayout);
        Renderer r = new Renderer(gridLayout.getContext(), this);
        context = gridLayout.getContext();
        gridLayout.addView(r);

    }





}

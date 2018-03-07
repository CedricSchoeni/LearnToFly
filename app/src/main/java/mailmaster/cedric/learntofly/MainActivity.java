package mailmaster.cedric.learntofly;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import mailmaster.cedric.learntofly.view.Renderer;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    static GridLayout gridLayout;

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

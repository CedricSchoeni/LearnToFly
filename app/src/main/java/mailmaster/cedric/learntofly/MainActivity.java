package mailmaster.cedric.learntofly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import mailmaster.cedric.learntofly.View.Renderer;

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

package mailmaster.cedric.learntofly;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import mailmaster.cedric.learntofly.View.Renderer;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        Point p = new Point();

        getWindowManager().getDefaultDisplay().getSize(p);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        Renderer r = new Renderer(gridLayout.getContext(), this);
        //Log.e("Sc.width",sc.getLayoutParams().width+"px");
        //Log.e("Sc.height",sc.getLayoutParams().height+"px");
        gridLayout.addView(r);



        //Renderer r = new Renderer(this);
        //setContentView(r);

    }
}

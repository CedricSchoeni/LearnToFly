package mailmaster.cedric.learntofly;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mailmaster.cedric.learntofly.View.Renderer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        Renderer r = new Renderer(this);
        setContentView(r);
        //Bitmap youmomgay = BitmapFactory.decodeResource(this.getResources(),R.drawable.elkenholz);

    }
}

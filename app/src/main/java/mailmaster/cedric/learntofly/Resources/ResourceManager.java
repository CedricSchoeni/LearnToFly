package mailmaster.cedric.learntofly.Resources;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.R;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public class ResourceManager extends AppCompatActivity {



    public Bitmap drawableToBitmap(Context c, int d){
        return BitmapFactory.decodeResource(c.getResources(),d);
    }

}
package mailmaster.cedric.learntofly.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by adriano.campiotti on 01.03.2018.
 * The ResourceManager is a simple class which manages Resources and contains but one method to do so.
 */

public class ResourceManager extends AppCompatActivity {


    /**
     * Converts a drawable into a Bitmap.
     * @param c required context to be able to convert the drawable.
     * @param d the Resource id of the drawable being converted.
     * @return Bitmap with the content of the drawable provided.
     */
    public static Bitmap drawableToBitmap(Context c, int d){
        return BitmapFactory.decodeResource(c.getResources(),d);
    }

}

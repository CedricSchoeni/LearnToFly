package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public interface RObject {

    /**
     * this drawings doesed
     */
    public void drawObject(Canvas canvas, Context context);
    public void rotateObject(float rotation);

}

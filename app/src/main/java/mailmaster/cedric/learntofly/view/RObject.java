package mailmaster.cedric.learntofly.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import mailmaster.cedric.learntofly.physics.FVector;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public interface RObject {

    void drawObject(Canvas canvas, Context context);
    boolean outOfScreen(int width, int height, int startx, int starty);
    void updatePosition(FVector v);
    void setPosition(FVector v);

    float getRotation();
    void addRotation(float rotation);
    void setRotation(float rotation);

    Bitmap combine(Bitmap bmp2);


}

package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import mailmaster.cedric.learntofly.Physics.FVector;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public interface RObject {

    public void drawObject(Canvas canvas, Context context);
    public boolean outOfScreen(int width, int height, int startx, int starty);
    public void updatePosition(FVector v);
    public void setPosition(FVector v);

    public float getRotation();
    public void addRotation(float rotation);
    public void setRotation(float rotation);

    public Bitmap combine(Bitmap bmp2);


}

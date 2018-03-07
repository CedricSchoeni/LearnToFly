package mailmaster.cedric.learntofly.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import mailmaster.cedric.learntofly.physics.FVector;

/**
 * Created by cedric.schoeni on 01.03.2018.
 */

public interface RObject {

    /**
     * this allows us to have classes with different draw methods
     * i.e drawRectangle and drawCircle are different
     * @param canvas where it will be drawn to
     * @param context context of the view
     */
    void drawObject(Canvas canvas, Context context);

    /**
     * This method checks if the object is in a given area and return true if so
     * @param width screen length
     * @param height screen height
     * @param startx screen start - usually 0
     * @param starty screen start - usually 0
     * @return
     */
    boolean outOfScreen(int width, int height, int startx, int starty);

    /**
     * v will be added to the current position
     * @param v FVector with x and y set
     */
    void updatePosition(FVector v);

    /**
     * v will replace the current location
     * @param v FVector with x and y set
     */
    void setPosition(FVector v);

    /**
     * Gets the image rotation
     * @return float rotation in degrees
     */
    float getRotation();

    /**
     * adds rotation to the current rotation
     * @param rotation float rotation in degrees
     */
    void addRotation(float rotation);

    /**
     * replaces the current rotation with the new one
     * @param rotation float rotation in degrees
     */
    void setRotation(float rotation);

    /**
     * this method takes 2 bitmap with the same size and combines them to one
     * @param bmp2 bitmap it will be combined with
     * @return Bitmap the combined bitmap
     */
    Bitmap combine(Bitmap bmp2);


}

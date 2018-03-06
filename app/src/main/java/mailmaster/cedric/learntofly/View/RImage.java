package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.Physics.PhysicEngine;

/**
 * Created by cedric.schoeni on 01.03.2018.
 */

public class RImage implements RObject {



    private FVector position;

    private int width;
    private int height;

    private float rotation;

    public float getRotation() {
        return rotation;
    }

    private Bitmap image;
    public int imageID;

    private Matrix matrix;


    public RImage(float xPos, float yPos, int width, int height, float rotation, Bitmap image, int id) {

        position = new FVector(xPos, yPos);
        this.imageID = id;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.image  = Bitmap.createScaledBitmap(
                image, width, height, false);
        this.matrix = new Matrix();
        setRotation(rotation);
    }

    public void addRotation(float rotation){
        this.rotation += rotation;
        this.matrix.setRotate(this.rotation,this.image.getWidth()/2,this.image.getHeight()/2);
        matrix.postTranslate(position.x, position.y);
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
        this.matrix.setRotate(rotation,this.image.getWidth()/2,this.image.getHeight()/2);
        matrix.postTranslate(position.x, position.y);
    }

    @Override
    public void drawObject(Canvas canvas, Context context) {

        canvas.drawBitmap(image, matrix, null);
    }

    @Override
    public boolean outOfScreen(int width, int height, int startx, int starty) {
        if (position.x > width + this.width || position.x < startx - this.width  || position.y > height  + this.height || this.height < starty - this.width ){
            return true;
        }
        return false;
    }

    @Override
    public void updatePosition(FVector v){
        position.add(v);
        matrix.postTranslate(v.x, v.y);
    }

    @Override
    public void setPosition(FVector v) {

        matrix.postTranslate(v.x - position.x, v.y - position.y);
        position.set(v.x, v.y);
    }

    /**
     * This method combines two Bitmaps into one and returns that bitmap.
     * @param bmp1 the first bitmap, this is the base layer the 2nd bitmap gets put ontop of
     * @param bmp2 the 2nd bitmap, this one gets put on the x: 0 and y:0 pixels of bmp1
     * @return Bitmap bmp1 with bmp2 on top of the bmp1.
     */
    public static Bitmap combine(Bitmap bmp1, Bitmap bmp2) {
        if(bmp1.getWidth()<bmp2.getWidth() || bmp1.getHeight()<bmp2.getHeight())return bmp1;
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    //Do not use for now.
    public static Bitmap combineTemp(Bitmap bmp1, Bitmap bmp2, int x, int y, boolean left) {
        x = (left) ? x : x+bmp2.getWidth();
       //y = (top) ? y : y+bmp2.getHeight();
        if(bmp1.getWidth()<bmp2.getWidth() || bmp1.getHeight()<bmp2.getHeight())return bmp1;
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, x, y, null);
        return bmOverlay;
    }

}

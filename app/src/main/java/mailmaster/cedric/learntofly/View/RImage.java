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


}

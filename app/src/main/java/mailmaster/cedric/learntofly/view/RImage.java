package mailmaster.cedric.learntofly.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import mailmaster.cedric.learntofly.physics.FVector;

/**
 * Created by cedric.schoeni on 01.03.2018.
 * This is a Renderable Image
 */

public class RImage implements RObject {



    private final FVector position;

    private final int width;
    private final int height;

    private float rotation;

    public float getRotation() {
        return rotation;
    }

    private Bitmap image;
    private final int imageID;

    private final Matrix matrix;


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

    /**
     * This rotates the image using a matrix
     * The rotation goes from the center which means that the position wont change
     * @param rotation float rotation in degrees
     */
    public void addRotation(float rotation){
        this.rotation += rotation;
        this.matrix.setRotate(this.rotation,this.image.getWidth()/2,this.image.getHeight()/2);
        matrix.postTranslate(position.x, position.y);
    }

    /**
     * This rotates the image using a matrix
     * The rotation goes from the center which means that the position wont change
     * @param rotation float rotation in degrees
     */
    public void setRotation(float rotation){
        this.rotation = rotation;
        this.matrix.setRotate(rotation,this.image.getWidth()/2,this.image.getHeight()/2);
        matrix.postTranslate(position.x, position.y);
    }

    /**
     * this draws a bitmap using our matrix which contains the rotation and position of the image
     * @param canvas where it will be drawn to
     * @param context context of the view
     */
    @Override
    public void drawObject(Canvas canvas, Context context) {
        canvas.drawBitmap(image, matrix, null);
    }


    @Override
    public boolean outOfScreen(int width, int height, int startx, int starty) {
        return position.x > width + this.width || position.x < startx - this.width || position.y > height + this.height || this.height < starty - this.width;
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
     * @param bmp2 the 2nd bitmap, this one gets put on the x: 0 and y:0 pixels of this image
     * @return Bitmap image with bmp2 on top of the image.
     */
    public Bitmap combine(Bitmap bmp2) {
        if(bmp2==null){
            Log.e("Failed combo","null pointer");
            return image;
        }

        if(image.getWidth()<bmp2.getWidth() || image.getHeight()<bmp2.getHeight()){
            Log.e("Failed combo","bmp2 larger than image");
            Log.e("width image - bmp2",image.getWidth()+"-"+bmp2.getWidth());
            Log.e("height image - bmp2",image.getHeight()+"-"+bmp2.getHeight());
            return image;
        }
        Bitmap bmOverlay = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(image, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    /**
     * Internal version of combine method to change the Bitmap of this RImage.
     * @param bmp2 the 2nd bitmap which gets put on top of this one.
     */
    void combineInternal(Bitmap bmp2){
        if(bmp2==null){
            Log.e("Failed comb.","null pointer");
            return;
        }

        if(image.getWidth()<bmp2.getWidth() || image.getHeight()<bmp2.getHeight()){
            Log.e("Failed combo","bmp2 larger than image");
            Log.e("width image - bmp2",image.getWidth()+"-"+bmp2.getWidth());
            Log.e("height image - bmp2",image.getHeight()+"-"+bmp2.getHeight());
            return;
        }
        Bitmap bmOverlay = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(image, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        image=bmOverlay;
    }

}

package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import mailmaster.cedric.learntofly.R;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public class RImage implements RObject{

    private int xPos;
    private int yPos;


    private int width;
    private int height;

    private float rotation;

    private Bitmap image;

    private Matrix matrix;


    public RImage(int xPos, int yPos, int width, int height, float rotation, Bitmap image) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.image  = Bitmap.createScaledBitmap(
                image, width, height, false);
        this.matrix = new Matrix();
        rotateObject(rotation);
    }

    public void rotateObject(float rotation){
        this.rotation = rotation;
        this.matrix.setRotate(rotation,this.image.getWidth()/2,this.image.getHeight()/2);
        matrix.postTranslate(xPos, yPos);
    }



    private int calcXPos(int x){
        x = x;
        return x;
    }

    private int calcYPos(int y){
        y = y;
        return y;
    }

    @Override
    public void drawObject(Canvas canvas, Context context) {
        canvas.drawBitmap(image, matrix, null);

    }
}

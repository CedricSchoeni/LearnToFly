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


    private int width = 500;

    private float rotation;

    private Bitmap image;

    private Matrix matrix;


    public RImage(int xPos, int yPos, float rotation, Bitmap image) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.image = image;
        int t = 500/image.getWidth();


        this.matrix = createMatrix();

    }

    private Matrix createMatrix(){
        Matrix matrix = new Matrix();
        //matrix.setRotate (rotation, calcXPos(xPos), calcXPos(yPos));
        matrix.postScale(1, 1);
        matrix.preRotate(rotation, xPos, yPos);
        return matrix;
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

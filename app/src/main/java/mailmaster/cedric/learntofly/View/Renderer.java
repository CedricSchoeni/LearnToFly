package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

public class Renderer extends View {

    ResourceManager rm;
    Context context;
    Handler handler;

    public List<RObject> renderList;


    public Renderer(Context context) {
        super(context);
        rm = new ResourceManager();
        this.context = context;

        renderList = new ArrayList<RObject>();

        handler = new Handler();

        /**
         * most important code
         RObject roi1 = new RImage(85, 155, 150, 50, 225, rm.drawableToBitmap(context, R.drawable.elkenholz));
         RObject roi2 = new RImage(155, 155, 150, 50, 315, rm.drawableToBitmap(context, R.drawable.elkenholz));
         RObject roi3 = new RImage(155, 225, 150, 50, 45, rm.drawableToBitmap(context, R.drawable.elkenholz));
         RObject roi4 = new RImage(85, 225, 150, 50, 135, rm.drawableToBitmap(context, R.drawable.elkenholz));

         RObject roi5 = new RImage(50, 50, 150, 50, 315, rm.drawableToBitmap(context, R.drawable.elkenholz));
         RObject roi6 = new RImage(260, 120, 150, 50, 45, rm.drawableToBitmap(context, R.drawable.elkenholz));
         RObject roi7 = new RImage(185, 325, 150, 50, 135, rm.drawableToBitmap(context, R.drawable.elkenholz));
         RObject roi8 = new RImage(-20, 260, 150, 50, 225, rm.drawableToBitmap(context, R.drawable.elkenholz));

         renderList.add(roi1);
         renderList.add(roi2);
         renderList.add(roi3);
         renderList.add(roi4);
         renderList.add(roi5);
         renderList.add(roi6);
         renderList.add(roi7);
         renderList.add(roi8);*/


        startRendering();
    }

    public void startRendering(){
        handler.postDelayed(periodicUpdate, 33);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        for (RObject ro : renderList){
            ro.drawObject(canvas, context);
        }
        //activateDebugLines(canvas);

    }


    private void activateDebugLines(Canvas c){
        Paint p = new Paint();
        p.setColor(Color.RED);
        for (int i = 0; i < 15; i++){
            c.drawLine(i * 25,0, i * 25, 600, p);
        }
    }

    private Runnable periodicUpdate = new Runnable () {
        @Override
        public void run() {
            // scheduled another events to be in 10 seconds later
            handler.postDelayed(periodicUpdate, 33);
            // below is whatever you want to do
            invalidate();
        }
    };
}

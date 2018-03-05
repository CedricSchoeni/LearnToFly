package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.MainMenu;
import mailmaster.cedric.learntofly.Physics.FVector;

/**
 * Created by adriano.campiotti on 01.03.2018.
 */

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

public class Renderer extends View {

    public static final int FPS = 60;
    public static final int FPS_DELAY = 1000 / FPS;
    public boolean playing = false;



    Game g;
    MainActivity main;

    Context context;
    Handler handler;



    public List<RObject> renderListFG;
    public List<RObject> renderListBG;


    public Renderer(Context context, MainActivity main) {
        super(context);
        this.context = context;
        this.main = main;

        renderListFG = new ArrayList<RObject>();
        renderListBG = new ArrayList<RObject>();

        handler = new Handler();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        g = new Game(this);
    }

    public void addObjectToBackground(RObject ro){
        renderListBG.add(ro);
    }

    public void removeObjectFromBackground(RObject ro){
        renderListBG.remove(ro);
    }


    public void addObjectToForeground(RObject ro){
        renderListFG.add(ro);
    }

    public void removeObjectFromForeground(RObject ro){
        renderListFG.remove(ro);
    }

    public void startRendering(){
        this.setOnTouchListener(handleTouch);
    }

    public void startGame(){
        playing = true;
        g.startGame();
        handler.postDelayed(periodicUpdate, FPS_DELAY);

    }

    public void stopGame(){
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(main,MainMenu.class);
        main.startActivity(intent);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // draw background first to prevent unwanted overlapping
        for (RObject ro : renderListBG){
            ro.drawObject(canvas, context);
        }

        for (RObject ro : renderListFG){
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
            handler.postDelayed(periodicUpdate, FPS_DELAY);
            // below is whatever you want to do
            g.update();
            invalidate();

        }
    };


    private View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Log.i("TAG", "touched down");

                    if (playing)
                        if (x > getWidth()/2)
                            g.rightSideClick();
                        else
                            g.leftSideClick();
                        g.handlePushDown();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    //Log.i("TAG", "touched up");
                    g.handleReleaseUp();
                    break;
            }
            return true;
        }
    };


}

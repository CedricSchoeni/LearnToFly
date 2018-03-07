package mailmaster.cedric.learntofly.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.MainMenu;

/**
 * Created by cedric.schoeni on 01.03.2018.
 * this class is responsible for bringing everything on the screen
 */


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

        renderListFG = new ArrayList<>();
        renderListBG = new ArrayList<>();
        //TODO main.getIntent().getExtras();
        handler = new Handler();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        g = new Game(this);
    }

    /**
     * this adds a new object to the renderListBG
     * @param ro RObject will be added to renderListBG
     */
    public void addObjectToBackground(RObject ro){
        renderListBG.add(ro);
    }

    /**
     * this removes a existing object to the renderListBG
     * @param ro RObject will be removes from renderListBG
     */
    public void removeObjectFromBackground(RObject ro){
        renderListBG.remove(ro);
    }

    /**
     * this adds a new object to the renderListFG
     * @param ro RObject will be added to renderListFG
     */
    public void addObjectToForeground(RObject ro){
        renderListFG.add(ro);
    }

    /**
     * this removes a existing object to the renderListBG
     * @param ro RObject will be removed from renderListBG
     */
    public void removeObjectFromForeground(RObject ro){
        renderListFG.remove(ro);
    }

    /**
     * OnTouch Events will be registered after the execution of this method
     */
    public void startRendering(){
        this.setOnTouchListener(handleTouch);
    }

    /**
     * After the game has initialized everything the Runnable will be activated
     */
    public void startGame(){
        playing = true;
        g.startGame();
        handler.postDelayed(periodicUpdate, FPS_DELAY);

    }

    /**
     * The OnTouchHandler will be removed and the view changes to the MainMenu
     */
    public void stopGame(){
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(main,MainMenu.class);
        main.startActivity(intent);
    }

    /**
     * This method will be called through the invalidate(); method in the periodicUpdate
     * It redraws every single element in the renderLists every FPS_DELAY
     * There are 2 render lists to make sure certain objects will always be covered by the foreground elements
     * @param canvas gridLayout in our case
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // draw background first to prevent unwanted overlapping
        for (RObject ro : renderListBG){
            ro.drawObject(canvas, context);
        }

        for (RObject ro : renderListFG){
            ro.drawObject(canvas, context);
        }
    }


    /**
     * this will call the Game.update() method and force the onDraw method every FPS_DELAY
     */
    private Runnable periodicUpdate = new Runnable () {
        @Override
        public void run() {
            // scheduled another events to be in FPS_DELAY seconds later
            handler.postDelayed(periodicUpdate, FPS_DELAY);
            // below is whatever you want to do
            g.update();
            invalidate();
        }
    };

    /**
     * This handler will fire whenever the screen is touched
     * it saves x and y position of the click and then determines what to do with that information
     * in our case if the right side is touched the character will rotate to the right side
     * same goes for the left side
     */
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

/*
  most important code
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
}

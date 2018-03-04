package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mailmaster.cedric.learntofly.Game.Cloud;
import mailmaster.cedric.learntofly.Game.MovableObject;
import mailmaster.cedric.learntofly.Game.Player;
import mailmaster.cedric.learntofly.Game._Stages.Stages;
import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;

/**
 * Created by Cedric on 02.03.2018.
 */

public class Game{

    private Renderer r;

    private final int CLOUD_DELAY = 1000;
    private int cloudTimer = 0;

    private int wind_delay = 2500;
    private int windTimer = 0;
    private FVector wind = new FVector(0,0);
    private float windRotation = 0;

    private final int CLOUD_LIMIT = 4;
    private List<Cloud> clouds;

    private Player player;


    private RObject background_image;
    private RObject background_image_grass_bg;
    private RObject background_image_grass_fg;
    private RObject launcher;

    public int CANVAS_WIDTH;
    public int CANVAS_HEIGHT;


    private int lateUpdateDelay;
    private int currentLateUpdateTick = 0;
    private boolean playing = false;

    public FVector gamePosition = new FVector(0,0);
    public FVector screenPosition = new FVector(0,0);
    public final float screenFactor = 0.1f; // actual position of background objects is screenFactor * gamePosition

    Handler handler;

    // should have their own class that keeps track of all textviews
    TextView distanceView;
    TextView heightView;
    TextView speedView;
    TextView windView;

    List<ProgressBar> proBars = new ArrayList<ProgressBar>();

    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    ProgressBar progressBar4;

    List<ImageButton> buttons = new ArrayList<ImageButton>();

    ImageButton stage1;
    ImageButton stage2;
    ImageButton stage3;
    ImageButton stage4;

    public Game(Renderer r){
        this.r = r;

        clouds = new ArrayList<Cloud>();
        handler = new Handler();

        CANVAS_WIDTH = r.getWidth();
        CANVAS_HEIGHT = r.getHeight();
        lateUpdateDelay = r.FPS_DELAY * 10;
        distanceView = (TextView)r.main.findViewById(R.id.textView);
        heightView = (TextView)r.main.findViewById(R.id.textView2);
        speedView = (TextView)r.main.findViewById(R.id.textView3);
        windView = (TextView)r.main.findViewById(R.id.textView4);

        stage1 = (ImageButton) r.main.findViewById(R.id.imageButton1);
        stage2 = (ImageButton) r.main.findViewById(R.id.imageButton2);
        stage3 = (ImageButton) r.main.findViewById(R.id.imageButton3);
        stage4 = (ImageButton) r.main.findViewById(R.id.imageButton4);

        buttons.add(stage1);
        buttons.add(stage2);
        buttons.add(stage3);
        buttons.add(stage4);

        progressBar1 = (ProgressBar) r.main.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) r.main.findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) r.main.findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) r.main.findViewById(R.id.progressBar4);

        proBars.add(progressBar1);
        proBars.add(progressBar2);
        proBars.add(progressBar3);
        proBars.add(progressBar4);

        initGame();
        r.startRendering();
    }

    public void startGame(){
        r.addObjectToForeground(player.model);
        player.activateLauncher();
    }

    private void initGame(){
        initForeGround();
        initBackground();
        initUI();
    }

    private void initUI(){

        for (int i = 0; i < player.stages.size(); i++){
            Drawable d = r.main.getResources().getDrawable(player.stages.get(i).getModel().imageID );
            // add values to progress bars
            proBars.get(i).setMax((int) player.stages.get(i).getFuel());
            proBars.get(i).setProgress((int) player.stages.get(i).getFuel());
            // add image width and height to imagebutton
            buttons.get(i).setImageDrawable(d);
            buttons.get(i).getLayoutParams().height = 64;
            buttons.get(i).getLayoutParams().width = 64;
            buttons.get(i).requestLayout();
            // add listener to the buttons
            addListener(buttons.get(i));
        }
        for (int i = player.stages.size(); i < 4; i++){
            proBars.get(i).setVisibility(View.GONE);
            buttons.get(i).setVisibility(View.GONE);
        }
        handler.postDelayed(periodicUpdate, Renderer.FPS_DELAY);
    }

    private void addListener(ImageButton b){
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                player.startStage();
            }
        });
    }

    private void initBackground(){
        background_image = new RImage(0,0,CANVAS_WIDTH, CANVAS_HEIGHT, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.background), R.drawable.background);
        background_image_grass_bg = new RImage(0,0,CANVAS_WIDTH, CANVAS_HEIGHT, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.background_grass_bg), R.drawable.background_grass_bg);
        background_image_grass_fg = new RImage(0,0,CANVAS_WIDTH, CANVAS_HEIGHT, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.background_grass_fg), R.drawable.background_grass_fg);
        player.launcher.setModel(new RImage(0,0,CANVAS_WIDTH, CANVAS_HEIGHT, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.canon_background), R.drawable.canon_background));
        launcher = player.launcher.getModel();
        r.addObjectToBackground(background_image);
        r.addObjectToBackground(background_image_grass_bg);
        r.addObjectToBackground(launcher);
        r.addObjectToBackground(background_image_grass_fg);
    }

    private void initForeGround(){
        player = new Player(new RImage(CANVAS_WIDTH/2 - 25,CANVAS_HEIGHT*1.75f/3 - 25,50, 50, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.elkenholz), R.drawable.elkenholz), 10);
    }




    public void update(){


        if (player.stagesActive){
            player.update();

            for (int i = 0; i < player.stages.size(); i++){
                if (player.stages.get(i).getActive())
                    proBars.get(i).setProgress((int)(player.stages.get(i).getFuel() - player.frameCounter));
            }
        }

        this.gamePosition = player.updatePosition();

        currentLateUpdateTick += r.FPS_DELAY;
        if (currentLateUpdateTick > lateUpdateDelay){
            currentLateUpdateTick = 0;
            lateUpdate();
        }

        moveBackgroundObjects();

        // Wind
        wind();
        windTimer += r.FPS_DELAY;
        if (windTimer >= wind_delay){
            wind_delay = (int) (Math.random() * 10000) + 5000;
            windTimer = 0;
            if (wind.x == 0){
                float x = (float)(Math.random() * 3)- 1.5f;
                wind.x = x;
            } else {
                wind.x = 0;
            }
        }


        // Clouds
        Iterator<Cloud> i = clouds.iterator();
        while (i.hasNext()) {
            RObject a = i.next().model;
            if (a.outOfScreen(CANVAS_WIDTH, CANVAS_HEIGHT)) {
                i.remove();
                r.removeObjectFromBackground(a);
            }
        }

        cloudTimer += r.FPS_DELAY;
        if (cloudTimer >= CLOUD_DELAY){
            cloudTimer = 0;
            if (clouds.size() < CLOUD_LIMIT){
                int x = (int) (Math.random() * CANVAS_WIDTH);
                int y = (int) (Math.random() * CANVAS_HEIGHT);

                Cloud cloud = new Cloud(new RImage(x,y,250, 125, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.cloud_v1), R.drawable.cloud_v1));
                r.addObjectToBackground(cloud.model);
                clouds.add(cloud);

            }
        }
    }

    private void wind(){
        windRotation = wind.x;
    }

    private void moveBackgroundObjects(){

        calcPosition();
        for (Cloud c : clouds){
            c.model.updatePosition(screenPosition);
        }
        background_image_grass_bg.updatePosition(screenPosition);
        background_image_grass_fg.updatePosition(screenPosition);
        launcher.updatePosition(screenPosition);
    }

    private void calcPosition(){
        screenPosition.set(player.getSpeedVector().x * -1, player.getSpeedVector().y);
        screenPosition.mult(screenFactor);

    }

    private void lateUpdate(){
        String t1 = "Distance: " + Float.toString(Math.round(gamePosition.x)) + " m";
        String t2 = "Height: " + Float.toString(Math.round(gamePosition.y)) + " m";
        String t3 = "Speed: " + Float.toString(Math.round(player.getSpeed() * Renderer.FPS_DELAY)) + " m/s";
        String t4 = "Wind Speed: " + Float.toString(Math.round(wind.x * Renderer.FPS_DELAY)) + " m/s";
        distanceView.setText(t1);
        heightView.setText(t2);
        speedView.setText(t3);
        windView.setText(t4);
    }

    public void handlePushDown(){
        if (!r.playing){
            r.startGame();
        }
    }

    public void handleReleaseUp(){
        rotation = 0;
    }

    float rotation = 0;

    public void leftSideClick(){
        rotation = -4f;
    }

    public void rightSideClick(){
        rotation = 4f;
    }

    private Runnable periodicUpdate = new Runnable () {
        @Override
        public void run() {
            // scheduled another events to be in 10 seconds later
            handler.postDelayed(periodicUpdate, Renderer.FPS_DELAY);
            // below is whatever you want to do
            player.addRotation(rotation);
            player.addRotation(windRotation);

        }
    };


}

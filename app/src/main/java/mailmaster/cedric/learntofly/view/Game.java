package mailmaster.cedric.learntofly.view;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mailmaster.cedric.learntofly.game.Cloud;
import mailmaster.cedric.learntofly.game.Player;
import mailmaster.cedric.learntofly.physics.FVector;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.resources.ResourceManager;
import mailmaster.cedric.learntofly.sql.DatabaseHelper;

/**
 * Created by Cedric on 02.03.2018.
 */

public class Game{

    private Renderer r;

    private int wind_delay = 2500;
    private int windTimer = 0;
    private FVector wind = new FVector(0,0);
    private float windRotation = 0;

    private int CLOUD_DELAY = 500;
    private int cloudTimer = 0;
    private List<Cloud> clouds;

    private Player player;
    float rotation = 0;

    private int endTimer = 0;
    private int oldVelocity = 0;


    private RObject background_image_grass_bg;
    private RObject background_image_grass_fg;
    private RObject launcher;

    private int CANVAS_WIDTH;
    private int CANVAS_HEIGHT;


    private int lateUpdateDelay;
    private int currentLateUpdateTick = 0;
    private boolean playing = false;

    private FVector gamePosition = new FVector(0,0);
    private FVector screenPosition = new FVector(0,0);

    Handler handler;

    // should have their own class that keeps track of all textviews
    TextView distanceView;
    TextView heightView;
    TextView speedView;
    TextView windView;

    List<ProgressBar> proBarsStages = new ArrayList<>();
    List<ProgressBar> proBarsBoosts = new ArrayList<>();

    ProgressBar progressBar1Stage;
    ProgressBar progressBar2Stage;
    ProgressBar progressBar3Stage;
    ProgressBar progressBar4Stage;

    ProgressBar progressBar1Boost;
    ProgressBar progressBar2Boost;
    ProgressBar progressBar3Boost;
    ProgressBar progressBar4Boost;

    List<ImageButton> buttonsStage = new ArrayList<>();
    List<ImageButton> buttonsBoosts = new ArrayList<>();

    ImageButton stage1;
    ImageButton stage2;
    ImageButton stage3;
    ImageButton stage4;

    ImageButton boost1;
    ImageButton boost2;
    ImageButton boost3;
    ImageButton boost4;

    DatabaseHelper dbhelper;

    int temp=0;
    public Game(Renderer r){
        this.r = r;

        clouds = new ArrayList<>();
        handler = new Handler();

        CANVAS_WIDTH = r.getWidth();
        CANVAS_HEIGHT = r.getHeight();
        lateUpdateDelay = Renderer.FPS_DELAY * 10;


        distanceView = r.main.findViewById(R.id.textView);
        heightView = r.main.findViewById(R.id.textView2);
        speedView = r.main.findViewById(R.id.textView3);
        windView = r.main.findViewById(R.id.textView4);

        stage1 = r.main.findViewById(R.id.imageButton1);
        stage2 = r.main.findViewById(R.id.imageButton2);
        stage3 = r.main.findViewById(R.id.imageButton3);
        stage4 = r.main.findViewById(R.id.imageButton4);

        buttonsStage.add(stage1);
        buttonsStage.add(stage2);
        buttonsStage.add(stage3);
        buttonsStage.add(stage4);

        boost1 = r.main.findViewById(R.id.imageButton5);
        boost2 = r.main.findViewById(R.id.imageButton6);
        boost3 = r.main.findViewById(R.id.imageButton7);
        boost4 = r.main.findViewById(R.id.imageButton8);

        buttonsBoosts.add(boost1);
        buttonsBoosts.add(boost2);
        buttonsBoosts.add(boost3);
        buttonsBoosts.add(boost4);

        progressBar1Stage = r.main.findViewById(R.id.progressBar1);
        progressBar2Stage= r.main.findViewById(R.id.progressBar2);
        progressBar3Stage = r.main.findViewById(R.id.progressBar3);
        progressBar4Stage = r.main.findViewById(R.id.progressBar4);

        proBarsStages.add(progressBar1Stage);
        proBarsStages.add(progressBar2Stage);
        proBarsStages.add(progressBar3Stage);
        proBarsStages.add(progressBar4Stage);


        progressBar1Boost = r.main.findViewById(R.id.progressBar5);
        progressBar2Boost = r.main.findViewById(R.id.progressBar6);
        progressBar3Boost = r.main.findViewById(R.id.progressBar7);
        progressBar4Boost = r.main.findViewById(R.id.progressBar8);

        proBarsBoosts.add(progressBar1Boost);
        proBarsBoosts.add(progressBar2Boost);
        proBarsBoosts.add(progressBar3Boost);
        proBarsBoosts.add(progressBar4Boost);

        dbhelper = new DatabaseHelper(r.main);
        initGame();
        r.startRendering();
    }

    void startGame(){
        r.addObjectToForeground(player.model);
        player.activateLauncher();
    }

    private void initGame(){
        initForeGround();
        initBackground();
        initStages();
        initBoosts();
        handler.postDelayed(periodicUpdate, Renderer.FPS_DELAY);
    }

    private void initStages(){
        Log.e("Stages", Integer.toString(player.stages.size()));
        for (int i = 0; i < player.stages.size(); i++){

            Drawable d = r.main.getResources().getDrawable(player.stages.get(i).getDrawable() );
            // add values to progress bars
            proBarsStages.get(i).setMax((int) player.stages.get(i).getFuel());
            proBarsStages.get(i).setProgress((int) player.stages.get(i).getFuel());
            // add image width and height to imagebutton
            buttonsStage.get(i).setImageDrawable(d);
            buttonsStage.get(i).getLayoutParams().height = 64;
            buttonsStage.get(i).getLayoutParams().width = 64;
            buttonsStage.get(i).requestLayout();
            // add listener to the buttons
            addStageListener(buttonsStage.get(i));
        }
        for (int i = player.stages.size(); i < 4; i++){
            proBarsStages.get(i).setVisibility(View.GONE);
            buttonsStage.get(i).setVisibility(View.GONE);
        }
    }

    private void addStageListener(ImageButton b){
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                player.startStage();
            }
        });
    }

    private void initBoosts(){

        for (int i = 0; i < player.boosts.size(); i++){
            Drawable d = r.main.getResources().getDrawable(player.boosts.get(i).getDrawable() );
            // add values to progress bars
            proBarsBoosts.get(i).setMax((int) player.boosts.get(i).getFuel());
            proBarsBoosts.get(i).setProgress((int) player.boosts.get(i).getFuel());
            // add image width and height to imagebutton
            buttonsBoosts.get(i).setImageDrawable(d);
            buttonsBoosts.get(i).getLayoutParams().height = 64;
            buttonsBoosts.get(i).getLayoutParams().width = 64;
            buttonsBoosts.get(i).requestLayout();
            // add listener to the buttons
            addBoostListener(buttonsBoosts.get(i), i);
        }
        for (int i = player.boosts.size(); i < 4; i++){
            proBarsBoosts.get(i).setVisibility(View.GONE);
            buttonsBoosts.get(i).setVisibility(View.GONE);
        }
    }

    private void addBoostListener(ImageButton b, int nr){
        final int temp = nr;
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                player.startBoost(temp);
            }
        });
    }

    private void initBackground(){
        RObject background_image = new RImage(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.background), R.drawable.background);
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
        /*player = new Player(
                new RImage(CANVAS_WIDTH/2 - 44,CANVAS_HEIGHT*1.75f/3 - 44,88, 88, 0,
                        RImage.combine(ResourceManager.drawableToBitmap(r.context, R.drawable.character_beta_v1),
                                ResourceManager.drawableToBitmap(r.context, R.drawable.rocket_v1)), R.drawable.character_beta_v1), 10);*/
        /*
        RObject ro = new RImage(CANVAS_WIDTH/2 - 44,CANVAS_HEIGHT*1.75f/3 - 44,88, 88, 0,
                ResourceManager.drawableToBitmap(r.context, R.drawable.character_beta_v1),
                R.drawable.character_beta_v1);
        ro = ro.combine()*/

        player = new Player(
                new RImage(CANVAS_WIDTH/2 - 44,CANVAS_HEIGHT*1.75f/3 - 44,88, 88, 0,
                        ResourceManager.drawableToBitmap(r.context, R.drawable.character_beta_v1),
                        R.drawable.character_beta_v1), 10, r.main);
    }




    public void update(){

        updateFlightDevices();




        this.gamePosition = player.updatePosition();

        currentLateUpdateTick += Renderer.FPS_DELAY;
        if (currentLateUpdateTick > lateUpdateDelay){
            currentLateUpdateTick = 0;
            lateUpdate();
        }

        moveBackgroundObjects();

        // Wind
        wind();
        windTimer += Renderer.FPS_DELAY;
        if (windTimer >= wind_delay){
            wind_delay = (int) (Math.random() * 10000) + 5000;
            windTimer = 0;
            if (wind.x == 0){
                wind.x = (float)(Math.random() * 3)- 1.5f;
            } else {
                wind.x = 0;
            }
        }


        // Clouds
        Iterator<Cloud> i = clouds.iterator();
        while (i.hasNext()) {
            RObject a = i.next().model;
            if (a.outOfScreen(CANVAS_WIDTH + 250, CANVAS_HEIGHT + 125, -250, -125)) {
                i.remove();
                r.removeObjectFromBackground(a);
            }
        }

        cloudTimer += Renderer.FPS_DELAY;
        if (cloudTimer >= CLOUD_DELAY){
            CLOUD_DELAY = (player.getSpeed() < 10) ? 750 : (int)(75000/player.getSpeed());
            cloudTimer = 0;
            int CLOUD_LIMIT = 5;
            if (clouds.size() < CLOUD_LIMIT){
                int xHalf = CANVAS_WIDTH/2;
                int x = (player.velocity.x > 0) ? (int) (Math.random() * (CANVAS_WIDTH - xHalf) + xHalf) : (int) (Math.random() * (xHalf + 250) - 250);
                int y = (player.velocity.y > 0) ? -125 : CANVAS_HEIGHT + 125;
                Cloud cloud = new Cloud(new RImage(x,y,250, 125, 0, ResourceManager.drawableToBitmap(r.context, R.drawable.cloud_v1), R.drawable.cloud_v1));
                r.addObjectToBackground(cloud.model);
                clouds.add(cloud);

            }
        }

        checkForEnd();

    }

    private void updateFlightDevices(){
        player.update();
        for (int i = 0; i < player.stages.size(); i++){
            if (player.stages.get(i).getActive())
                proBarsStages.get(i).setProgress((int)(player.stages.get(i).getFuel() - player.stages.get(i).getTimeCounter()));
        }
        for (int i = 0; i < player.boosts.size(); i++){
            if (player.boosts.get(i).getActive())
                proBarsBoosts.get(i).setProgress((int)(player.boosts.get(i).getFuel() - player.boosts.get(i).getTimeCounter()));
        }
    }

    private void checkForEnd(){
        endTimer += Renderer.FPS_DELAY;
        int END_DELAY = 2500;
        if (endTimer >= END_DELAY) {
            endTimer = 0;
            int currentVelocity = (int) player.velocity.mag();
            int END_VELOCITY = 2;
            if (currentVelocity == oldVelocity && oldVelocity == END_VELOCITY){
                stopGame();
                r.stopGame();
            }
            oldVelocity = (int) player.velocity.mag();

        }
    }

    private void stopGame(){
        handler.removeCallbacksAndMessages(null);
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
        float screenFactor = 0.15f;
        screenPosition.mult(screenFactor);
    }

    private void lateUpdate(){
        String t1 = "Distance: " + String.format("%.2f", (gamePosition.x / 1000)) + " km";
        String t2 = "Height: " + String.format("%.2f", (gamePosition.y / 1000)) + " km";
        String t3 = "Speed: " + String.format("%.2f", (player.getSpeed() * Renderer.FPS_DELAY / 1000)) + " km/s";
        String t4 = "Wind Speed: " +  String.format("%.2f", (wind.x * Renderer.FPS_DELAY)) + " m/s";
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

package mailmaster.cedric.learntofly.view;

import android.graphics.Bitmap;
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
import mailmaster.cedric.learntofly.game.flightdevices.FlightDevice;
import mailmaster.cedric.learntofly.physics.FVector;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.resources.ResourceManager;
import mailmaster.cedric.learntofly.sql.DatabaseHelper;

/**
 * Created by Cedric on 02.03.2018.
 * This is the game class where everything related to the game will be controlled
 */

class Game{

    private final Renderer r;

    private int wind_delay = 2500;
    private int windTimer = 0;
    private final FVector wind = new FVector(0,0);
    private float windRotation = 0;

    private int CLOUD_DELAY = 500;
    private int cloudTimer = 0;
    private final List<Cloud> clouds;

    private Player player;
    private float rotation = 0;

    private int endTimer = 0;
    private int oldVelocity = 0;


    private RObject background_image_grass_bg;
    private RObject background_image_grass_fg;
    private RObject launcher;

    private final int CANVAS_WIDTH;
    private final int CANVAS_HEIGHT;


    private final int lateUpdateDelay;
    private int currentLateUpdateTick = 0;
    private boolean playing = false;

    private FVector gamePosition = new FVector(0,0);
    private final FVector screenPosition = new FVector(0,0);

    private final Handler handler;

    // should have their own class that keeps track of all textviews
    private final TextView distanceView;
    private final TextView heightView;
    private final TextView speedView;
    private final TextView windView;

    private final List<ProgressBar> proBarsStages = new ArrayList<>();
    private final List<ProgressBar> proBarsBoosts = new ArrayList<>();

    private final ProgressBar progressBar1Stage;
    private final ProgressBar progressBar2Stage;
    private final ProgressBar progressBar3Stage;
    private final ProgressBar progressBar4Stage;

    private final ProgressBar progressBar1Boost;
    private final ProgressBar progressBar2Boost;
    private final ProgressBar progressBar3Boost;
    private final ProgressBar progressBar4Boost;

    private final List<ImageButton> buttonsStage = new ArrayList<>();
    private final List<ImageButton> buttonsBoosts = new ArrayList<>();

    private final ImageButton stage1;
    private final ImageButton stage2;
    private final ImageButton stage3;
    private final ImageButton stage4;

    private final ImageButton boost1;
    private final ImageButton boost2;
    private final ImageButton boost3;
    private final ImageButton boost4;

    private final DatabaseHelper dbhelper;

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

    /**
     * this is called from the renderer
     * the launcher will be prepared and the player model added to the renderlist
     */
    void startGame(){
        r.addObjectToForeground(player.model);
        player.activateLauncher();
    }

    /**
     * this will initialize every image for the foreground and background needed
     * it also creates the stages and boosts selected previously in the Shop
     * after that it starts the handler which
     */
    private void initGame(){
        initForeGround();
        initBackground();
        initStages();
        initBoosts();
        handler.postDelayed(periodicUpdate, Renderer.FPS_DELAY);
    }

    /**
     * This method will set the ImageButtons image and their height
     * if less than 4 stages were selected wont show empty buttons
     */
    private void initStages(){
        //Log.e("Stages", Integer.toString(player.stages.size()));
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

    /**
     * this method adds a listener to the ImageButton b which will activate all Stages on click
     * @param b ImageButton which will get the onClick Event
     */
    private void addStageListener(ImageButton b){
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                player.startStage();
            }
        });
    }

    /**
     * This method will set the ImageButtons image and their height
     * if less than 4 boosts were selected wont show empty buttons
     */
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

    /**
     * this method adds a listener to the ImageButton b which will activate the boost at index nr
     * @param b ImageButton which will get the onClick Event
     * @param nr the index of the BoostList in the player class
     */
    private void addBoostListener(ImageButton b, int nr){
        final int temp = nr;
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                player.startBoost(temp);
            }
        });
    }

    /**
     * this adds multiple images to the render list for the starting area including the launcher
     */
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

    /**
     * A new Player object is created
     * the character image is a combination of up to 4boosts 4stages and 1 character
     */
    private void initForeGround(){
        player = new Player(constructPlayerImage(), 10, r.main);

    }

    /**
     * This will create a list of stages based on what the user chose in the shop
     * @return List<FlightDevice> a list of stages
     */
    private List<FlightDevice> getStages(){
        List<FlightDevice> stages = new ArrayList<>();
        for(int i=1; i <5; i++){
            int tmp=0;
            try{//noinspection ConstantConditions
                tmp=r.main.getIntent().getExtras().getInt("stage"+i);}catch(Exception ignored){}
            //Log.e("stage"+i,""+tmp);
            if(tmp>0)
                stages.add(dbhelper.getStage(tmp));
        }
        return stages;
    }

    /**
     * This will create a list of boosts based on what the user chose in the shop
     * @return List<FlightDevice> a list of boosts
     */
    private List<FlightDevice> getBoosts(){
        List<FlightDevice> boosts = new ArrayList<>();
        for(int i=1; i <5; i++){
            int tmp=0;
            try{tmp=r.main.getIntent().getExtras().getInt("boost"+i);}catch(Exception ignored){}
            if(tmp>0)
                boosts.add(dbhelper.getBoost(tmp));
        }
        return boosts;
    }

    /**
     * in this function the character image is built
     * multiple bitmaps are combined to a final bitmap
     * @return RImage the character image for the player
     */
    private RImage constructPlayerImage(){
        Bitmap tmp = ResourceManager.drawableToBitmap(r.context, R.drawable.character);
        RImage rImage= new RImage(CANVAS_WIDTH/2 - 44,CANVAS_HEIGHT*1.75f/3 - 44,tmp.getWidth(), tmp.getHeight(), 0,
                tmp,
                R.drawable.character);
        List<FlightDevice> stages= getStages();
        List<FlightDevice> boosts = getBoosts();
        for(int i=0; i < stages.size(); i++){
            //Log.e((1+i)+"stage-image"+(i+1),""+imagechooser(stages.get(i),i+1));
            rImage.combineInternal(ResourceManager.drawableToBitmap(r.context,imagechooser(stages.get(i),i+1)));
        }
        rImage.combineInternal(ResourceManager.drawableToBitmap(r.context,R.drawable.character));
        for(int i=0; i < boosts.size(); i++){
            //Log.e((1+i)+"boost-image"+(i+1),""+imagechooser(boosts.get(i),i+1));
            rImage.combineInternal(ResourceManager.drawableToBitmap(r.context,imagechooser(boosts.get(i),i+1)));
        }
        return rImage;
    }

    /**
     * there are 4 versions of the boost/stage image
     * depending on the position that the image is it has to chose the correct image
     * @param flightDevice the flightDevice where the image is
     * @param index position of the image
     * @return int index
     */
    private int imagechooser(FlightDevice flightDevice,int index){
        int tmp=-1;
        switch(index){
            case 1:
                tmp=flightDevice.getImage1();
                break;
            case 2:
                tmp=flightDevice.getImage2();
                break;
            case 3:
                tmp=flightDevice.getImage3();
                break;
            case 4:
                tmp=flightDevice.getImage4();
                break;
        }
        return tmp;
    }

    /**
     * This method is called every r.FPS_DELAY
     * 1: stages/boost fuel is updated -> updateFlightDevices();
     * 2: player position is updated
     * 3: lateUpdate is called every 10 * r.FPS_DELAY
     * 4: background items are moved to simulate that the character is moving
     * 5: a random wind period is started or an existing one ended - windRotation is changed accordingly
     * 6: Clouds spawn in a delay relative to the velocity the position is calculated by the direction of travel
     * 7: check if the game has ended
     */
    void update(){
        // 1
        updateFlightDevices();

        // 2
        this.gamePosition = player.updatePosition();

        // 3
        currentLateUpdateTick += Renderer.FPS_DELAY;
        if (currentLateUpdateTick > lateUpdateDelay){
            currentLateUpdateTick = 0;
            lateUpdate();
        }
        // 4
        moveBackgroundObjects();

        // 5
        windRotation = wind.x;
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

        // 6
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

        // 7
        checkForEnd();
    }

    /**
     * player.update() - Player updates the frameCounter of each active stage/boost
     * the progressBar changes visually
     */
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

    /**
     * If the target velocity is held for 2.5seconds the game will stop
     */
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

    /**
     * the Handler is stopped
     */
    private void stopGame(){
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * This method first calculates the position of the background images
     * the screenFactor is relative to the actual progress of the player
     * the position of clouds and the backgroundImages of the Launch are then updated
     */
    private void moveBackgroundObjects(){
        // calculate position
        screenPosition.set(player.getSpeedVector().x * -1, player.getSpeedVector().y);
        float screenFactor = 0.15f;
        screenPosition.mult(screenFactor);

        for (Cloud c : clouds){
            c.model.updatePosition(screenPosition);
        }
        background_image_grass_bg.updatePosition(screenPosition);
        background_image_grass_fg.updatePosition(screenPosition);
        launcher.updatePosition(screenPosition);
    }

    /**
     * The lateUpdate is called 10 times less than the update
     * This is because the text doesn't need to be set every so often and it looks better
     * It gets the information it needs and creates a String for that
     * this string will then be set as TextView text
     */
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

    /**
     * The game is started by clicking on the screen
     * This will then set off the launch
     */
    void handlePushDown(){
        if (!r.playing){
            r.startGame();
        }
    }

    /**
     * This event is called by the Renderer OnTouchHandler whenever the player stops clicking on the screen
     */
    void handleReleaseUp(){
        rotation = 0;
    }

    /**
     * This is also called by the Renderer OnTouchHandler but only if the left side of the screen is touched
     */
    void leftSideClick(){
        rotation = -4f;
    }

    /**
     * This is also called by the Renderer OnTouchHandler but only if the left side of the screen is touched
     */
    void rightSideClick(){
        rotation = 4f;
    }

    /**
     * this runnable updates the rotation of the player character every FPS_DELAY
     * this ensures a really smooth rotation of the image
     */
    private final Runnable periodicUpdate = new Runnable () {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, Renderer.FPS_DELAY);
            // below is whatever you want to do
            player.addRotation(rotation);
            player.addRotation(windRotation);
        }
    };


}

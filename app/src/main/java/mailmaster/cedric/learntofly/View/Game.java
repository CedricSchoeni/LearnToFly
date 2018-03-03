package mailmaster.cedric.learntofly.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mailmaster.cedric.learntofly.Game.Cloud;
import mailmaster.cedric.learntofly.Game.MovableObject;
import mailmaster.cedric.learntofly.Game.Player;
import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;

/**
 * Created by Cedric on 02.03.2018.
 */

public class Game{

    private Renderer r;
    private ResourceManager rm;

    private final int CLOUD_DELAY = 2500;
    private int cloudTimer = 0;

    private final int CLOUD_LIMIT = 8;
    private List<Cloud> clouds;

    private Player player;


    private RObject background_image;

    public int CANVAS_WIDTH;
    public int CANVAS_HEIGHT;


    private int lateUpdateDelay;
    private int currentLateUpdateTick = 0;

    public FVector gamePosition = new FVector(0,0);
    public FVector screenPosition = new FVector(0,0);
    public FVector lastScreenPosition = new FVector(0,0);
    public FVector screenDiff = new FVector(0,0);
    public final float screenFactor = 0.1f; // actual position of background objects is screenFactor * gameposition

    // should have their own class that keeps track of all textviews
    TextView distanceView;
    TextView heightView;

    public Game(Renderer r){
        this.r = r;
        rm = new ResourceManager();

        clouds = new ArrayList<Cloud>();

        CANVAS_WIDTH = r.getWidth();
        CANVAS_HEIGHT = r.getHeight();
        lateUpdateDelay = r.FPS_DELAY * 10;
        distanceView = (TextView)r.main.findViewById(R.id.textView);
        heightView = (TextView)r.main.findViewById(R.id.textView2);

        initGame();
        r.startRendering();

    }

    public void initGame(){
        initBackground();
        initForeGround();
    }

    private void initBackground(){
        background_image = createRObject(0,0,CANVAS_WIDTH, CANVAS_HEIGHT, 0, rm.drawableToBitmap(r.context, R.drawable.background));
        r.addObjectToBackground(background_image);

    }

    private void initForeGround(){
        player = new Player(new RImage(CANVAS_WIDTH/2,CANVAS_HEIGHT/2,50, 50, 0, rm.drawableToBitmap(r.context, R.drawable.elkenholz)), 10);
        r.addObjectToForeground(player.model);
    }

    public void update(){

        this.gamePosition = player.updatePosition();


        currentLateUpdateTick += r.FPS_DELAY;
        if (currentLateUpdateTick > lateUpdateDelay){
            currentLateUpdateTick = 0;
            lateUpdate();
        }

        moveBackgroundObjects();

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
                int x = (int) (Math.random() * (CANVAS_WIDTH - 0)) + 0;
                int y = (int) (Math.random() * (CANVAS_HEIGHT - 0)) + 0;

                Cloud cloud = new Cloud(new RImage(x,y,250, 125, 0, rm.drawableToBitmap(r.context, R.drawable.cloud_v1)));
                r.addObjectToBackground(cloud.model);
                clouds.add(cloud);

            }
        }
    }

    private void moveBackgroundObjects(){

        calcPosition();
        //Log.e("PosX", Float.toString(this.screenDiff.x));
        //Log.e("PosY", Float.toString(this.screenDiff.y));
        for (Cloud c : clouds){
            c.model.updatePosition(screenDiff);
        }
    }

    private void calcPosition(){
        lastScreenPosition.set(screenPosition.x, screenPosition.y);
        screenPosition.set(gamePosition.x, gamePosition.y);
        screenPosition.mult(screenFactor);
        screenPosition.x = screenPosition.x * -1;
        screenPosition.y = screenPosition.y;
        screenDiff.set(screenPosition.x - lastScreenPosition.x, screenPosition.y - lastScreenPosition.y);
    }

    private void lateUpdate(){
        String t1 = "Distance: " + Float.toString(Math.round(gamePosition.x));
        String t2 = "Height: " + Float.toString(Math.round(gamePosition.y));
        distanceView.setText(t1);
        heightView.setText(t2);
    }

    public RObject createRObject(float xPos, float yPos, int width, int height, float rotation, Bitmap image){
        RObject ro = new RImage(xPos, yPos, width, height, rotation, image);
        return (RImage)ro;
    }

    public void handlePushDown(){
        //player.addRotation(5);
        player.addPower(25);
    }

    public void handleReleaseUp(){
        player.subPower(25);
    }


}

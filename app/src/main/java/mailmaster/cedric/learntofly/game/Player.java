package mailmaster.cedric.learntofly.game;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.game.launchers.Launcher_Canon_v1;
import mailmaster.cedric.learntofly.game.launchers.Launchers;
import mailmaster.cedric.learntofly.game.flightdevices.FlightDevice;
import mailmaster.cedric.learntofly.physics.FVector;
import mailmaster.cedric.learntofly.sql.DatabaseHelper;
import mailmaster.cedric.learntofly.view.RObject;
import mailmaster.cedric.learntofly.view.Renderer;

/**
 * Created by Cedric on 03.03.2018.
 * This is the Player object class with all functionality to move the player
 */
public class Player extends PhysicsObject implements MovableObject {

    final Handler handler = new Handler();

    public RObject model;

    public List<FlightDevice> stages;
    public List<FlightDevice> boosts;
    public Launchers launcher;
    private MainActivity main;
    private DatabaseHelper dbhelper;

    public Player(RObject model, float mass, MainActivity main) {
        super(mass, model.getRotation());
        stages = new ArrayList<>();
        boosts = new ArrayList<>();
        this.model = model;
        this.main=main;
        launcher = new Launcher_Canon_v1();
        dbhelper = new DatabaseHelper(main);
        addStages();
        addBoosts();

        for (FlightDevice s : stages){
            super.mass += s.getMass();
        }
        for (FlightDevice s : boosts){
            super.mass += s.getMass();
        }
    }

    /**
     * This method adds the selected Stages to its List
     */
    private void addStages(){
        for(int i=1; i <5; i++){
            int tmp=0;
            try{//noinspection ConstantConditions
                tmp=main.getIntent().getExtras().getInt("stage"+i);}catch(Exception ignored){}
            //Log.e("stage"+i,""+tmp);
            if(tmp>0)
                stages.add(dbhelper.getStage(tmp));
        }
    }

    /**
     * This method adds the selected Stages to its List
     */
    private void addBoosts(){
        for(int i=1; i <5; i++){
            int tmp=0;
            try{tmp=main.getIntent().getExtras().getInt("boost"+i);}catch(Exception ignored){}
            if(tmp>0)
            boosts.add(dbhelper.getBoost(tmp));
        }
    }

    @Override
    public void addRotation(float r) {
        model.addRotation(r);
        if (super.rotation + r < 0)
            super.rotation = 360 + r;
        else
            super.rotation = (super.rotation + r) % 360f;
    }

    @Override
    public void setRotation(float r) {
        model.setRotation(r);
        super.rotation = r;
    }

    /**
     * Stages are all activated at the same time
     * They will be activated and add their power to the PhsysicsObject
     */
    public void startStage(){
        for (FlightDevice s : stages){
            if (!s.getActive()) {
                super.addPower(s.getPower());
                s.setActive(true);
            }
        }
    }

    /**
     * @return float speed this returns the velocity magnitude of the Velocity FVector
     */
    public float getSpeed(){
        return super.velocity.mag();
    }

    /**
     *
     * @return FVector velocity this returns the velocity FVector
     */
    public FVector getSpeedVector(){
        return super.velocity;
    }

    /**
     * this method will update the frameCounter in every activated boost and stage
     * if the frameCounter is greater than the fuel it will turn the rocket off by taking the power away
     */
    public void update(){
        // update stages if active
        for (FlightDevice s : stages){
            if (s.getActive()) {
                s.updateTimeCounter(Renderer.FPS_DELAY);
                if (s.getFuel() <= s.getTimeCounter()) {
                    s.setActive(false);
                    subPower(s.getPower());
                }
            }
        }
        // update boosts if active
        for (FlightDevice s : boosts){
            if (s.getActive()) {
                s.updateTimeCounter(Renderer.FPS_DELAY);
                if (s.getFuel() <= s.getTimeCounter()) {
                    s.setActive(false);
                    subPower(s.getPower());
                }
            }
        }
    }

    /**
     * Launcher will activate and add a Power
     * After the fuel is used up the power will be subtracted again
     */
    public void activateLauncher(){
        super.addPower(launcher.getPower());
        launcher.setActive(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                subPower(launcher.getPower());
                launcher.setActive(false);
            }
        }, (long)launcher.getFuel());
    }

    /**
     * Boosts can be activated separately
     * this will activate the boost nr and add the power
     * @param nr the position in the list
     */
    public void startBoost(int nr){
        if (!boosts.get(nr).getActive()) {
            super.addPower(boosts.get(nr).getPower());
            boosts.get(nr).setActive(true);
        }
    }
}

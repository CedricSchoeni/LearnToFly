package mailmaster.cedric.learntofly.game;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.game.flightdevices.boosts.Boost;
import mailmaster.cedric.learntofly.game.flightdevices.stages.Stage;
import mailmaster.cedric.learntofly.game.launchers.Launcher_Canon_v1;
import mailmaster.cedric.learntofly.game.launchers.Launchers;
import mailmaster.cedric.learntofly.game.flightdevices.FlightDevice;
import mailmaster.cedric.learntofly.physics.FVector;
import mailmaster.cedric.learntofly.view.RObject;
import mailmaster.cedric.learntofly.view.Renderer;

/**
 * Created by Cedric on 03.03.2018.
 */

public class Player extends PhysicsObject implements MovableObject {

    public RObject model;

    public List<FlightDevice> stages;
    public List<FlightDevice> boosts;
    public Launchers launcher;

    public Player(RObject model, float mass) {
        super(mass, model.getRotation());
        stages = new ArrayList<FlightDevice>();
        boosts = new ArrayList<FlightDevice>();
        this.model = model;

        stages.add(new Stage());
        stages.add(new Stage());
        stages.add(new Stage());

        boosts.add(new Boost());
        boosts.add(new Boost());
        boosts.add(new Boost());
        launcher = new Launcher_Canon_v1();
    }
    private void stages(){

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

    public void startStage(){
        for (FlightDevice s : stages){
            if (!s.getActive()) {
                super.mass += s.getMass();
                super.addPower(s.getPower());
                s.setActive(true);
            }
        }
    }

    public float getSpeed(){
        return super.velocity.mag();
    }

    public FVector getSpeedVector(){
        return super.velocity;
    }

    public void update(){
        // update stages if active
        for (FlightDevice s : stages){
            if (s.getActive()) {
                s.updateTimeCounter(Renderer.FPS_DELAY);
                if (s.getFuel() <= s.getTimeCounter()) {
                    s.setActive(false);
                    //super.mass -= s.getMass();
                    subPower(s.getPower());
                }
            }
        }

        for (FlightDevice s : boosts){
            if (s.getActive()) {
                s.updateTimeCounter(Renderer.FPS_DELAY);
                if (s.getFuel() <= s.getTimeCounter()) {
                    s.setActive(false);
                    //super.mass -= s.getMass();
                    subPower(s.getPower());
                }
            }
        }
    }

    final Handler handler = new Handler();


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

    public void startBoost(int nr){
        if (!boosts.get(nr).getActive()) {
            super.mass += boosts.get(nr).getMass();
            super.addPower(boosts.get(nr).getPower());
            boosts.get(nr).setActive(true);
        }
    }



}

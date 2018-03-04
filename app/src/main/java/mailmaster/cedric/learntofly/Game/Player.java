package mailmaster.cedric.learntofly.Game;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.Game._Launchers.Launcher_Canon_v1;
import mailmaster.cedric.learntofly.Game._Launchers.Launchers;
import mailmaster.cedric.learntofly.Game._Stages.Stage_Rocket_v1;
import mailmaster.cedric.learntofly.Game._Stages.Stage_Rocket_v2;
import mailmaster.cedric.learntofly.Game._Stages.Stages;
import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.Physics.PhysicEngine;
import mailmaster.cedric.learntofly.View.RObject;
import mailmaster.cedric.learntofly.View.Renderer;

/**
 * Created by Cedric on 03.03.2018.
 */

public class Player extends PhysicsObject implements MovableObject {

    public RObject model;

    public List<Stages> stages;
    public Launchers launcher;

    public boolean stagesActive = false;
    public float frameCounter = 0;

    public Player(RObject model, float mass) {
        super(mass, model.getRotation());
        stages = new ArrayList<Stages>();
        this.model = model;


        stages.add(new Stage_Rocket_v1());
        stages.add(new Stage_Rocket_v2());
        launcher = new Launcher_Canon_v1();

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
        if (!stagesActive){
            for (Stages s : stages){
                super.mass += s.getMass();
                super.addPower(s.getPower());
                s.setActive(true);
            }
        }
        stagesActive = true;
    }

    public float getSpeed(){
        return super.velocity.mag();
    }

    public FVector getSpeedVector(){
        return super.velocity;
    }

    public void update(){
        frameCounter += Renderer.FPS_DELAY;
        for (Stages s : stages){

            if (s.getFuel() <= frameCounter && s.getActive()) {
                s.setActive(false);
                subPower(s.getPower());
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



}

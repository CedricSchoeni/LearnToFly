package mailmaster.cedric.learntofly.Game;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import mailmaster.cedric.learntofly.Game._Launchers.Launcher_Canon_v1;
import mailmaster.cedric.learntofly.Game._Launchers.Launchers;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage_Rocket_v1;
import mailmaster.cedric.learntofly.Game.FlightDevices._Stages.Stage_Rocket_v2;
import mailmaster.cedric.learntofly.Game.FlightDevices.FlightDevice;
import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.View.RObject;
import mailmaster.cedric.learntofly.View.Renderer;

/**
 * Created by Cedric on 03.03.2018.
 */

public class Player extends PhysicsObject implements MovableObject {

    public RObject model;

    public List<FlightDevice> stages;
    public Launchers launcher;

    public boolean stagesActive = false;
    public float frameCounter = 0;

    public Player(RObject model, float mass) {
        super(mass, model.getRotation());
        stages = new ArrayList<FlightDevice>();
        this.model = model;


        stages.add(new Stage_Rocket_v1());
        stages.add(new Stage_Rocket_v2());
        stages.add(new Stage_Rocket_v1());
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
            for (FlightDevice s : stages){
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
        boolean empty = true;
        frameCounter += Renderer.FPS_DELAY;
        for (FlightDevice s : stages){
            if (s.getFuel() > 0)
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

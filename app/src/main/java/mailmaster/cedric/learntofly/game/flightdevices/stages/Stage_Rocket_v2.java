package mailmaster.cedric.learntofly.game.flightdevices.stages;

import mailmaster.cedric.learntofly.game.flightdevices.FlightDevice;
import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.resources.ResourceManager;
import mailmaster.cedric.learntofly.view.RImage;
import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by Cedric on 04.03.2018.
 */

public abstract class Stage_Rocket_v2 implements FlightDevice {

    private float frameCounter = 0;
    private boolean active = false;
    private float power = 100;
    private float mass = 5;
    private float fuel = 5000; // 1000 = 1sec
    private RObject model = new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, R.drawable.rocket_v2), R.drawable.rocket_v2);

    @Override
    public RImage getModel() {
        return (RImage)model;
    }

    @Override
    public float getPower() {
        return power;
    }

    @Override
    public float getFuel() {
        return fuel;
    }

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public boolean getActive() {
        return active;
    }

    @Override
    public void setActive(boolean b) {
        active = b;
    }

    @Override
    public void updateTimeCounter(float t) {
        frameCounter += t;
    }

    @Override
    public float getTimeCounter() {
        return frameCounter;
    }
}
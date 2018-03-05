package mailmaster.cedric.learntofly.Game.FlightDevices._Stages;

import mailmaster.cedric.learntofly.Game.FlightDevices.FlightDevice;
import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;
import mailmaster.cedric.learntofly.View.RImage;
import mailmaster.cedric.learntofly.View.RObject;

/**
 * Created by Cedric on 04.03.2018.
 */

public class Stage_Rocket_v2 implements FlightDevice {

    public boolean active = false;
    public float power = 100;
    public float mass = 5;
    public float fuel = 5000; // 1000 = 1sec
    public RObject model = new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, R.drawable.rocket_v2), R.drawable.rocket_v2);

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
}

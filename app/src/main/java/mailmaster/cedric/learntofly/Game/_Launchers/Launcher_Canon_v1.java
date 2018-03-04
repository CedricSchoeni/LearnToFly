package mailmaster.cedric.learntofly.Game._Launchers;

import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;
import mailmaster.cedric.learntofly.View.RImage;
import mailmaster.cedric.learntofly.View.RObject;

/**
 * Created by Cedric on 04.03.2018.
 */

public class Launcher_Canon_v1 implements Launchers{

    public boolean active = false;
    public float power = 500;
    public float fuel = 2000; // 1000 = 1sec
    public RObject model;

    @Override
    public RImage getModel() {
        return (RImage)model;
    }

    @Override
    public void setModel(RObject model) {
        this.model = model;
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
    public boolean getActive() {
        return active;
    }

    @Override
    public void setActive(boolean b) {
        active = b;
    }

}

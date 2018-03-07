package mailmaster.cedric.learntofly.game.launchers;

import mailmaster.cedric.learntofly.view.RImage;
import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by Cedric on 04.03.2018.
 */

public class Launcher_Canon_v1 implements Launchers{

    public boolean active = false;
    public float power = 5000;
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

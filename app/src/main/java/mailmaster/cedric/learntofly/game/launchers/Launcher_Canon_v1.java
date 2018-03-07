package mailmaster.cedric.learntofly.game.launchers;

import mailmaster.cedric.learntofly.view.RImage;
import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by Cedric on 04.03.2018.
 * This class is a fillerclass which should be replaced with a Launcher template class that would allow us
 * to instantiate different launchers - due to limited time we left this class at this state
 */

public class Launcher_Canon_v1 implements Launchers{

    private boolean active = false;
    private float power = 2000;
    private float fuel = 2000; // 1000 = 1sec
    private RObject model;

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

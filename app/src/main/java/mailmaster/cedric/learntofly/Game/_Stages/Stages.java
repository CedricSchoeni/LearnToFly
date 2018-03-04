package mailmaster.cedric.learntofly.Game._Stages;

import mailmaster.cedric.learntofly.View.RImage;

/**
 * Created by Cedric on 04.03.2018.
 */

public interface Stages {

    public RImage getModel();

    public float getPower();
    public float getFuel();
    public float getMass();
    public boolean getActive();
    public void setActive(boolean b);

}

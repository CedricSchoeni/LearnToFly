package mailmaster.cedric.learntofly.Game._Launchers;

import mailmaster.cedric.learntofly.View.RImage;
import mailmaster.cedric.learntofly.View.RObject;

/**
 * Created by Cedric on 04.03.2018.
 */

public interface Launchers {

    public RImage getModel();
    public void setModel(RObject model);

    public float getPower();
    public float getFuel();
    public boolean getActive();
    public void setActive(boolean b);

}

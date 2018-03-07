package mailmaster.cedric.learntofly.game.launchers;

import mailmaster.cedric.learntofly.view.RImage;
import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by Cedric on 04.03.2018.
 */

public interface Launchers {

    RImage getModel();
    void setModel(RObject model);

    float getPower();
    float getFuel();
    boolean getActive();
    void setActive(boolean b);

}

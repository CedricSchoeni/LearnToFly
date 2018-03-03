package mailmaster.cedric.learntofly.Game;

import mailmaster.cedric.learntofly.View.RObject;

/**
 * Created by Cedric on 03.03.2018.
 */

public class Cloud implements MovableObject {

    public RObject model;

    public Cloud(RObject model) {
        this.model = model;
    }

    @Override
    public void addRotation(float r) {
        model.addRotation(r);
    }

    @Override
    public void setRotation(float r) {
        model.setRotation(r);
    }
}

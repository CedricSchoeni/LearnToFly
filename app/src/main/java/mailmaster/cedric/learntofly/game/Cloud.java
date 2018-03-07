package mailmaster.cedric.learntofly.game;

import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by Cedric on 03.03.2018.
 * The class Cloud can be moved on the screen because of the MoveableObject Interface
 * In the current Version this class is pretty useless and could just be a RImage instance.
 */

public class Cloud implements MovableObject {

    public final RObject model;

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

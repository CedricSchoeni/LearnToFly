package mailmaster.cedric.learntofly.Game;

import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.Physics.PhysicEngine;
import mailmaster.cedric.learntofly.View.RObject;

/**
 * Created by Cedric on 03.03.2018.
 */

public class Player extends PhysicsObject implements MovableObject {

    public RObject model;

    public Player(RObject model, float mass) {
        super(mass, model.getRotation());

        this.model = model;
    }


    @Override
    public void addRotation(float r) {
        model.addRotation(r);
        super.rotation += r;
    }

    @Override
    public void setRotation(float r) {
        model.setRotation(r);
        super.rotation = r;
    }
}

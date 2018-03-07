package mailmaster.cedric.learntofly.physics;

/**
 * Created by cedric.schoeni on 02.03.2018.
 * The PhysicsEngine contains a few variables which are needed to simulate the effects of actual physics
 */

public class PhysicEngine {
    /**
     * The FVector GRAVITY simulates the effects of gravity on earth and is active throughout the whole flight.
     */
    public static final FVector GRAVITY = new FVector(0,-2.981f);
    /**
     * The FVector Air_Drag simulates the effects of Air drag as air resistance
     */
    public static FVector AIR_DRAG = new FVector(0.95f,0.95f);
}

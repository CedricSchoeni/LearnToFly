package mailmaster.cedric.learntofly.game;

import mailmaster.cedric.learntofly.physics.FVector;
import mailmaster.cedric.learntofly.physics.PhysicEngine;

/**
 * Created by Cedric on 03.03.2018.
 * This class was created to make it easier to add physical effects to objects on the screen
 */

public class PhysicsObject {

    private FVector position; // contains x and y position of object
    public FVector velocity; // contains xSpeed and ySpeed of object

    public float mass;
    private float power;
    private final float MAX_POWER_EFFICIENCY = 100; // if the power is greater than this it will take exactly 5 updatePositions() to reach maxSpeed.

    private float maxSpeed;
    private float currSpeed;
    private float speedIncrement;
    private float speedDecrement;

    public float rotation;

    public PhysicsObject(float mass, float rotation) {
        position = new FVector(0,0);
        velocity = new FVector(0,0);

        this.power = 0;
        this.mass = mass;
        this.rotation = rotation;
        updatePower();
    }

    /**
     * This method calculates the new position and how much the object will travel based on the forces affecting it.
     * step 1: The object will accelerate to the maxSpeed calculated in another function
     * step 2: If the Speed is higher than the max speed - this can happen if the object loses power and has to recalculate the maxSpeed - it will decrease in small increments to the maxSpeed
     * step 3: The object will hold the speed
     * step 4: gravity is applied
     * step 5: airDrag scales the Vector down to simulate air drag
     * @return FVector position the returned FVector contains the x and y coordinates of the object
     */
    public FVector updatePosition() {

        if (currSpeed < maxSpeed) {
            if (currSpeed + speedIncrement > maxSpeed){
                currSpeed = maxSpeed;
            } else {
                currSpeed += speedIncrement;
            }
            updateVelocity(currSpeed);
        } else {
            if (currSpeed > maxSpeed){
                if (currSpeed > maxSpeed){
                    currSpeed -= speedDecrement;
                }
                updateVelocity(currSpeed);
            } else {
                updateVelocity(maxSpeed);
            }
        }

        velocity.add(PhysicEngine.GRAVITY);

        //Log.e("currSpeed", Float.toString(currSpeed));
        //Log.e("Magnitude", Float.toString(velocity.mag()));
        //Log.e("Max Speed", Float.toString(maxSpeed));
        //Log.e("Current Speed", Float.toString(currSpeed));
        //Log.e("Acceleration Y", Float.toString(acceleration.y));
        //Log.e("Velocity Y", Float.toString(velocity.y));
        //Log.e("Velocity X", Float.toString(velocity.x));
        //Log.e("Angle", Float.toString(rotation));
        //Log.e("Power", Float.toString(power));

        velocity.scale(PhysicEngine.AIR_DRAG);
        position.add(velocity);
        return position;
    }

    /**
     * This method is used by the updatePosition method
     * It takes in a float which is the Magnitude of the velocity we have to convert to a FVector
     * To do this we need to use the trigonometric functions to split the speed in xSpeed and ySpeed based on the angle of travel
     * @param speed The speed in this case is the Magnitude of a Vector (a² + b² = c² => sqrt(c²) = Magnitude)
     */
    private void updateVelocity(float speed){
        velocity.reset();
        // create FVector with trigonometric functions
        // y and x are swapped
        float rot = (float)Math.toRadians(rotation%90.0000001);

        float x = (float)(speed * Math.sin(rot));
        float y = (float)(speed * Math.cos(rot));
        //Log.e("x", Float.toString(x));
        //Log.e("y", Float.toString(y));

        if (rotation > 90 && rotation < 180){
            float temp = x;
            //noinspection SuspiciousNameCombination
            x = y;
            y = temp * - 1;
        } else if (rotation >= 180 && rotation < 270){
            x *= -1;
            y *= -1;
        } else if (rotation >= 270){
            float temp = x;
            x = y * -1;
            y = temp;
        }

        velocity.add(new FVector(x, y));

    }

    /**
     * This method adds Power to the object which will result in it travelling faster
     * @param p the amout of power that will be added to the object.power
     */
    public void addPower(float p){
        this.power += p;
        updatePower();
    }

    /**
     * This method subtracts Power from the object which will result in it travelling slower
     * @param p the amout of power that will be subtracted from the object.power
     */
    public void subPower(float p){
        this.power -= p;
        updatePower();
    }

    /**
     * this method is called whenever the Power is changed
     * it will calculate a new maximum speed which can not be under 0
     * (to avoid errors the maxSpeed can not be less than 0, this prevents division by 0 from happening)
     * speedIncrement - this will be added every time updatePosition() is called and the currSpeed < maxSpeed
     * speedDecrement - this will be subtracted every time updatePosition() is called and the currSpeed > maxSpeed
     */
    private void updatePower(){
        speedDecrement = maxSpeed / 100;
        maxSpeed = ((power * 10) - mass) / 5;
        maxSpeed = (maxSpeed > 0) ? maxSpeed : 0.0000001f;
        float powerDelay = (maxSpeed > MAX_POWER_EFFICIENCY) ? 5 : maxSpeed / MAX_POWER_EFFICIENCY * 5;
        speedIncrement = maxSpeed / powerDelay;
    }


}

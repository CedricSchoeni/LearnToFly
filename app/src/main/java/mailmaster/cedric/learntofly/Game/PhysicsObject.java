package mailmaster.cedric.learntofly.Game;

import android.telephony.PhoneStateListener;
import android.util.Log;

import mailmaster.cedric.learntofly.Physics.FVector;
import mailmaster.cedric.learntofly.Physics.PhysicEngine;

/**
 * Created by Cedric on 03.03.2018.
 */

public class PhysicsObject {

    private FVector position;
    public FVector velocity;
    private FVector acceleration;

    public float mass;
    private float power;
    private final float MAX_POWER_EFFICIENCY = 100;

    private float maxSpeed;
    private float currSpeed;
    private float speedIncrement;
    private float speedDecrement;
    private FVector lastSpeed;


    public float rotation;

    public PhysicsObject(float mass, float rotation) {
        position = new FVector(0,0);
        velocity = new FVector(0,0);
        acceleration = new FVector(0,0);
        lastSpeed = new FVector(0,0);

        this.power = 0;
        this.mass = mass;
        this.rotation = rotation;
        updatePower();
    }

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



        //acceleration.add(PhysicEngine.GRAVITY);


        //Log.e("currSpeed", Float.toString(currSpeed));
        //Log.e("Magnitude", Float.toString(velocity.mag()));
        //Log.e("Max Speed", Float.toString(maxSpeed));
        //Log.e("Current Speed", Float.toString(currSpeed));
        //Log.e("Acceleration Y", Float.toString(acceleration.y));
        //Log.e("Velocity Y", Float.toString(velocity.y));
        //Log.e("Velocity X", Float.toString(velocity.x));
        //Log.e("Angle", Float.toString(rotation));
        //Log.e("Power", Float.toString(power));


        //velocity.add(acceleration);

        //velocity.scale(PhysicEngine.AIR_DRAG);
        position.add(velocity);
        return position;
    }

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

        lastSpeed = new FVector(x, y);

        velocity.add(lastSpeed);

    }

    public void addPower(float p){
        this.power += p;
        updatePower();
    }

    public void subPower(float p){
        this.power -= p;
        updatePower();
    }

    private void updatePower(){
        speedDecrement = maxSpeed / 100;
        maxSpeed = ((power * 10) - mass) / 5;
        maxSpeed = (maxSpeed > 0) ? maxSpeed : 0.0000001f;
        float powerDelay = (maxSpeed > MAX_POWER_EFFICIENCY) ? 5 : maxSpeed / MAX_POWER_EFFICIENCY * 5;
        speedIncrement = maxSpeed / powerDelay;
    }


}

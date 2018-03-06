package mailmaster.cedric.learntofly.Game.FlightDevices;

import mailmaster.cedric.learntofly.View.RImage;

/**
 * Interface used for both kinds of FlightDevices as they both require very similar functionality.
 */

public interface FlightDevice {


    RImage getModel();

    float getPower();
    float getFuel();
    float getMass();
    boolean getActive();
    void setActive(boolean b);

    void updateTimeCounter(float t);
    float getTimeCounter();

    int getDrawable();
    void setDrawable(int drawable);
    void setPower(float power);
    void setFuel(float fuel);
    void setMass(float mass);
    void setTimeCounter(float timeCounter);
    void setModel(int drawableID);
    void setName(String name);
    String getName();
    void setId(int id);
    int getId();
    int getPrice();
    void setPrice(int price);



}

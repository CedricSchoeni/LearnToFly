package mailmaster.cedric.learntofly.Game.FlightDevices;

import mailmaster.cedric.learntofly.View.RImage;

/**
 * Interface used for both kinds of FlightDevices as they both require very similar functionality.
 */

public interface FlightDevice {


    public RImage getModel();

    public float getPower();
    public float getFuel();
    public float getMass();
    public boolean getActive();
    public void setActive(boolean b);

    public void updateTimeCounter(float t);
    public float getTimeCounter();

    public int getDrawable();
    public void setDrawable(int drawable);
    public void setPower(float power);
    public void setFuel(float fuel);
    public void setMass(float mass);
    public void setTimeCounter(float timeCounter);
    public void setModel(int drawableID);
    public void setName(String name);
    public String getName();
    public void setId(int id);
    public int getId();
    public int getPrice();
    public void setPrice(int price);



}

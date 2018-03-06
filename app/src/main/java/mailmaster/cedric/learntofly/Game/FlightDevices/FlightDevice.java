package mailmaster.cedric.learntofly.Game.FlightDevices;

import mailmaster.cedric.learntofly.View.RImage;

/**
 * Interface used for both kinds of FlightDevices as they both require very similar functionality.
 */

public interface FlightDevice {

    /**
     * This returns the RImage version of the model the FlightDevice has.
     * @return RImage - image of FlightDevice.
     */
    RImage getModel();

    /**
     * The Power determines the strength/force of the FlightDevice.
     * @return float power of FlightDevice
     */
    float getPower();

    /**
     * The Fuel determines how long a FlightDevice is able to run.
     * @return float Fuel of FlightDevice.
     */
    float getFuel();

    /**
     * The Mass determines the weight of a FlightDevice, more weight drags down the user more.
     * @return float Mass of the Flightdevice.
     */
    float getMass();

    /**
     * Active determines whether the FlightDevice is currently active and in use.
     * @return boolean if it's active or not.
     */
    boolean getActive();

    /**
     * The Drawable is the Resource (R) id of the drawable used in the model of the FlightDevice.
     * @return int R.drawable resource id of model image.
     */
    int getDrawable();

    /**
     * The Name is simply the visual name which the FlightDevice has and is mainly used in the shop to display it.
     * @return String name of the FlightDevice.
     */
    String getName();

    /**
     * The ID is used to keep track of the item and it's location in the database.
     * @return int Database entry ID of the FlightDevice.
     */
    int getId();

    /**
     * The Price determines how expensive it is to buy an item in the shop (currently unused).
     * @return float Price of the FlightDevice.
     */
    int getPrice();

    /**
     * The Type determines if the FlightDevice is a boost or a stage.
     * @return int type of FlightDevice(0 = boost , 1 = stage)
     */
    int getType();

    /**
     * The TimeCounter/FrameCounter is used to calculate acoordingly with the FPS Delay and FPS of the game.
     * @return float TimeCounter/FrameCounter of the FlightDevice.
     */
    float getTimeCounter();
    void setActive(boolean b);

    void updateTimeCounter(float t);

    void setDrawable(int drawable);
    void setPower(float power);
    void setFuel(float fuel);
    void setMass(float mass);
    void setTimeCounter(float timeCounter);
    void setModel(int drawableID);
    void setName(String name);
    void setId(int id);
    void setPrice(int price);
    void setType(int type);
}

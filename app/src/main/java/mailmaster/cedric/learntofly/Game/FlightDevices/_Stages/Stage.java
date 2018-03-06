package mailmaster.cedric.learntofly.Game.FlightDevices._Stages;

import mailmaster.cedric.learntofly.Game.FlightDevices.FlightDevice;
import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.Resources.ResourceManager;
import mailmaster.cedric.learntofly.View.RImage;
import mailmaster.cedric.learntofly.View.RObject;

/**
 * Created by adriano.campiotti on 06.03.2018.
 */

public class Stage implements FlightDevice{
    private float frameCounter = 0;
    private boolean active = false;
    private float power = 50;
    private float mass = 10;
    private float fuel = 10000; // 1000 = 1sec
    private String name="Rocket";
    private int id=0;
    private int price=0;
    private RObject model;// = new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, R.drawable.rocket_v1), R.drawable.rocket_v1);


    @Override
    public RImage getModel() {
        return (RImage)this.model;
    }

    @Override
    public float getPower() {
        return this.power;
    }

    @Override
    public float getFuel() {
        return this.fuel;
    }

    @Override
    public float getMass() {
        return this.mass;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean b) {
        this.active=b;
    }

    @Override
    public void updateTimeCounter(float t) {
        this.frameCounter+=t;
    }

    @Override
    public float getTimeCounter() {
        return this.frameCounter;
    }

    public void setMass(float mass){
        this.mass = mass;
    }

    public void setFrameCounter(float frameCounter) {
        this.frameCounter = frameCounter;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public void setModel(int drawableID) {
        if(MainActivity.context!=null)
        this.model = new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, drawableID), drawableID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

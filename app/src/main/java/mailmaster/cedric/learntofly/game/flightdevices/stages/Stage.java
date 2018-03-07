package mailmaster.cedric.learntofly.game.flightdevices.stages;

import mailmaster.cedric.learntofly.game.flightdevices.FlightDevice;
import mailmaster.cedric.learntofly.MainActivity;
import mailmaster.cedric.learntofly.R;
import mailmaster.cedric.learntofly.resources.ResourceManager;
import mailmaster.cedric.learntofly.view.RImage;
import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by campiotti on 06.03.2018.
 * Base Class for all Stages
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
    private RObject model= (MainActivity.context==null)? null : new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, R.drawable.rocket_v1), R.drawable.rocket_v1);
    private int drawable=0;
    private int type=1;

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

    @Override
    public void setTimeCounter(float frameCounter) {
        this.frameCounter = frameCounter;
    }
    @Override
    public void setPower(float power) {
        this.power = power;
    }
    @Override
    public void setFuel(float fuel) {
        this.fuel = fuel;
    }
    @Override
    public void setModel(int drawableID) {
        if(MainActivity.context!=null)
            this.model = new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, drawableID), drawableID);
        else
            this.setDrawable(drawableID);
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int getPrice() {
        return price;
    }
    @Override
    public void setPrice(int price) {
        this.price = price;
    }
    @Override
    public int getDrawable() {
        return drawable;
    }
    @Override
    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }
}

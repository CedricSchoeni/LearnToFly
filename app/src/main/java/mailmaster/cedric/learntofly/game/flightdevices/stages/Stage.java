package mailmaster.cedric.learntofly.game.flightdevices.stages;

import android.content.Context;

import mailmaster.cedric.learntofly.game.flightdevices.FlightDevice;
import mailmaster.cedric.learntofly.resources.ResourceManager;
import mailmaster.cedric.learntofly.view.RImage;
import mailmaster.cedric.learntofly.view.RObject;

/**
 * Created by Campiotti on 06.03.2018.
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
    private RObject model= null;//(MainActivity.context==null)? null : new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(MainActivity.context, R.drawable.rocket_v1), R.drawable.rocket_v1);
    private int drawable=0;
    private int type=1;
    private int image1=0,image2=0,image3=0,image4=0;

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
    public void setModel(int drawableID, Context context) {
        if(context!=null)
            this.model = new RImage(0,0,64,64, 0, ResourceManager.drawableToBitmap(context, drawableID), drawableID);
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
    @Override
    public int getImage1() {
        return image1;
    }
    @Override
    public void setImage1(int image1) {
        this.image1 = image1;
    }
    @Override
    public int getImage2() {
        return image2;
    }
    @Override
    public void setImage2(int image2) {
        this.image2 = image2;
    }
    @Override
    public int getImage3() {
        return image3;
    }
    @Override
    public void setImage3(int image3) {
        this.image3 = image3;
    }
    @Override
    public int getImage4() {
        return image4;
    }
    @Override
    public void setImage4(int image4) {
        this.image4 = image4;
    }
}

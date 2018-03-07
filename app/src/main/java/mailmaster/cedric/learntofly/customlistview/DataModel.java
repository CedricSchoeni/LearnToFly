package mailmaster.cedric.learntofly.customlistview;

import mailmaster.cedric.learntofly.game.Profile;

/**
 * Created by Cedric on 06.03.2018.
 * The DataModel class holds the info of a single entry in the row_item list
 * and is used by the DataAdapter to be successfully displayed.
 */

public class DataModel {

    private String name;
    private String fuel;
    private String power;
    private String id;
    private String mass;
    private int profileID;
    private Profile profile;

    public DataModel(String name, String fuel, String power, String id, String mass, int profileID, Profile profile) {
        this.name=name;
        this.fuel = fuel;
        this.power = power;
        this.id = id;
        this.mass = mass;
        this.profileID=profileID;
        this.profile=profile;
    }

    public String getName() {
        return name;
    }

    public String getFuel() {
        return fuel;
    }

    public String getPower() {
        return power;
    }

    public String getId() {
        return id;
    }

    public String getMass() {
        return mass;
    }

    public Profile getProfile(){return profile;}


    public int getProfileID(){return profileID;}

}